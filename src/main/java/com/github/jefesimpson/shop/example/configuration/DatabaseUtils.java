package com.github.jefesimpson.shop.example.configuration;

import com.github.jefesimpson.shop.example.configuration.Constants;
import com.github.jefesimpson.shop.example.model.Client;
import com.github.jefesimpson.shop.example.model.Employee;
import com.github.jefesimpson.shop.example.model.Order;
import com.github.jefesimpson.shop.example.model.Product;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseUtils {
    private final static ConnectionSource connectionSource;


    static{
        try {
            connectionSource = new JdbcConnectionSource(Constants.DB_PATH);

            TableUtils.createTableIfNotExists(connectionSource, Client.class);
            TableUtils.createTableIfNotExists(connectionSource, Employee.class);
            TableUtils.createTableIfNotExists(connectionSource, Order.class);
            TableUtils.createTableIfNotExists(connectionSource, Product.class);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database initialize exception");
        }
    }

    public static ConnectionSource connectionSource() {
        if (connectionSource == null)
            throw new RuntimeException("Connection incorrect exception");
        return connectionSource;
    }

    public DatabaseUtils(){}
}
