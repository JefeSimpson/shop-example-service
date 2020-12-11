package com.github.jefesimpson.shop.example.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.jefesimpson.shop.example.configuration.DatabaseUtils;
import com.github.jefesimpson.shop.example.model.Client;
import com.github.jefesimpson.shop.example.model.Order;
import com.github.jefesimpson.shop.example.model.OrderStatus;
import com.github.jefesimpson.shop.example.model.Product;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderDeserializer extends StdDeserializer<Order> implements DaoDeserializer<Client, Product>{
    public OrderDeserializer() {
        super(Order.class);
    }



    @Override
    public Order deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root = jsonParser.getCodec().readTree(jsonParser);
        int clientId = root.get(Order.COLUMN_CLIENT).asInt();
        int productId = root.get(Order.COLUMN_PRODUCT).asInt();
        LocalDate date = LocalDate.parse(root.get(Order.COLUMN_DATE).asText());
        String address = root.get(Order.COLUMN_ADDRESS).asText();
        String quantity = root.get(Order.COLUMN_QUANTITY).asText();
        String price = root.get(Order.COLUMN_PRICE).asText();
        OrderStatus status = OrderStatus.valueOf(root.get(Order.COLUMN_STATUS).asText());

        Client client = null;
        try {
            client = daoOne().queryForId(clientId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Product product = null;
        try {
            product = daoTwo().queryForId(productId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new Order(0, client, product, date, address, quantity, price, status);
    }

    @Override
    public Dao<Client, Integer> daoOne() throws SQLException {
        return DaoManager.createDao(DatabaseUtils.connectionSource(), Client.class);
    }

    @Override
    public Dao<Product, Integer> daoTwo() throws SQLException {
        return DaoManager.createDao(DatabaseUtils.connectionSource(), Product.class);
    }
}
