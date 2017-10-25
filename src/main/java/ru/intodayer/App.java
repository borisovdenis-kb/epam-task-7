package ru.intodayer;

import ru.intodayer.models.Publisher;
import ru.intodayer.serializator.CustomJsonSerializer;
import ru.intodayer.serializator.SerializationException;
import ru.intodayer.serializator.SerializerTester;


public class App {
    public static void main(String[] args) throws SerializationException {
        Publisher publisher = TestDataCreator.createPublisher();
        String filePath = "test.json";
        String absoluteFilePath = SerializerTester.getAbsoluteFilePath(filePath);

        CustomJsonSerializer serializer = new CustomJsonSerializer(publisher);
        serializer.serialize(absoluteFilePath);
    }
}
