package ru.intodayer.duplicatemodels;

import ru.intodayer.models.Author;
import ru.intodayer.models.Book;
import ru.intodayer.models.Publisher;

import java.util.*;


public class DuplicatePublisher {
    private transient int currentBookId = 1;
    private transient int currentAuthorId = 1;
    private transient Set<UniqueObject> alreadyAdded = new HashSet<>();
    private String name;
    private Map<String, UniqueObject> uniqueObjectMap = new HashMap<>();
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

    private DuplicateBook addBook(Book book) {
        DuplicateBook newBook = new DuplicateBook(book);
        publishedBooks.add(newBook);

        return newBook;
    }

    private <T extends UniqueObject> void addUniqueObject(T object) {
        uniqueObjectMap.put(object.getId(), object);
    }

    private void setAllInnerObjects(Publisher publisher) {
        for (Book book: publisher.getPublishedBooks()) {
            DuplicateBook newDupBook = addBook(book);
            if (!alreadyAdded.contains(newDupBook)) {
                newDupBook.setId(getNextBookId());
                addUniqueObject(newDupBook);
                alreadyAdded.add(newDupBook);
            }
            for (Author author: book.getAuthors()) {
                DuplicateAuthor newDupAuthor = newDupBook.addAuthor(author, getNextAuthorId());
                if (!alreadyAdded.contains(newDupAuthor)) {
                    newDupAuthor.setId(getNextAuthorId());
                    addUniqueObject(newDupAuthor);
                    alreadyAdded.add(newDupAuthor);
                }
            }
        }
    }
}
