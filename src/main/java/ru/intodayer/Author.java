package ru.intodayer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Author {
    private final String name;
    private final LocalDate birthDay;
    private final Gender gender;
    private LocalDate deathDay;

    public Author(String name, LocalDate birthDay, Gender gender) {
        this.name = name;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    public Author(String name, LocalDate birthDay, LocalDate deathDay, Gender gender) {
        this(name, birthDay, gender);
        this.deathDay = deathDay;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public LocalDate getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(LocalDate deathDay) {
        this.deathDay = deathDay;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getAge() {
        if (deathDay != null) {
            return (int) ChronoUnit.YEARS.between(birthDay, deathDay);
        } else {
            return (int) ChronoUnit.YEARS.between(birthDay, LocalDate.now());
        }
    }

    @Override
    public String toString() {
        return name + "(" + getAge() + ")";
    }
}
