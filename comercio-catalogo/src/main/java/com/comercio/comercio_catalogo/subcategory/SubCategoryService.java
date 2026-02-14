package com.comercio.comercio_catalogo.subcategory;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryService(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    public List<SubCategory> getAll() {
        return subCategoryRepository.findAll();
    }

    public SubCategory getById(String id) {
        return subCategoryRepository.findById(id).orElse(null);
    }

    public SubCategory create(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    public void delete(String id) {
        subCategoryRepository.deleteById(id);
    }
}
