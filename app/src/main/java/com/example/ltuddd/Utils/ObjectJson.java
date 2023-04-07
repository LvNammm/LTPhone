package com.example.ltuddd.Utils;

import android.hardware.lights.Light;
import android.widget.LinearLayout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.util.List;

public class ObjectJson<T> {
    final Class<T> typeParameterClass ;

    public ObjectJson(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public  String toJson(T object){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public T  toObject(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            T t = mapper.readValue(json, typeParameterClass);
            return t;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<T> toListObject(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, new TypeReference<List<T>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
