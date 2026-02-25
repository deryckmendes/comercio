package com.comercio.comercio_catalogo.subcategory;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.comercio.comercio_catalogo.exceptions.ApiException;
import com.comercio.comercio_catalogo.product.Product;
import com.comercio.comercio_catalogo.product.dto.ProductRequestDTO;
import com.comercio.comercio_catalogo.subcategory.dto.mapper.SubCategoryDTOMapper;
import com.comercio.comercio_catalogo.subcategory.dto.request.SubCategoryRequestDTO;
import com.comercio.comercio_catalogo.subcategory.dto.response.SubCategoryResponseDTO;

@Service
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryDTOMapper subCategoryDTOMapper;

    public SubCategoryService(
            SubCategoryRepository subCategoryRepository,
            SubCategoryDTOMapper subCategoryDTOMapper) {
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryDTOMapper = subCategoryDTOMapper;
    }

    public List<SubCategoryResponseDTO> getAll() {
        return subCategoryRepository.findAll().stream()
                .map(subCategoryDTOMapper::toResponse)
                .toList();
    }

    public SubCategoryResponseDTO getById(String id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "Sub-Categoria não encontrada com id: " + id));

        return subCategoryDTOMapper.toResponse(subCategory);
    }

    public List<SubCategoryResponseDTO> getByCategoryId(String categoryId) {
        return subCategoryRepository.findByCategoryId(categoryId).stream()
                .map(subCategoryDTOMapper::toResponse)
                .toList();
    }

    public SubCategoryResponseDTO create(SubCategoryRequestDTO subCategoryDTO) {
        if (subCategoryRepository.existsByName(subCategoryDTO.name())) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "Já existe uma Sub-Categoria com o nome: " + subCategoryDTO.name());
        }

        SubCategory subCategory = subCategoryDTOMapper.toRequest(subCategoryDTO);
        SubCategory save = subCategoryRepository.save(subCategory);
        return subCategoryDTOMapper.toResponse(save);
    }

    public void delete(String id) {
        subCategoryRepository.deleteById(id);
    }

    public SubCategoryResponseDTO createProduct(String subCategoryId, ProductRequestDTO productDTO) {
        System.out.println("PROD: " + subCategoryId + productDTO);
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "Sub-Categoria não encontrada com id: " + subCategoryId));

        boolean existProduct = subCategory.getProducts().stream()
                .anyMatch(product -> product.getName().equalsIgnoreCase(productDTO.name()));
        if (existProduct) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "Já existe um Produto com o nome '" + productDTO.name() + "' nesta subcategoria");
        }

        Product product = new Product(
                productDTO.name(),
                productDTO.description(),
                productDTO.price(),
                productDTO.quantity());

        subCategory.getProducts().add(product);
        subCategoryRepository.save(subCategory);

        return subCategoryDTOMapper.toResponse(subCategory);
    }

    public SubCategory deleteProduct(String subCategoryId, String productId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "Sub-Categoria não encontrada com id: '"
                                + subCategoryId));

        boolean remove = subCategory.getProducts()
                .removeIf(product -> product.getId().equals(productId));

        if (!remove) {
            throw new ApiException(HttpStatus.NOT_FOUND,
                    "Produto inexistente, não foi possível deletar o Produto com id:" + productId);
        }

        return subCategoryRepository.save(subCategory);

    }
}
