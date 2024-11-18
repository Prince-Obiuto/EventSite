package com.cit306.EventSite.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

public class Json {

    private static ObjectMapper myObjectMapper = defaultObjectMapper();

    private static ObjectMapper defaultObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static JsonNode parse(String jsonSource) throws JsonProcessingException {
        return myObjectMapper.readTree(jsonSource);
    }

    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException{
        return myObjectMapper.treeToValue(node, clazz);
    }

    public static JsonNode toJson(Object object){
        return myObjectMapper.valueToTree(object);
    }

    public static String stringer(JsonNode node) throws JsonProcessingException {
        return generateJSON(node, false);
    }

    public static String stringerPretty(JsonNode node) throws JsonProcessingException {
        return generateJSON(node, true);
    }

    private static String generateJSON(Object object1, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = myObjectMapper.writer();
        if(pretty){
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(object1);
    }
}
