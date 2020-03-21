package com.roche.interview.lib.repository.mongo.mapping;

import com.roche.interview.lib.model.Product;
import org.bson.Document;
import org.bson.types.Decimal128;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ProductMapper {

    private static final String ID_FIELD = "_id";
    private static final String NAME_FIELD = "name";
    private static final String PRICE_FIELD = "price";
    private static final String DATE_FIELD = "date";

    public Document map(Product product) {
        var document = new Document();
        document.append(NAME_FIELD, product.getName());
        document.append(PRICE_FIELD, product.getPrice());
        document.append(DATE_FIELD, product.getCreationDate());

        return document;
    }

    public Product map(Document document) {
        return new Product(
                document.getObjectId(ID_FIELD).toHexString(),
                document.getString(NAME_FIELD),
                document.get(PRICE_FIELD, Decimal128.class).bigDecimalValue(),
                LocalDateTime.ofInstant(document.getDate(DATE_FIELD).toInstant(), ZoneId.systemDefault())
        );
    }
}
