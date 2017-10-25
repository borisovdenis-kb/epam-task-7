package ru.intodayer.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Book  implements Serializable {
    private final String title;
    private final LocalDate publishDate;
    private final List<Author> authors = new ArrayList<>();

    public Book(String title, LocalDate publishDate, Author... authors) {
        this.title = title;
        this.publishDate = publishDate;
        Collections.addAll(this.authors, authors);
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public List<Author> getAuthors() {
        return authors;
    }
}
