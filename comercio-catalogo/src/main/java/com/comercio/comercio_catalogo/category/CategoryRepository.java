package com.comercio.comercio_catalogo.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
    boolean existsByName(String name);

    List<Category> findAllByUserId(String userId);

    Optional<Category> findByIdAndUserId(String id, String userId);

    boolean existsByNameAndUserId(String name, String userId);
}
