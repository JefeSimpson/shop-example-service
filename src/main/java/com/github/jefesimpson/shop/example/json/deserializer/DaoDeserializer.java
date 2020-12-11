package com.github.jefesimpson.shop.example.json.deserializer;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public interface DaoDeserializer<T, S> {
    Dao<T, Integer> daoOne() throws SQLException;
    Dao<S, Integer> daoTwo() throws SQLException;
}
