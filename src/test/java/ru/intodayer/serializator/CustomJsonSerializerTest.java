package ru.intodayer.serializator;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import ru.intodayer.models.Author;
import ru.intodayer.models.Book;
import ru.intodayer.models.Gender;
import ru.intodayer.models.Publisher;

import java.time.LocalDate;


public class CustomJsonSerializerTest extends TestCase {
    @Test
    public void testSerialize() throws Exception {
        Author authorPalanik = new Author(
            "Чак Паланик",
            LocalDate.of(1962, 2, 21),
            Gender.MAN
        );
        Book bookSurvivor = new Book(
            "Уцелевший",
            LocalDate.of(1999, 2, 17),
            authorPalanik
        );
        Publisher publisherRosman = new Publisher(
            "Росмэн",
            bookSurvivor
        );
        String filePath = "test.json";
        String absoluteFilePath = TestUtility.getAbsoluteFilePath(filePath);

        CustomJsonSerializer serializer = new CustomJsonSerializer(publisherRosman);
        serializer.serialize(absoluteFilePath);
    }

    @Test
    public void testDeserialize() throws Exception {
    }

}