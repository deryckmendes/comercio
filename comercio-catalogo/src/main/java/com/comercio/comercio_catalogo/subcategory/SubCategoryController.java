package com.comercio.comercio_catalogo.subcategory;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comercio.comercio_catalogo.product.dto.ProductRequestDTO;
import com.comercio.comercio_catalogo.subcategory.dto.request.SubCategoryRequestDTO;
import com.comercio.comercio_catalogo.subcategory.dto.response.SubCategoryResponseDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(
            SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;

    }

    @GetMapping
    public List<SubCategoryResponseDTO> getAll() {
        System.out.println("SUBCATEGORY CALL!");
        return subCategoryService.getAll();
    }

    @GetMapping("/{id}")
    public SubCategoryResponseDTO getById(@PathVariable String id) {
        return subCategoryService.getById(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<SubCategoryResponseDTO> getByCategoryId(@PathVariable String categoryId) {
        return subCategoryService.getByCategoryId(categoryId);

    }

    @PostMapping
    public SubCategoryResponseDTO create(@RequestBody @Valid SubCategoryRequestDTO subCategoryDTO) {
        return subCategoryService.create(subCategoryDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        subCategoryService.delete(id);
    }

    @PostMapping("/{subCategoryId}/products")
    public ResponseEntity<SubCategoryResponseDTO> createProduct(
            @PathVariable String subCategoryId,
            @RequestBody @Valid ProductRequestDTO productDTO) {

        SubCategoryResponseDTO responseDTO = subCategoryService.createProduct(subCategoryId, productDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @DeleteMapping("/{subCategoryId}/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String subCategoryId, @PathVariable String productId) {
        subCategoryService.deleteProduct(subCategoryId, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
