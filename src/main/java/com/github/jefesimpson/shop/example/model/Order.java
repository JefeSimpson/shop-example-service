package com.github.jefesimpson.shop.example.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDate;
import java.util.Objects;

@DatabaseTable
public class Order {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(foreignAutoRefresh = true, foreignAutoCreate = true, foreign = true)
    private Client client;
    @DatabaseField(foreignAutoCreate = true, foreign = true, foreignAutoRefresh = true)
    private Product product;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private LocalDate date;
    @DatabaseField
    private String address;
    @DatabaseField
    private String quantity;
    @DatabaseField
    private String price;
    @DatabaseField(dataType = DataType.ENUM_STRING)
    private OrderStatus status;

    public Order(int id, Client client, Product product, LocalDate date, String address, String quantity, String price, OrderStatus status) {
        this.id = id;
        this.client = client;
        this.product = product;
        this.date = date;
        this.address = address;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client +
                ", product=" + product +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Objects.equals(client, order.client) &&
                Objects.equals(product, order.product) &&
                Objects.equals(date, order.date) &&
                Objects.equals(address, order.address) &&
                Objects.equals(quantity, order.quantity) &&
                Objects.equals(price, order.price) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, product, date, address, quantity, price, status);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    //int id, Client client, Product product, LocalDate date, String address, String quantity, String price, OrderStatus status
    public final static String COLUMN_ID = "id";
    public final static String COLUMN_CLIENT = "client";
    public final static String COLUMN_PRODUCT = "product";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_ADDRESS = "address";
    public final static String COLUMN_QUANTITY = "quantity";
    public final static String COLUMN_PRICE = "price";
    public final static String COLUMN_STATUS = "status";

}
