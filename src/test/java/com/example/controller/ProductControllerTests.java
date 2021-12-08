package com.example.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.domain.Product;
import com.example.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import java.util.Random;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    ObjectMapper mapper;
    ObjectWriter writer;
    Product product;
    String jsonProduct;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    public ProductControllerTests() {
        configureJsonWriter();
    }

    public void configureJsonWriter() {
        try {
            product = Product.builder()
                    .id(new Random().nextLong())
                    .name("random")
                    .stock(10)
                    .price(100)
                    .build();
            mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            writer = mapper.writer().withDefaultPrettyPrinter();
            jsonProduct = writer.writeValueAsString(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void savingProductSuccessfullyReturnsTrue() throws Exception {

        when(productService.saveProduct(product))
                .thenReturn(Mono.just(Boolean.TRUE));
        this.mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonProduct))
                .andExpect(status().is(200));
    }

    @Test
    public void savingProductUnsuccessfullyReturnsFalse() throws Exception {

    }
}
