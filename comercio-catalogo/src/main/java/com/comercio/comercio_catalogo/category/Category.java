package com.comercio.comercio_catalogo.category;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
@CompoundIndex(name = "unique_user_category", def = "{'userId': 1, 'name': 1}", unique = true)
public class Category {

    @Id
    private String id;
    private String userId;
    private String name;

    public Category(String id, String userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    public Category() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
