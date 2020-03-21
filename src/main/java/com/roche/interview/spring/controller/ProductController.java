package com.roche.interview.spring.controller;

import com.roche.interview.lib.model.Product;
import com.roche.interview.lib.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.roche.interview.spring.controller.ProductController.PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(PATH)
public class ProductController {

    static final String PATH = "products";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody Product product) {
        productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Product>> getAll() {
        var products = productService.getAll();

        return ResponseEntity.ok(products);
    }

    @PatchMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> update(Product product, @PathVariable String id) {
        productService.update(product, id);

        // TODO (more than 2 hours are needed)
        throw new UnsupportedOperationException();
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable String id) {
        productService.delete(id);

        // TODO (more than 2 hours are needed)
        throw new UnsupportedOperationException();
    }
}
