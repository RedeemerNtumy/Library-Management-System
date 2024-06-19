package com.projects.lms.finallms;

import java.util.Date;

public class Transaction {
    private int transactionID;  // Auto-incremented by the database
    private int bookID;
    private int patronID;
    private String patronName;  // Assuming we store the patron's name for display purposes
    private Date dateBorrowed;
    private Date dateDue;
    private Date dateReturned;

    public Transaction(int bookID, int patronID, Date dateBorrowed, Date dateDue) {
        this.bookID = bookID;
        this.patronID = patronID;
        this.dateBorrowed = dateBorrowed;
        this.dateDue = dateDue;
        this.dateReturned = null;  // New transactions have no return date initially
    }

    public Transaction(int transactionID, int bookID, String patronName, Date dateBorrowed, Date dateDue, Date dateReturned) {
        this.transactionID = transactionID;
        this.bookID = bookID;
        this.patronName = patronName;
        this.dateBorrowed = dateBorrowed;
        this.dateDue = dateDue;
        this.dateReturned = dateReturned;
    }

    // Getters and Setters
    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getPatronID() {
        return patronID;
    }

    public void setPatronID(int patronID) {
        this.patronID = patronID;
    }

    public String getPatronName() {
        return patronName;
    }

    public void setPatronName(String patronName) {
        this.patronName = patronName;
    }

    public Date getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(Date dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }
}
