package ru.intodayer;

import ru.intodayer.models.Author;
import ru.intodayer.models.Book;
import ru.intodayer.models.Gender;
import ru.intodayer.models.Publisher;
import ru.intodayer.serializator.CustomJsonSerializer;
import ru.intodayer.serializator.SerializationException;
import ru.intodayer.serializator.Serializer;
import ru.intodayer.serializator.SerializerTester;

import java.time.LocalDate;


public class App {
    public static void main(String[] args) throws SerializationException {
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
        String absoluteFilePath = SerializerTester.getAbsoluteFilePath(filePath);

        CustomJsonSerializer serializer = new CustomJsonSerializer(publisherRosman);
        serializer.serialize(absoluteFilePath);
    }
}
