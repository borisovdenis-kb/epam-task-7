package ru.intodayer.duplicatemodels;

import ru.intodayer.models.Author;
import ru.intodayer.models.Gender;
import java.time.LocalDate;


public class DuplicateAuthor extends UniqueObject {
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
        this.id = this.getClass().getSimpleName() + authorId;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DuplicateAuthor)) return false;

        DuplicateAuthor that = (DuplicateAuthor) o;

        if (!name.equals(that.name)) return false;
        if (!birthDay.equals(that.birthDay)) return false;
        return gender.equals(that.gender);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + birthDay.hashCode();
        result = 31 * result + gender.hashCode();
        return result;
    }
}
