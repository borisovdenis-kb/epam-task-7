package ru.intodayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Publisher implements Serializable {
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

    @Override
    public String toString() {
        return "Publisher{" + name + "}";
    }
}
