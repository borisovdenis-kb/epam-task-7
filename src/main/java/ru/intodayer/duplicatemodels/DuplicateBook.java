package ru.intodayer.duplicatemodels;

import ru.intodayer.models.Author;
import ru.intodayer.models.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DuplicateBook extends UniqueObject {
    private String title;
    private LocalDate publishDate;
    private List<DuplicateAuthor> authors = new ArrayList<>();

    public DuplicateBook(Book book) {
        this.title = book.getTitle();
        this.publishDate = book.getPublishDate();
    }

    public DuplicateAuthor addAuthor(Author author, int authorId) {
        DuplicateAuthor newAuthor = new DuplicateAuthor(author);
        newAuthor.setId(authorId);
        authors.add(newAuthor);

        return newAuthor;
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
}
