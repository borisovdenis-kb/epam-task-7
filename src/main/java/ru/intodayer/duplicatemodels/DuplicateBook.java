package ru.intodayer.duplicatemodels;

import ru.intodayer.models.Author;
import ru.intodayer.models.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class DuplicateBook extends UniqueObject {
    private String title;
    private LocalDate publishDate;
    private List<DuplicateAuthor> authors = new ArrayList<>();

    public DuplicateBook() {
    }

    public DuplicateBook(Book book) {
        this.title = book.getTitle();
        this.publishDate = book.getPublishDate();
    }

    public DuplicateAuthor addAuthor(Author author) {
        DuplicateAuthor newAuthor = new DuplicateAuthor(author);
        authors.add(newAuthor);

        return newAuthor;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public void setAuthors(List<DuplicateAuthor> authors) {
        this.authors = authors;
    }

    public List<DuplicateAuthor> getAuthors() {
        return authors;
    }

    private Author[] castObjectArrayToAuthorArray(Object[] objects) {
        Author[] authors = new Author[objects.length];
        for (int i = 0; i < objects.length; i++) {
            authors[i] = (Author) objects[i];
        }
        return authors;
    }

    public Book getBookFromThis() {
        Object[] objects = this.authors
            .stream()
            .map((a) -> a.getAuthorFromThis())
            .collect(Collectors.toList()).toArray();
        return new Book(
            this.title,
            this.publishDate,
            castObjectArrayToAuthorArray(objects)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DuplicateBook)) return false;

        DuplicateBook that = (DuplicateBook) o;

        if (!title.equals(that.title)) return false;
        if (!publishDate.equals(that.publishDate)) return false;
        return authors.equals(that.authors);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + publishDate.hashCode();
        result = 31 * result + authors.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return title;
    }
}
