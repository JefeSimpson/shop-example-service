package com.github.jefesimpson.shop.example.service;

import com.github.jefesimpson.shop.example.model.*;
import com.github.jefesimpson.shop.example.service.Service;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService extends Service<Employee> {
    Employee authenticate(String login, String password);
    boolean loginExist(String login);
    Employee findByLogin(String login);
    Employee authenticate(String token, LocalDate date);
    boolean tokenExist(String token);
    Employee findByToken(String token);
    List<ModelPermission> access(Employee employee, Employee target);
    List<ModelPermission> access(Employee employee, Client target);
    List<ModelPermission> access(Employee employee, Product target);
    List<ModelPermission> access(Employee employee, Order order);
}
