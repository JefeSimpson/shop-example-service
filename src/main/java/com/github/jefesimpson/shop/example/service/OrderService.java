package com.github.jefesimpson.shop.example.service;

import com.github.jefesimpson.shop.example.configuration.DatabaseUtils;
import com.github.jefesimpson.shop.example.model.Order;
import com.github.jefesimpson.shop.example.service.Service;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;

public class OrderService implements Service<Order> {

    @Override
    public Dao<Order, Integer> dao() throws SQLException {
        return DaoManager.createDao(DatabaseUtils.connectionSource(), Order.class);
    }
}
