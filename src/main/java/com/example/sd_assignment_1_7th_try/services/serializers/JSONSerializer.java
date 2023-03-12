package com.example.sd_assignment_1_7th_try.services.serializers;

import com.example.sd_assignment_1_7th_try.models.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hibernate.type.SerializationException;

public class JSONSerializer implements Serializer {
    private final ObjectMapper objectMapper;

    public JSONSerializer() {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    public String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new SerializationException("Error serializing ticket to JSON", e);
        }
    }
}
