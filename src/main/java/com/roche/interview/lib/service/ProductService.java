package com.roche.interview.lib.service;

import com.roche.interview.lib.model.Product;
import com.roche.interview.lib.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void create(Product product) {
        logger.info("Creating product {}", product);
        productRepository.insert(product);
    }

    public Collection<Product> getAll() {
        logger.info("Getting all products");
        return productRepository.findAll();
    }

    public void delete(String id) {
        logger.info("Deleting product with id {}", id);
        productRepository.remove(id);
    }

    public Product update(Product product, String id) {
        logger.info("Updating the product with id {} using data {}", id, product);
        return null;
    }
}
