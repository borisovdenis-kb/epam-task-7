package ru.intodayer.duplicatemodels;

import ru.intodayer.models.Author;
import ru.intodayer.models.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DuplicateBook {
    private String id;
    private String title;
    private LocalDate publishDate;
    private List<DuplicateAuthor> authors = new ArrayList<>();

    public DuplicateBook(Book book) {
        this.title = book.getTitle();
        this.publishDate = book.getPublishDate();
    }

    public void setId(int bookId) {
        this.id = this.getClass().getName() + bookId;
    }

    public String getId() {
        return id;
    }

    public void addAuthor(Author author, int authorId) {
        DuplicateAuthor newAuthor = new DuplicateAuthor(author);
        newAuthor.setId(authorId);
        authors.add(newAuthor);
    }
}
