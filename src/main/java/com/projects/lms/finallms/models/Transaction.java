package com.projects.lms.finallms.models;

import java.sql.Date;

public class Transaction {
    private int transactionID;  // Auto-incremented by the database
    private int bookID;
    private String bookTitle;   // Added book title for display purposes
    private int patronID;
    private String patronName;  // Assuming we store the patron's name for display purposes
    private Date dateBorrowed;
    private Date dateDue;
    private Date dateReturned;

    public Transaction(int bookID, String bookTitle, int patronID, String patronName, Date dateBorrowed, Date dateDue) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.patronID = patronID;
        this.patronName = patronName;
        this.dateBorrowed = dateBorrowed;
        this.dateDue = dateDue;
        this.dateReturned = null;  // New transactions have no return date initially
    }

    public Transaction(int transactionID, int bookID, String bookTitle, String patronName, Date dateBorrowed, Date dateDue, Date dateReturned) {
        this.transactionID = transactionID;
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.patronName = patronName;
        this.dateBorrowed = dateBorrowed;
        this.dateDue = dateDue;
        this.dateReturned = dateReturned;
    }
    // Getters and Setters
    public int getTransactionID() {return transactionID;}
    public void setTransactionID(int transactionID) {this.transactionID = transactionID;}
    public int getBookID() {return bookID;}
    public String getBookTitle() {return bookTitle;}
    public int getPatronID() {
        return patronID;
    }
    public String getPatronName() {
        return patronName;
    }
    public Date getDateBorrowed() {
        return dateBorrowed;
    }
    public Date getDateDue() {
        return dateDue;
    }
    public Date getDateReturned() {
        return dateReturned;
    }
    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }



}
