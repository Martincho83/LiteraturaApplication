package com.aluralatam.literalura.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConvert implements IDataConvert {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> generic_class) {
        try {
            return objectMapper.readValue(json,generic_class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
