package ru.intodayer.duplicatemodels;


public class UniqueObject {
    protected String id;

    public void setId(int id) {
//        this.id = this.getClass().getSimpleName() + id;
        this.id = String.valueOf(this.hashCode());
    }

    public String getId() {
//        return id;
//        return String.valueOf(this.getClass().getSimpleName() + this.hashCode());
        return this.getClass().getSimpleName() + this.hashCode();
    }
}
