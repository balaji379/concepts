package com.secure.assignment1.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class JsonConverter implements AttributeConverter<JsonNode, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(JsonNode jsonNode) {
        try {
            return (jsonNode == null) ? null : objectMapper.writeValueAsString(jsonNode);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JsonNode to JSON string", e);
        }
    }

    @Override
    public JsonNode convertToEntityAttribute(String json) {
        try {
            return (json == null) ? null : objectMapper.readTree(json);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON string to JsonNode", e);
        }
    }
}
//@Converter(autoApply = true)
//class DummyConverter implements AttributeConverter<JsonNode,String>{
//
//    ObjectMapper mapper = new ObjectMapper();
//    @Override
//    public String convertToDatabaseColumn(JsonNode attribute) {
//        try {
//            return attribute != null ? mapper.writeValueAsString(attribute) : null;
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public JsonNode convertToEntityAttribute(String dbData) {
//
//        try {
//            return dbData == null ? null : mapper.readTree(dbData);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
