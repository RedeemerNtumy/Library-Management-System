package com.projects.lms.finallms.models;

public class Patron extends User {
    private int patronID;  // Auto-incremented by the database

    public Patron(String name, String email) {
        super(name, email);
    }


    public int getPatronID() {
        return patronID;
    }

    public void setPatronID(int patronID) {
        this.patronID = patronID;
    }

    @Override
    public String toString() {
        return getName();  // Return the patron's name for display in the ComboBox
    }
}
