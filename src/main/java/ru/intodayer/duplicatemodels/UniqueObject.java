package ru.intodayer.duplicatemodels;


public class UniqueObject {
    protected String id;

    public void setId(int id) {
        this.id = this.getClass().getSimpleName() + id;
    }

    public String getId() {
        return id;
    }
}
