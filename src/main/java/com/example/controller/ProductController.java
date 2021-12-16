package com.example.controller;

import com.example.domain.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.jaegertracing.Tracer;
import io.jaegertracing.reporters.RemoteReporter;
import io.jaegertracing.samplers.ConstSampler;
import io.jaegertracing.senders.UdpSender;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.propagation.TextMapInjectAdapter;
import org.bson.Document;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.opentracing.util.AutoFinishScopeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

import static io.opentracing.propagation.Format.Builtin.HTTP_HEADERS;

@RestController
@Validated
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    private Tracer tracer;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostConstruct
    public void init() {
        tracer = new Tracer.Builder("spring-product-service")
                .withReporter(new RemoteReporter.Builder()
                        .withSender(new UdpSender("127.0.0.1", 6831, 0))
                        .build())
                .withSampler(new ConstSampler(true))
                .withScopeManager(new AutoFinishScopeManager())
                .build();
    }

    @PostMapping
    public Mono<Boolean> saveProduct(@RequestBody Product product) {
        tracer.buildSpan("save-product").startActive(true);
        SpanContext context = tracer.activeSpan().context();
        tracer.inject(context, HTTP_HEADERS, new TextMapInjectAdapter(new HashMap<>()));

        log.info("Saving product");
        Mono<Boolean> isProductSaved = productService.saveProduct(product);

        tracer.scopeManager().active().close();
        return productService.saveProduct(product);
    }

    @GetMapping
    public Product getProductById(@RequestParam Long id) {
        return productService.getById(id);
    }

    @GetMapping("/all")
    public Publisher<Document> getAllProducts() {
        HashMap<String, String> map = new HashMap<>();

        tracer.buildSpan("get-all-products").startActive(true);
        SpanContext context = tracer.activeSpan().context();
        tracer.inject(context, HTTP_HEADERS, new TextMapInjectAdapter(new HashMap<>()));

        Publisher<Document> products = productService.getAll();
        tracer.scopeManager().active().close();
        return products;
    }
}
