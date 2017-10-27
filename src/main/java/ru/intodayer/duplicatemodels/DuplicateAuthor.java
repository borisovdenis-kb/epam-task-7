package ru.intodayer.duplicatemodels;

import ru.intodayer.models.Author;
import ru.intodayer.models.Gender;
import java.time.LocalDate;


public class DuplicateAuthor extends UniqueObject {
    private String name;
    private LocalDate birthDay;
    private Gender gender;
    private LocalDate deathDay;

    public DuplicateAuthor(){
    }

    public DuplicateAuthor(Author author) {
        this.name = author.getName();
        this.birthDay = author.getBirthDay();
        this.deathDay = author.getDeathDay();
        this.gender = author.getGender();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setDeathDay(LocalDate deathDay) {
        this.deathDay = deathDay;
    }

    public Author getAuthorFromThis() {
        return new Author(
            this.name, this.birthDay, this.deathDay, this.gender
        );
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

    @Override
    public String toString() {
        return name;
    }
}
