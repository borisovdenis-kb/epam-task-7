package ru.intodayer.serializator;

import java.io.IOException;
import java.util.List;


public interface Serializator {
    void serialize(String outFilePath) throws SerializationException;
    List<Object> deserialize(String inFilePath) throws SerializationException;
}
