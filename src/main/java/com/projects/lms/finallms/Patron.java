package com.projects.lms.finallms;

public class Patron {
    private int patronID;  // Auto-incremented by the database
    private String name;
    private String email;

    public Patron(String name, String email) {
        this.name = name;
        this.email = email;
    }
    // Getters and Setters
    public int getPatronID() {
        return patronID;
    }
    public void setPatronID(int patronID) {
        this.patronID = patronID;
    }
    public String getName() {return name;}
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    @Override
    public String toString() {return name;}  // Return the patron's name for display in the ComboBox
}
