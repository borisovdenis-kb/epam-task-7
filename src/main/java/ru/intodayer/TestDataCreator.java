package ru.intodayer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class TestDataCreator {
    public static List<Author> createAuthorList() {
        List<Author> authors = new ArrayList<>();
        authors.add(
            new Author(
                "Чингиз Айтматов",
                LocalDate.of(1928, 12, 12),
                LocalDate.of(2008, 6, 10),
                Gender.MAN
            )
        );
        authors.add(
            new Author(
                "Карл Саган",
                LocalDate.of(1934, 11, 23),
                LocalDate.of(1996, 12, 20),
                Gender.MAN
            )
        );
        authors.add(
            new Author(
                "Джоан Роулинг",
                LocalDate.of(1965, 7, 31),
                Gender.WOMAN
            )
        );
        authors.add(
            new Author(
                "Чак Паланик",
                LocalDate.of(1962, 2, 21),
                Gender.MAN
            )
        );
       authors.add(
           new Author(
                "Стивен Кинг",
                LocalDate.of(1947, 9, 21),
                Gender.MAN
           )
        );

        return authors;
    }

    private static <K, V, T> Map<K, V> createMap(
            List<T> collection, Function<T, K> getKey, Function<T, V> getValue) {
        return collection
            .stream()
            .collect(Collectors.toMap(
                getKey,
                getValue
            ));
    }

    public static List<Book> createBookList(List<Author> authors) {
        Map<String, Author> authorMap = createMap(authors, (a -> a.getName()), (a -> a));
        List<Book> books = new ArrayList<>();
        books.add(
            new Book(
                "Буранный полустанок",
                LocalDate.of(1980, 1, 1),
                authorMap.get("Чингиз Айтматов")
            )
        );
        books.add(
            new Book(
                "Уцелевший",
                LocalDate.of(1999, 2, 17),
                authorMap.get("Чак Паланик")
            )
        );
        books.add(
            new Book(
                "Сияние: Наследие",
                LocalDate.of(2016, 1, 1),
                authorMap.get("Джоан Роулинг"),
                authorMap.get("Стивен Кинг")
            )
        );
        books.add(
            new Book(
                "Космос",
                LocalDate.of(1980, 1, 1),
                authorMap.get("Карл Саган")
            )
        );
        books.add(
            new Book(
                "Стихи о науке",
                LocalDate.of(1995, 1, 1),
                authorMap.get("Чингиз Айтматов"),
                authorMap.get("Карл Саган")
            )
        );

        return books;
    }

    public static List<Publisher> createPublisherList(List<Book> books) {
        Map<String, Book> booksMap = createMap(books, ((b) -> b.getTitle()), (b -> b));
        List<Publisher> publishers = new ArrayList<>();
        publishers.add(
            new Publisher(
                "Росмэн",
                booksMap.get("Стихи о науке"),
                booksMap.get("Сияние: Наследие")
            )
        );
        publishers.add(
            new Publisher(
                "Росмэн",
                booksMap.get("Уцелевший"),
                booksMap.get("Космос")
            )
        );
        publishers.add(
            new Publisher(
                "ACT",
                booksMap.get("Уцелевший"),
                booksMap.get("Буранный полустанок")
            )
        );
        return publishers;
    }
}
