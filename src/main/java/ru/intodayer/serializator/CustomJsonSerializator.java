package ru.intodayer.serializator;

import java.util.ArrayList;
import java.util.List;


public class CustomJsonSerializator implements Serializator {
    private List<? extends Object> serializableObjects = new ArrayList<>();

    public CustomJsonSerializator(List<? extends Object> serializableObjects) {
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
