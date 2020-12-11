package com.github.jefesimpson.shop.example.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDate;
import java.util.Objects;

@DatabaseTable
public class Employee {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String surname;
    @DatabaseField(dataType = DataType.ENUM_STRING)
    private EmployeeDepartment department;
    @DatabaseField
    private String login;
    @DatabaseField
    private String password;
    @DatabaseField
    private String token;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private LocalDate dateOfExtermination;

    public Employee(int id, String name, String surname, EmployeeDepartment department, String login, String password, String token, LocalDate dateOfExtermination) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.department = department;
        this.login = login;
        this.password = password;
        this.token = token;
        this.dateOfExtermination = dateOfExtermination;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", department=" + department +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", dateOfExtermination=" + dateOfExtermination +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                Objects.equals(name, employee.name) &&
                Objects.equals(surname, employee.surname) &&
                department == employee.department &&
                Objects.equals(login, employee.login) &&
                Objects.equals(password, employee.password) &&
                Objects.equals(token, employee.token) &&
                Objects.equals(dateOfExtermination, employee.dateOfExtermination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, department, login, password, token, dateOfExtermination);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LocalDate getDateOfExtermination() {
        return dateOfExtermination;
    }

    public void setDateOfExtermination(LocalDate dateOfExtermination) {
        this.dateOfExtermination = dateOfExtermination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public EmployeeDepartment getDepartment() {
        return department;
    }

    public void setDepartment(EmployeeDepartment department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Employee() {
    }


    public final static String COLUMN_ID = "id";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_SURNAME = "surname";
    public final static String COLUMN_DEPARTMENT = "department";
    public final static String COLUMN_EMAIL = "email";
    public final static String COLUMN_PHONE = "phone";
    public final static String COLUMN_PASSWORD = "password";
    public final static String COLUMN_TOKEN = "token";
    public final static String COLUMN_LOGIN = "login";
    public final static String COLUMN_DATE_EXTERMINATION = "dateOfExtermination";
}
