package com.comercio.comercio_catalogo.subcategory;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.comercio.comercio_catalogo.product.Product;

@Document(collection = "subcategories")
public class SubCategory {

    @Id
    private String id;
    private String categoryId;
    private String name;
    private Set<Product> products = new LinkedHashSet<>();

    public SubCategory(String id, String categoryId, String name, Set<Product> products) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.products = products;
    }

    public SubCategory() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
