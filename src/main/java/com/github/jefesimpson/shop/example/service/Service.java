package com.github.jefesimpson.shop.example.service;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public interface Service <T> {
    Dao<T, Integer> dao() throws SQLException;
    default void create(T object) throws SQLException {
        dao().create(object);
    }
    default void update(T object) throws SQLException {
        dao().update(object);
    }
    default T findById(int id) throws SQLException {
        return dao().queryForId(id);
    }
    default void deleteById(int id) throws SQLException {
        dao().deleteById(id);
    }
    default List<T> all() throws SQLException {
        return dao().queryForAll();
    }
}

