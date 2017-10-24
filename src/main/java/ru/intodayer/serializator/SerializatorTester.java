package ru.intodayer.serializator;

import ru.intodayer.Author;
import ru.intodayer.Book;
import ru.intodayer.Publisher;
import ru.intodayer.TestDataCreator;
import java.io.File;
import java.util.List;


public class SerializatorTester {
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

    public static void testStandardSerializator() throws SerializationException {
        List<Publisher> serializableObjects = getSerializableObject();
        StandardSerializator serializator = new StandardSerializator(serializableObjects);

        String filePath = "serializedObjects.dat";
        serializator.serialize(getAbsoluteFilePath(filePath));
    }
}
