package com.github.jefesimpson.shop.example.service;

import com.github.jefesimpson.shop.example.configuration.DatabaseUtils;
import com.github.jefesimpson.shop.example.model.Product;
import com.github.jefesimpson.shop.example.service.Service;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;

public class ProductService implements Service<Product> {
    @Override
    public Dao<Product, Integer> dao() throws SQLException {
        return DaoManager.createDao(DatabaseUtils.connectionSource(), Product.class);
    }
}
