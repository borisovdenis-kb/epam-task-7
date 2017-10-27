package ru.intodayer;

import ru.intodayer.duplicatemodels.DuplicateAuthor;
import ru.intodayer.duplicatemodels.DuplicatePublisher;
import ru.intodayer.models.Author;
import ru.intodayer.models.Gender;
import ru.intodayer.models.Publisher;
import ru.intodayer.serializator.CustomJsonSerializer;
import ru.intodayer.serializator.SerializationException;
import ru.intodayer.serializator.SerializerTester;

import java.time.LocalDate;


public class App {
    public static void main(String[] args) throws SerializationException {
        Publisher publisher = TestDataCreator.createPublisher();
        DuplicatePublisher dupPublisher = new DuplicatePublisher(publisher);

        String filePath = "test.json";
        String absoluteFilePath = SerializerTester.getAbsoluteFilePath(filePath);

        CustomJsonSerializer serializer = new CustomJsonSerializer(dupPublisher);
        serializer.serialize(absoluteFilePath);
//        serializer.deserialize(absoluteFilePath);
    }
}
