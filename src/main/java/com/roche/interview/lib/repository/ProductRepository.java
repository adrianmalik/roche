package com.roche.interview.lib.repository;

import com.roche.interview.lib.model.Product;

import java.util.Collection;

public interface ProductRepository {

    void insert(Product product);

    Collection<Product> findAll();

    void remove(String productId);
}
