package ru.intodayer.duplicatemodels;

import ru.intodayer.models.Author;
import ru.intodayer.models.Book;
import ru.intodayer.models.Publisher;
import java.util.ArrayList;
import java.util.List;


public class DuplicatePublisher {
    private int currentBookId = 1;
    private int currentAuthorId = 1;
    private String name;
    private List<DuplicateBook> publishedBooks = new ArrayList<>();

    public DuplicatePublisher(Publisher publisher) {
        this.name = publisher.getName();
        setAllInnerObjects(publisher);
    }

    private int getNextBookId() {
        return currentBookId++;
    }

    private int getNextAuthorId() {
        return currentAuthorId++;
    }

    private DuplicateBook addBook(Book book, int bookId) {
        DuplicateBook newBook = new DuplicateBook(book);
        newBook.setId(bookId);
        publishedBooks.add(newBook);

        return newBook;
    }

    private void setAllInnerObjects(Publisher publisher) {
        for (Book book: publisher.getPublishedBooks()) {
            DuplicateBook newDupBook = addBook(book, getNextBookId());
            for (Author author: book.getAuthors()) {
                newDupBook.addAuthor(author, getNextAuthorId());
            }
        }
    }
}
