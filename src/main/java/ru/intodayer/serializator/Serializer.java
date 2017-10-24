package ru.intodayer.serializator;

import java.io.IOException;
import java.util.List;


public interface Serializer {
    void serialize(String outFilePath) throws SerializationException;
    Object deserialize(String inFilePath) throws SerializationException;
}
