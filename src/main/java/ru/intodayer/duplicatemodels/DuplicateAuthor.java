package ru.intodayer.duplicatemodels;

import ru.intodayer.models.Author;
import ru.intodayer.models.Gender;
import java.time.LocalDate;


public class DuplicateAuthor {
    private String id;
    private final String name;
    private final LocalDate birthDay;
    private final Gender gender;
    private LocalDate deathDay;

    public DuplicateAuthor(Author author) {
        this.name = author.getName();
        this.birthDay = author.getBirthDay();
        this.deathDay = author.getDeathDay();
        this.gender = author.getGender();
    }

    public void setId(int authorId) {
        this.id = this.getClass().getName() + authorId;
    }

    public String getId() {
        return id;
    }
}
