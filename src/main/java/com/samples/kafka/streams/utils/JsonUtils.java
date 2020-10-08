package com.samples.kafka.streams.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
    private static JsonUtils instance = null;
    private ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtils() {
    }

    public static JsonUtils Instance() {
        if (instance == null) {
            instance = new JsonUtils();
        }

        return instance;
    }

    public <T> T readJsonAsObject(Class<T> cls, String json) {
        T t = null;
        try {
            t = this.MAPPER.readValue(json, cls);
        } catch (IOException e) {
            System.out.println("Error occured while reading json "+ e);
        }
        return t;
    }


    public <T> String toJson(T object) {
        String json = null;
        try {
            json = this.MAPPER.writeValueAsString(object);
        } catch (IOException e) {
            System.out.println("Error occured while creating json "+ e);
        }
        return json;
    }


}


