package com.example.service;

import com.example.domain.Product;
import com.example.repository.ProductRepository;
import com.mongodb.reactivestreams.client.MongoClient;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.bson.Document;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private final MongoClient mongoClient;

    public ProductService(MongoClient mongoClient, ProductRepository productRepository) {
        this.mongoClient = mongoClient;
        this.productRepository = productRepository;
    }

    public Mono<Boolean> saveProduct(Product product) {
        return Mono.from(mongoClient.getDatabase("admin")
                                    .getCollection("products")
                                    .insertOne(product))
                        .map(result -> Boolean.TRUE)
                        .onErrorReturn(Boolean.FALSE);
    }

    public Product getById(Long id) {
        return productRepository.findById(id).get();
    }

    public Flux<Document> getAll() {
        return Flux.from(mongoClient.getDatabase("admin")
                .getCollection("products")
                .find());
    }

}
