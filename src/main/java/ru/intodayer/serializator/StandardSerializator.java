package ru.intodayer.serializator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class StandardSerializator implements Serializator {
    private List<? extends Object> serializableObjects = new ArrayList<>();

    public StandardSerializator(List<? extends Object> serializableObjects) {
        this.serializableObjects = serializableObjects;
    }

    private SerializationException buildException(Exception exp, String filePath) {
        try {
            throw exp;
        } catch (FileNotFoundException e) {
            return new SerializationException(
                "File with name " + filePath + " not found.", e
            );
        } catch (ClassNotFoundException | IOException e) {
            return new SerializationException(exp.getMessage(), e);
        } catch (Exception e) {
            return new SerializationException("", e);
        }
    }

    @Override
    public void serialize(String outFilePath) throws SerializationException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outFilePath))) {
            oos.writeObject(serializableObjects);
        } catch (Exception e) {
            throw buildException(e, outFilePath);
        }
    }

    @Override
    public List<Object> deserialize(String inFilePath) throws SerializationException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(inFilePath))) {
            return (List<Object>) ois.readObject();
        } catch (Exception e) {
            throw buildException(e, inFilePath);
        }
    }
}
