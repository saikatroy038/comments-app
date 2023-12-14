package org.intuit.craft.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class LocalDateTimeUtil {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.findAndRegisterModules();
    }

    public static LocalDateTime getLocalDateTimeFromString(String val) {
        try {
            return OBJECT_MAPPER.readValue(val, LocalDateTime.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot prase date");
        }
    }

    public static String getStringFromLocalDateTime(LocalDateTime val) {
        try {
            return OBJECT_MAPPER.writeValueAsString(val);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot prase date");
        }
    }
}
