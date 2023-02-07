package com.frwk.marketplace.customer.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class MappingJsonConvertion {

    public static String objectMapper(Object builder) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            LocalDateSerializer localDateSerializer = new LocalDateSerializer(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            javaTimeModule.addSerializer(LocalDate.class, localDateSerializer);
            LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            javaTimeModule.addDeserializer(LocalDate.class, localDateDeserializer);
            mapper.registerModule(new JavaTimeModule());

            return mapper.writeValueAsString(builder);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}