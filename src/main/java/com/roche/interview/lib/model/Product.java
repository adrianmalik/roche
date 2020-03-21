package com.roche.interview.lib.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.time.LocalDateTime.now;

public class Product {

    private final String id;
    private final String name;
    private final BigDecimal price;
    private final LocalDateTime creationDate;

    // no-argument constructor required for some
    // frameworks like Spring to create instance
    // from request body
    public Product() {
        this("", "", new BigDecimal(0), now());
    }

    public Product(String id, String name, BigDecimal price, LocalDateTime creationDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(creationDate, product.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, creationDate);
    }

    @Override
    public String toString() {
        return "Product{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", date=" + creationDate +
            '}';
    }
}
