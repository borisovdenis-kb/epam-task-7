package ru.intodayer.serializator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class StandardSerializer implements Serializer {
    private Object serializableObject = new ArrayList<>();

    public StandardSerializer(Object serializableObject) {
        this.serializableObject = serializableObject;
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
            oos.writeObject(serializableObject);
        } catch (Exception e) {
            throw buildException(e, outFilePath);
        }
    }

    @Override
    public Object deserialize(String inFilePath) throws SerializationException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(inFilePath))) {
            return (Object) ois.readObject();
        } catch (Exception e) {
            throw buildException(e, inFilePath);
        }
    }
}
