package com.roche.interview.spring.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.roche.interview.lib.repository.ProductRepository;
import com.roche.interview.lib.repository.mongo.MongoProductRepository;
import com.roche.interview.lib.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public MongoDatabase mongoClient(
        @Value("${mongodb.host}") String host,
        @Value("${mongodb.port}") int port,
        @Value("${mongodb.database}") String database
    ) {
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.maxConnectionIdleTime(250000);
        MongoClientOptions opts = builder.build();

        return new MongoClient(new ServerAddress(host, port), opts).getDatabase(database);
    }

    @Bean
    public ProductRepository productRepository(MongoDatabase mongoDatabase) {
        return new MongoProductRepository(mongoDatabase.getCollection(MongoProductRepository.COLLECTION_NAME));
    }

    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new ProductService(productRepository);
    }
}
