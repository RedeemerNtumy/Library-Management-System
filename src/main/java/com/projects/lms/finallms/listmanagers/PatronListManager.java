package com.projects.lms.finallms;

import java.util.ArrayList;
import java.util.List;

public class PatronListManager {
    private List<Patron> patrons;
    private PatronDAO patronDAO;

    public PatronListManager() {
        this.patrons = new ArrayList<>();
        this.patronDAO = new PatronDAO();
        loadPatrons();  // Initialize the patron list by loading from the database
    }

    // Add a patron to the list and the database
    public void addPatron(Patron patron) {
        patronDAO.addPatron(patron);  // Add patron to the database and retrieve generated ID
        patrons.add(patron);         // Add patron to the local list
    }

    // Remove a patron from the list and the database
    public void removePatron(int patronID) {
        patronDAO.removePatron(patronID);  // Remove patron from the database
        patrons.removeIf(p -> p.getPatronID() == patronID);  // Remove patron from the local list
    }

    // Load all patrons from the database
    private void loadPatrons() {
        patrons = patronDAO.getAllPatrons();  // Load patrons from the database
    }

    // Getter for the list of patrons
    public List<Patron> getPatrons() {
        return patrons;
    }
}
