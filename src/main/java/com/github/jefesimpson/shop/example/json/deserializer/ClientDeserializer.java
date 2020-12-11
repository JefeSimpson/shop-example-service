package com.github.jefesimpson.shop.example.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.jefesimpson.shop.example.model.Client;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.LocalDate;

public class ClientDeserializer extends StdDeserializer<Client> {
    public ClientDeserializer() {
        super(Client.class);
    }

    @Override
    public Client deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root = jsonParser.getCodec().readTree(jsonParser);
        String name = root.get(Client.COLUMN_NAME).asText();
        String surname = root.get(Client.COLUMN_SURNAME).asText();
        String email = root.get(Client.COLUMN_EMAIL).asText();
        String phone = root.get(Client.COLUMN_PHONE).asText();
        String login = root.get(Client.COLUMN_LOGIN).asText();
        String password = root.get(Client.COLUMN_PASSWORD).asText();
        String token = root.get(Client.COLUMN_TOKEN).asText();
        LocalDate date = LocalDate.parse(root.get(Client.COLUMN_DATE_EXTERMINATION).asText());
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        String tokenHash = BCrypt.hashpw(token, BCrypt.gensalt());

        return new Client(0, name, surname, email, phone, login, passwordHash, tokenHash, date);
    }
}
