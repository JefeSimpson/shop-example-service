package com.github.jefesimpson.shop.example.service;

import com.github.jefesimpson.shop.example.configuration.DatabaseUtils;
import com.github.jefesimpson.shop.example.model.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicEmployeeService implements EmployeeService {
    @Override
    public Dao<Employee, Integer> dao() throws SQLException {
        return DaoManager.createDao(DatabaseUtils.connectionSource(), Employee.class);
    }

    @Override
    public Employee authenticate(String login, String password) {

        if(!loginExist(login)) {
            throw new RuntimeException("login not exists");
        }
        Employee employee = findByLogin(login);
        if(employee.getToken() != null) {
            throw new RuntimeException("token exists");
        }
        if(BCrypt.checkpw(password, employee.getPassword())) {
            SecretGenerator secretGenerator = new DefaultSecretGenerator();
            String secret = secretGenerator.generate();
            employee.setToken(secret);
            return employee;
        }
        else{
            return null;
        }
    }

    @Override
    public boolean loginExist(String login) {
        return findByLogin(login) != null;
    }

    @Override
    public Employee findByLogin(String login) {
        try {
            QueryBuilder<Employee, Integer> queryBuilder = dao().queryBuilder();
            queryBuilder.where().eq(Employee.COLUMN_LOGIN, login);
            PreparedQuery<Employee> preparedQuery = queryBuilder.prepare();
            Employee employee = dao().queryForFirst(preparedQuery);
            return employee;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("dao exception");
        }
    }

    @Override
    public Employee authenticate(String token, LocalDate date) {
        if(!tokenExist(token)){
            throw new RuntimeException("token doesn't exist");
        }
        Employee employee = findByToken(token);
        if(date.isAfter(employee.getDateOfExtermination())) {
            employee.setToken(null);
            throw new RuntimeException("token is invalid. Please log in using login and password");
        }
        else{
            return employee;
        }
    }

    @Override
    public boolean tokenExist(String token) {
        return findByToken(token) != null;
    }

    @Override
    public Employee findByToken(String token) {
        try {
            QueryBuilder<Employee, Integer> queryBuilder = dao().queryBuilder();
            queryBuilder.where().eq(Employee.COLUMN_TOKEN, token);
            PreparedQuery<Employee> preparedQuery = queryBuilder.prepare();
            Employee employee = dao().queryForFirst(preparedQuery);
            return employee;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("dao exception");
        }
    }

    @Override
    public List<ModelPermission> access(Employee employee, Employee target) {
        if(employee.getDepartment() == EmployeeDepartment.MANAGER){
            List<ModelPermission> modelPermissions = new ArrayList<>(Arrays.asList(ModelPermission.values()));
            return modelPermissions;
        }
        return null;
    }

    @Override
    public List<ModelPermission> access(Employee employee, Client target) {
        if(employee.getDepartment() == EmployeeDepartment.SALES) {
            List<ModelPermission> modelPermissions = new ArrayList<>(Arrays.asList(ModelPermission.values()));
            return modelPermissions;
        }
        return null;
    }

    @Override
    public List<ModelPermission> access(Employee employee, Product target) {
        if(employee.getDepartment() == EmployeeDepartment.PRODUCTION) {
            List<ModelPermission> modelPermissions = new ArrayList<>(Arrays.asList(ModelPermission.values()));
            return modelPermissions;
        }
        return null;
    }

    @Override
    public List<ModelPermission> access(Employee employee, Order order) {
        if(employee.getDepartment() == EmployeeDepartment.SALES) {
            List<ModelPermission> modelPermissions = new ArrayList<>(Arrays.asList(ModelPermission.values()));
            return modelPermissions;
        }
        else if(employee.getDepartment() == EmployeeDepartment.PRODUCTION) {
            List<ModelPermission> modelPermissions = new ArrayList<>(Arrays.asList(ModelPermission.READ));
            return modelPermissions;
        }
        return null;
    }
}
