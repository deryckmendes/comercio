package com.comercio.comercio_catalogo.product;

import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id" })
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private Integer quantity;

    public Product(String name, String description, double price, Integer quantity) {
        this.id = new ObjectId().toString();
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Product product = (Product) obj;
        return Objects.equals(id, product.id)
                && Objects.equals(name, product.name)
                && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

}
