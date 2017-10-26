package ru.intodayer.duplicatemodels;

import ru.intodayer.models.Author;
import ru.intodayer.models.Book;
import ru.intodayer.models.Publisher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DuplicatePublisher {
    private transient int currentBookId = 1;
    private transient int currentAuthorId = 1;
    private transient Map<String, UniqueObject> uniqueObjectMap = new HashMap<>();
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

    private <T extends UniqueObject> void addUniqueObject(T object) {
        if (!uniqueObjectMap.containsKey(object.getId())) {
            uniqueObjectMap.put(object.getId(), object);
        }
    }

    private void setAllInnerObjects(Publisher publisher) {
        for (Book book: publisher.getPublishedBooks()) {
            DuplicateBook newDupBook = addBook(book, getNextBookId());
            addUniqueObject(newDupBook);
            for (Author author: book.getAuthors()) {
                DuplicateAuthor newDupAuthor = newDupBook.addAuthor(author, getNextAuthorId());
                addUniqueObject(newDupAuthor);
            }
        }
    }
}
