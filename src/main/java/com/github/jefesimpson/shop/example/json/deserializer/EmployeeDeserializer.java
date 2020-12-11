package com.github.jefesimpson.shop.example.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.jefesimpson.shop.example.model.Employee;
import com.github.jefesimpson.shop.example.model.EmployeeDepartment;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.LocalDate;

public class EmployeeDeserializer extends StdDeserializer<Employee> {
    public EmployeeDeserializer() {
        super(Employee.class);
    }

    @Override
    public Employee deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root = jsonParser.getCodec().readTree(jsonParser);
        String name = root.get(Employee.COLUMN_NAME).asText();
        String surname = root.get(Employee.COLUMN_SURNAME).asText();
        EmployeeDepartment department = EmployeeDepartment.valueOf(root.get(Employee.COLUMN_DEPARTMENT).asText());
        String login = root.get(Employee.COLUMN_LOGIN).asText();
        String password = root.get(Employee.COLUMN_PASSWORD).asText();
        String token = root.get(Employee.COLUMN_TOKEN).asText();
        LocalDate date = LocalDate.parse(root.get(Employee.COLUMN_DATE_EXTERMINATION).asText());

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        String tokenHash = BCrypt.hashpw(token, BCrypt.gensalt());

        return new Employee(0, name, surname, department, login, passwordHash, tokenHash, date);
    }
}
