package com.github.jefesimpson.shop.example.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.jefesimpson.shop.example.model.Client;

import java.io.IOException;

public class ClientSerializer extends StdSerializer<Client> {
    public ClientSerializer() {
        super(Client.class);
    }

    @Override
    public void serialize(Client client, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField(Client.COLUMN_ID, client.getId());
        jsonGenerator.writeStringField(Client.COLUMN_NAME, client.getName());
        jsonGenerator.writeStringField(Client.COLUMN_SURNAME, client.getSurname());
        jsonGenerator.writeStringField(Client.COLUMN_EMAIL, client.getEmail());
        jsonGenerator.writeStringField(Client.COLUMN_PHONE, client.getPhone());
        jsonGenerator.writeStringField(Client.COLUMN_LOGIN, client.getLogin());
        jsonGenerator.writeStringField(Client.COLUMN_TOKEN, client.getToken());
        jsonGenerator.writeObjectField(Client.COLUMN_DATE_EXTERMINATION, client.getDateOfExtermination());
        jsonGenerator.writeEndObject();
    }
}
