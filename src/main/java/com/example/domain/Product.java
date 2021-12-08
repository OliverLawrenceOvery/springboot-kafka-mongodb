package com.example.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "products")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product extends org.bson.Document {

    private Long id;
    private String name;
    private double price;
    private Integer stock;

}
