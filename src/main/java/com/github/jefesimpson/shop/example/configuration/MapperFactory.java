package com.github.jefesimpson.shop.example.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jefesimpson.shop.example.model.ModelPermission;

public interface MapperFactory {
    ObjectMapper objectMapper(ModelPermission modelPermission);
}
