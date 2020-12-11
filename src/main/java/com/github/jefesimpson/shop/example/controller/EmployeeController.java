package com.github.jefesimpson.shop.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jefesimpson.shop.example.configuration.Constants;
import com.github.jefesimpson.shop.example.configuration.MapperFactory;
import com.github.jefesimpson.shop.example.model.Employee;
import com.github.jefesimpson.shop.example.model.ModelPermission;
import com.github.jefesimpson.shop.example.service.ClientService;
import com.github.jefesimpson.shop.example.service.EmployeeService;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.InternalServerErrorResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeController implements AuthorizationController<Employee> {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;
    private final ClientService clientService;
    private final MapperFactory mapperFactory;

    public EmployeeController(EmployeeService employeeService, ClientService clientService, MapperFactory mapperFactory) {
        this.employeeService = employeeService;
        this.clientService = clientService;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public void create(Context context) {
        try {
            Employee employee = employeeSenderOrThrowUnauthorized(context);
            Employee target = mapperFactory.objectMapper(ModelPermission.CREATE).readValue(context.body(), Employee.class);

            LOGGER.info(String.format("Sender {%s} started to create employee {%s} ", employee, target));
            if(employeeService.access(employee, target).contains(ModelPermission.CREATE)) {
                employeeService.create(target);
                context.status(Constants.CREATED_201);
                LOGGER.info(String.format("Sender {%s} successfully created employee {%s} ", employee, target));
            }
            else {
                LOGGER.info(String.format("Sender {%s} is not authorized to create employee {%s}. Throwing Forbidden", employee, target));
                throw new ForbiddenResponse();
            }
        } catch (JsonProcessingException | SQLException e) {
            e.printStackTrace();
            throw new BadRequestResponse();
        }
    }

    @Override
    public void delete(Context context, int id) {
        try {
            Employee employee = employeeSenderOrThrowUnauthorized(context);
            Employee target = employeeService.findById(id);

            LOGGER.info(String.format("Sender {%s} started to delete employee {%s} ", employee, target));
            if (employeeService.access(employee, target).contains(ModelPermission.DELETE)) {
                clientService.deleteById(id);
                LOGGER.info(String.format("Sender {%s} successfully deleted employee {%s} ", employee, target));
                context.status(Constants.NO_CONTENT_204);
            }
            else {
                LOGGER.info(String.format("Sender {%s} is not authorized to delete employee {%s}. Throwing Forbidden", employee, target));
                throw new ForbiddenResponse();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BadRequestResponse();
        }
    }

    @Override
    public void update(Context context, int id) {
        try {
            Employee employee = employeeSenderOrThrowUnauthorized(context);
            Employee target = employeeService.findById(id);

            LOGGER.info(String.format("Sender {%s} started to update employee {%s} ", employee, target));
            if(employeeService.access(employee, target).contains(ModelPermission.UPDATE)) {
                context.result(mapperFactory.objectMapper(ModelPermission.UPDATE).writeValueAsString(target));

                Employee updated = mapperFactory.objectMapper(ModelPermission.UPDATE).readValue(context.body(), Employee.class);
                updated.setId(id);
                employeeService.update(updated);
                LOGGER.info(String.format("Sender {%s} successfully updated employee {%s} ", employee, target));
                context.result(mapperFactory.objectMapper(ModelPermission.UPDATE).writeValueAsString(updated));
            }
            else {
                LOGGER.info(String.format("Sender {%s} is not authorized to update employee {%s}. Throwing Forbidden", employee, target));
                throw new ForbiddenResponse();
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            throw new BadRequestResponse();
        }
    }

    @Override
    public void getAll(Context context) {
        try {
            Employee employee = employeeSenderOrThrowUnauthorized(context);

            LOGGER.info(String.format("Sender {%s} started to getAll", employee));

            List<Employee> employees = employeeService.all()
                    .stream()
                    .filter(target -> employeeService.access(employee, target).contains(ModelPermission.READ))
                    .collect(Collectors.toList());
            LOGGER.info(String.format("Sender {%s} successfully gotAll", employee));
            context.result(mapperFactory.objectMapper(ModelPermission.READ).writeValueAsString(employees));
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            throw new InternalServerErrorResponse();
        }
    }

    @Override
    public void getOne(Context context, int id) {
        try {
            Employee employee = employeeSenderOrThrowUnauthorized(context);
            Employee target = employeeService.findById(id);

            LOGGER.info(String.format("Sender {%s} started to getOne employee {%s} ", employee, target));
            if (employeeService.access(employee, target).contains(ModelPermission.READ)) {
                LOGGER.info(String.format("Sender {%s} successfully gotOne employee {%s} ", employee, target));
                context.result(mapperFactory.objectMapper(ModelPermission.READ).writeValueAsString(target));
            }
            else {
                LOGGER.info(String.format("Sender {%s} is not authorized to getOne employee {%s}. Throwing Forbidden", employee, target));
                throw new ForbiddenResponse();
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            throw new InternalServerErrorResponse();
        }
    }

    @Override
    public ClientService clientService() {
        return clientService;
    }

    @Override
    public EmployeeService employeeService() {
        return employeeService;
    }
}
