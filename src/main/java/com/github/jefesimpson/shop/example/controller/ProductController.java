package com.github.jefesimpson.shop.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jefesimpson.shop.example.configuration.Constants;
import com.github.jefesimpson.shop.example.configuration.MapperFactory;
import com.github.jefesimpson.shop.example.model.Client;
import com.github.jefesimpson.shop.example.model.Employee;
import com.github.jefesimpson.shop.example.model.ModelPermission;
import com.github.jefesimpson.shop.example.model.Product;
import com.github.jefesimpson.shop.example.service.ClientService;
import com.github.jefesimpson.shop.example.service.EmployeeService;
import com.github.jefesimpson.shop.example.service.Service;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.InternalServerErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController implements AuthorizationController<Product> {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final Service<Product> productService;
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final MapperFactory mapperFactory;

    public ProductController(Service<Product> productService, ClientService clientService, EmployeeService employeeService, MapperFactory mapperFactory) {
        this.productService = productService;
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public void create(Context context) {
        try {
            Employee employee = employeeSenderOrThrowUnauthorized(context);
            Product target = mapperFactory.objectMapper(ModelPermission.CREATE).readValue(context.body(), Product.class);

            LOGGER.info(String.format("Sender {%s} started to create product {%s} ", employee, target));
            if(employeeService.access(employee, target).contains(ModelPermission.CREATE)){
                productService.create(target);
                LOGGER.info(String.format("Sender {%s} successfully created product {%s} ", employee, target));
                context.status(Constants.CREATED_201);
            }
            else{
                LOGGER.info(String.format("Sender {%s} is not authorized to create product {%s}. Throwing Forbidden", employee, target));
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
            Product target = productService.findById(id);

            LOGGER.info(String.format("Sender {%s} started to delete product {%s} ", employee, target));
            if (employeeService.access(employee, target).contains(ModelPermission.DELETE)){
                productService.deleteById(id);
                LOGGER.info(String.format("Sender {%s} successfully deleted product {%s} ", employee, target));
                context.status(Constants.NO_CONTENT_204);
            }
            else{
                LOGGER.info(String.format("Sender {%s} is not authorized to delete product {%s}. Throwing Forbidden", employee, target));
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
            Product target = productService.findById(id);

            LOGGER.info(String.format("Sender {%s} started to update product {%s} ", employee, target));
            if (employeeService.access(employee, target).contains(ModelPermission.UPDATE)){
                Product updated = mapperFactory.objectMapper(ModelPermission.UPDATE).readValue(context.body(), Product.class);
                updated.setId(id);
                productService.update(updated);

                LOGGER.info(String.format("Sender {%s} successfully updated product {%s} ", employee, target));
                context.result(mapperFactory.objectMapper(ModelPermission.UPDATE).writeValueAsString(updated));
            }
            else{
                LOGGER.info(String.format("Sender {%s} is not authorized to update product {%s}. Throwing Forbidden", employee, target));
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
            Client client = clientSenderOrThrowUnauthorized(context);

            if(client != null) {
                LOGGER.info(String.format("Sender {%s} started to getAll", client));
                List<Product> products = productService.all()
                        .stream()
                        .filter(target -> clientService.access(client, target).contains(ModelPermission.READ))
                        .collect(Collectors.toList());
                LOGGER.info(String.format("Sender {%s} successfully gotAll", client));
                context.result(mapperFactory.objectMapper(ModelPermission.READ).writeValueAsString(products));
            }
            else if (employee != null) {
                LOGGER.info(String.format("Sender {%s} started to getAll", employee));
                List<Product> products = productService.all()
                        .stream()
                        .filter(target -> employeeService.access(employee, target).contains(ModelPermission.READ))
                        .collect(Collectors.toList());
                LOGGER.info(String.format("Sender {%s} successfully gotAll", employee));
                context.result(mapperFactory.objectMapper(ModelPermission.READ).writeValueAsString(products));
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            throw new InternalServerErrorResponse();
        }
    }

    @Override
    public void getOne(Context context, int id) {
        try {
            Employee employee = employeeSenderOrThrowUnauthorized(context);
            Client client = clientSenderOrThrowUnauthorized(context);
            Product target = productService.findById(id);

            if (client != null) {
                LOGGER.info(String.format("Sender {%s} started to getOne product {%s} ", client, target));
                if (clientService.access(client, target).contains(ModelPermission.READ)) {
                    LOGGER.info(String.format("Sender {%s} successfully gotOne product {%s} ", client, target));
                    context.result(mapperFactory.objectMapper(ModelPermission.READ).writeValueAsString(target));
                }
                else {
                    LOGGER.info(String.format("Sender {%s} is not authorized to getOne product {%s}. Throwing Forbidden", client, target));
                    throw new ForbiddenResponse();
                }
            }
            else if (employee != null) {
                LOGGER.info(String.format("Sender {%s} started to getOne product {%s} ", employee, target));
                if (employeeService.access(employee, target).contains(ModelPermission.READ)) {
                    LOGGER.info(String.format("Sender {%s} successfully gotOne product {%s} ", employee, target));
                    context.result(mapperFactory.objectMapper(ModelPermission.READ).writeValueAsString(target));
                }
                else {
                    LOGGER.info(String.format("Sender {%s} is not authorized to getOne product {%s}. Throwing Forbidden", employee, target));
                    throw new ForbiddenResponse();
                }
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
