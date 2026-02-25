package com.comercio.comercio_catalogo.subcategory;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubCategoryRepository extends MongoRepository<SubCategory, String> {
    List<SubCategory> findByCategoryId(String categoryId);

    boolean existsByName(String name);
}
