package com.github.jefesimpson.shop.example.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.jefesimpson.shop.example.configuration.DatabaseUtils;
import com.github.jefesimpson.shop.example.model.Employee;
import com.github.jefesimpson.shop.example.model.Product;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.io.IOException;
import java.sql.SQLException;

public class ProductDeserializer extends StdDeserializer<Product> implements DaoDeserializer<Employee, Employee> {

    public ProductDeserializer() {
        super(Product.class);
    }

    @Override
    public Product deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root = jsonParser.getCodec().readTree(jsonParser);
        String name = root.get(Product.COLUMN_NAME).asText();
        String description = root.get(Product.COLUMN_DESCRIPTION).asText();
        int employeeId = root.get(Product.COLUMN_CREATOR).asInt();

        Employee employee = null;
        try {
            employee = daoOne().queryForId(employeeId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new Product(0, name, description, employee);
    }

    @Override
    public Dao<Employee, Integer> daoOne() throws SQLException {
        return DaoManager.createDao(DatabaseUtils.connectionSource(), Employee.class);
    }

    @Override
    public Dao daoTwo() throws SQLException {
        return null;
    }
}

