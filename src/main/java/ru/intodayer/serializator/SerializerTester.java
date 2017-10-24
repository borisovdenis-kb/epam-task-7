package ru.intodayer.serializator;

import ru.intodayer.Author;
import ru.intodayer.Book;
import ru.intodayer.Publisher;
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

    private static String getAbsoluteFilePath(String relativePath) {
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

    public static void testStandardSerializator() throws SerializationException {
        List<Publisher> serializableObjects = getSerializableObject();
        StandardSerializer serializator = new StandardSerializer(serializableObjects);
        String filePath = "serializedObjects.dat";
        String absoluteFilePath = getAbsoluteFilePath(filePath);

        System.out.println("Serializing objects by standard (ObjectInputStream)...\n");
        serializator.serialize(absoluteFilePath);

        System.out.println("Deserializing objects by standard (ObjectOutputStream): ");
        List<Publisher> publishers = castAllObjectsToPublisher(serializator.deserialize(absoluteFilePath));

        for (Publisher p: publishers) {
            System.out.println(p);
        }
    }
}
