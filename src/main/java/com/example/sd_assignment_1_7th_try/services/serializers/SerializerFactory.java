package com.example.sd_assignment_1_7th_try.services.serializers;

import com.example.sd_assignment_1_7th_try.models.Show;
import com.example.sd_assignment_1_7th_try.models.Ticket;

public class SerializerFactory {
    public static Serializer getSerializer(String serializer) {
        if (serializer.equals("JSON")) {
            return new JSONSerializer();
        } else {
            throw new IllegalArgumentException("Unsupported serializer: " + serializer);
        }
    }
}
