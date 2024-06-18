package com.projects.lms.librarymanagementsystem;
import java.util.Date;import java.util.Date;

public class Transaction {
    private int id;
    private int bookId;
    private int patronId;
    private Date issueDate;
    private Date returnDate;

    public Transaction(int id, int bookId, int patronId, Date issueDate, Date returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.patronId = patronId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPatronId() {
        return patronId;
    }

    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
