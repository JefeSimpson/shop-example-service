package com.github.jefesimpson.shop.example.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.jefesimpson.shop.example.model.Product;

import java.io.IOException;

public class ProductSerializer extends StdSerializer<Product> {
    public ProductSerializer() {
        super(Product.class);
    }

    @Override
    public void serialize(Product product, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField(Product.COLUMN_ID, product.getId());
        jsonGenerator.writeStringField(Product.COLUMN_NAME, product.getName());
        jsonGenerator.writeStringField(Product.COLUMN_DESCRIPTION, product.getDescription());
        jsonGenerator.writeObjectField(Product.COLUMN_CREATOR, product.getCreated_by());
        jsonGenerator.writeEndObject();
    }
}
