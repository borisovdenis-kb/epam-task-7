package ru.intodayer.serializator;

import java.util.ArrayList;
import java.util.List;


public class CustomJsonSerializer implements Serializer {
    private List<? extends Object> serializableObjects = new ArrayList<>();

    public CustomJsonSerializer(List<? extends Object> serializableObjects) {
        this.serializableObjects = serializableObjects;
    }

    @Override
    public void serialize(String outFilePath) throws SerializationException {

    }

    @Override
    public List<Object> deserialize(String inFilePath) throws SerializationException {
        return null;
    }
}
