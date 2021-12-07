package com.example.controller;

import com.example.domain.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bson.Document;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@Validated
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Mono<Boolean> saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping
    public Product getProductById(@RequestParam Long id) {
        return productService.getById(id);
    }

    @GetMapping("/all")
    public Publisher<Document> getAllProducts() {
        return productService.getAll();
    }
}
