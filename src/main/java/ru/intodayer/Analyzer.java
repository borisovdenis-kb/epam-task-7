package ru.intodayer;

import javafx.util.Pair;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.*;


public class Analyzer {
    private static <T> boolean collectionIsEmpty(Collection<T> collection) {
        return (collection == null && collection.isEmpty());
    }

    public static Double getAverageAuthorAge(Collection<Author> authors) {
        if (collectionIsEmpty(authors))
            return null;
        return authors.stream().collect(Collectors.averagingInt(Author::getAge));
    }

    public static List<Author> sortAuthorsByAge(Collection<Author> authors) {
        if (collectionIsEmpty(authors))
            return null;
        return authors
                .stream()
                .sorted(Comparator.comparing(Author::getAge))
                .collect(Collectors.toList());
    }

    public static List<Author> filterPensioners(Collection<Author> authors) {
        if (collectionIsEmpty(authors))
            return null;

        Predicate<Author> siftYoung = (a) -> {
            int age = a.getAge();
            return  (a.getGender() == Gender.MAN ? age > 65 : age > 63);
        };
        return authors.stream().filter(siftYoung).collect(Collectors.toList());
    }

    public static Map<String, Integer> mapBookTitlesToAge(Collection<Book> books) {
        if (collectionIsEmpty(books))
            return null;

        return books
            .stream().collect(Collectors.toMap(
                (book) -> book.getTitle(),
                (book) -> (int) ChronoUnit.YEARS.between(book.getPublishDate(), LocalDate.now())
            ));
    }

    public static Set<Author> getCollaborativeAuthors(Collection<Book> books) {
        if (collectionIsEmpty(books))
            return null;

        return books
            .stream()
            .flatMap(book -> book.getAuthors().size() < 2 ? null : book.getAuthors().stream())
            .collect(Collectors.toSet());
    }

    public static Map<String, Set<String>> mapAuthorsToTheirBooks(Collection<Book> books) {
        if (collectionIsEmpty(books))
            return null;

        return books
            .stream()
            .flatMap(book -> book.getAuthors().stream().map(a -> new Pair<>(a, book)))
            .collect(Collectors.groupingBy(
                    a->a.getKey().getName(),
                    Collectors.mapping(a->a.getValue().getTitle(), Collectors.toSet())
            ));
    }

    public static void demonstrateWorkOfAnalyzer() {
        List<Author> authors = TestDataCreator.createAuthorList();
        List<Book> books = TestDataCreator.createBookList(authors);

        System.out.println("Average age of authors:        " + Analyzer.getAverageAuthorAge(authors));
        System.out.println("Books and their ages:          " + Analyzer.mapBookTitlesToAge(books));
        System.out.println("Pensioners:                    " + Analyzer.filterPensioners(authors));
        System.out.println("Authors and their books:       " + Analyzer.mapAuthorsToTheirBooks(books));
        System.out.println("Sorting authors by age (asc.): " + Analyzer.sortAuthorsByAge(authors));
        System.out.println("List of collaborative authors: " + Analyzer.getCollaborativeAuthors(books));
    }
}
