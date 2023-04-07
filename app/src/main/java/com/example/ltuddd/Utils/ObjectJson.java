package com.example.ltuddd.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

public class ObjectJson<T> {
    public static String toJson(Object object){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static Object toObject(String json, Class t){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json,t);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
