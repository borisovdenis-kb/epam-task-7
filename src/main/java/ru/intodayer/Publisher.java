package ru.intodayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Publisher {
    private String name;
    private List<Book> publishedBooks = new ArrayList<>();

    public Publisher(String name, Book... publishedBooks) {
        this.name = name;
        Collections.addAll(this.publishedBooks, publishedBooks);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBook(Book book) {
        publishedBooks.add(book);
    }
}
