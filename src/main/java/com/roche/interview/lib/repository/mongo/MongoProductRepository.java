package com.roche.interview.lib.repository.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.roche.interview.lib.model.Product;
import com.roche.interview.lib.repository.ProductRepository;
import com.roche.interview.lib.repository.mongo.mapping.ProductMapper;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.LinkedList;

public class MongoProductRepository implements ProductRepository {

    public static final String COLLECTION_NAME = "products";

    private final ProductMapper productMapper;
    private final MongoCollection<Document> collection;

    public MongoProductRepository(MongoCollection<Document> collection) {
        productMapper = new ProductMapper();
        this.collection = collection;
    }

    @Override
    public void insert(Product product) {
        var document = productMapper.map(product);
        collection.insertOne(document);
    }

    @Override
    public Collection<Product> findAll() {
        var products = new LinkedList<Product>();
        var iterable = collection.find().cursor();
        while (iterable.hasNext()) {
            var document = iterable.next();
            var product = productMapper.map(document);
            products.add(product);
        }

        return products;
    }

    @Override
    public void remove(String productId) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(productId)));
    }
}
