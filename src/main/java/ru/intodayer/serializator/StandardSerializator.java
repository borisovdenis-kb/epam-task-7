package ru.intodayer.serializator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class StandardSerializator implements Serializator {
    private List<Object> serializableObjects = new ArrayList<>();

    public StandardSerializator(List<Object> serializableObjects) {
        this.serializableObjects = serializableObjects;
    }

    @Override
    public void serialize(String outFilePath) throws SerializationException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outFilePath))) {
            oos.writeObject(serializableObjects);
        } catch (FileNotFoundException e) {
            throw new SerializationException(
                "File with name " + outFilePath + " not found.", e
            );
        } catch (IOException e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }

    @Override
    public List<Object> deserialize(String inFilePath) throws SerializationException {
        List<Object> objects = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(inFilePath))) {
            objects = (ArrayList<Object>) ois.readObject();
            return objects;
        } catch (ClassNotFoundException e) {
            throw new SerializationException(e.getMessage(), e);
        } catch (FileNotFoundException e) {
            throw new SerializationException(
                "File with name " + inFilePath + " not found.", e
            );
        } catch (IOException e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }
}
