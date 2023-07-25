package com.seine.productcatalog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    String id;
    String name;
    String[] imageUrls;
    String description;
    String[] tags;

    public Product(String name, String[] imageUrls, String description, String[] tags){
        this.name = name;
        this.imageUrls = imageUrls;
        this.description = description;
        this.tags = tags;
    }
}
