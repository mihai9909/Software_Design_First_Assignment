package com.example.sd_assignment_1_7th_try.services;


import com.example.sd_assignment_1_7th_try.services.serializers.Serializer;
import com.example.sd_assignment_1_7th_try.services.serializers.SerializerFactory;
import org.springframework.stereotype.Service;

@Service
public class SerializationService {
    private final SerializerFactory serializerFactory;

    public SerializationService() {
        serializerFactory = new SerializerFactory();
    }

    public String serializeAs(String serializzer, Object object) {
        Serializer serializer = serializerFactory.getSerializer(serializzer);
        return serializer.serialize(object);
    }
}
