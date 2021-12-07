package com.example.domain;

import com.mongodb.lang.Nullable;
import lombok.*;
import org.bson.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
