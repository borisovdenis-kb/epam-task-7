package ru.intodayer.duplicatemodels;

import ru.intodayer.models.Author;
import ru.intodayer.models.Book;
import ru.intodayer.models.Publisher;
import java.util.*;


public class DuplicatePublisher {
    private transient int currentBookId = 1;
    private transient int currentAuthorId = 1;
    private String name;
    private Map<String, UniqueObject> uniqueObjectMap = new TreeMap<>();
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

    private void showUniqueObjectsMap() {
        Iterator itr = uniqueObjectMap.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, UniqueObject> pair = (Map.Entry) itr.next();
            System.out.println(pair.getKey() + ": " + pair.getValue());
        }
    }

    private void setAllInnerObjects(Publisher publisher) {
        for (Book book: publisher.getPublishedBooks()) {
            DuplicateBook newDupBook = addBook(book);
            if (!uniqueObjectMap.containsKey(newDupBook.getId())) {
                newDupBook.setId(getNextBookId());
                addUniqueObject(newDupBook);
            }
            for (Author author: book.getAuthors()) {
                DuplicateAuthor newDupAuthor = newDupBook.addAuthor(author);
                if (!uniqueObjectMap.containsKey(newDupAuthor.getId())) {
                    newDupAuthor.setId(getNextAuthorId());
                    addUniqueObject(newDupAuthor);
                }
            }
        }
    }
}
