package ru.intodayer.serializator;

import ru.intodayer.models.Author;
import ru.intodayer.models.Book;
import ru.intodayer.models.Publisher;
import ru.intodayer.TestDataCreator;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SerializerTester {
    private static final String RESOURCES_PATH = "src\\main\\resources\\";

    private static List<Publisher> getSerializableObject() {
        List<Author> authors = TestDataCreator.createAuthorList();
        List<Book> books = TestDataCreator.createBookList(authors);
        return TestDataCreator.createPublisherList(books);
    }

    public static String getAbsoluteFilePath(String relativePath) {
        String workingDir = System.getProperty("user.dir");
        return String.format(
            "%s%s%s%s", workingDir, File.separator, RESOURCES_PATH, relativePath
        );
    }

    private static List<Publisher> castAllObjectsToPublisher(List<Object> objects) {
        List<Publisher> publishers = new ArrayList<>();
        for (Object obj: objects) {
            publishers.add((Publisher) obj);
        }
        return publishers;
    }

    public static void testStandardSerializer() throws SerializationException {
        Publisher serObject = getSerializableObject().get(0);
        StandardSerializer serializer = new StandardSerializer(serObject);
        String filePath = "serializedObjects.dat";
        String absoluteFilePath = getAbsoluteFilePath(filePath);

        System.out.println("Serializing objects by standard (ObjectInputStream)...\n");
        serializer.serialize(absoluteFilePath);

        System.out.println("Deserializing objects by standard (ObjectOutputStream): ");
        Publisher deserObject = (Publisher) serializer.deserialize(absoluteFilePath);

        System.out.println(deserObject);
        System.out.println();
    }

    public static void testCustomJsonSerializer() throws SerializationException {
        Publisher serObject = getSerializableObject().get(0);
        CustomJsonSerializer serializer = new CustomJsonSerializer(serObject);
        String filePath = "test.json";
        String absoluteFilePath = getAbsoluteFilePath(filePath);

        System.out.println("Serializing objects by custom json serialization...\n");
        serializer.serialize(absoluteFilePath);

        System.out.println("Deserializing objects by custom json deserialization: ");
        Publisher deserObject = (Publisher) serializer.deserialize(absoluteFilePath);

        System.out.println(deserObject);
        System.out.println();
    }
}
