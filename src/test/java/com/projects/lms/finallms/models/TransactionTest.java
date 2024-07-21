package com.projects.lms.finallms.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    private Transaction transaction;
    private Date dateBorrowed;
    private Date dateDue;
    private Date dateReturned;

    @BeforeEach
    void setUp() {
        // Set up sample dates
        dateBorrowed = Date.valueOf("2023-07-01");
        dateDue = Date.valueOf("2023-07-15");
        dateReturned = Date.valueOf("2023-07-10");

        // Initialize a Transaction object before each test
        transaction = new Transaction(1, "Effective Java", 101, "John Doe", dateBorrowed, dateDue);
    }

    @Test
    void getTransactionID() {
        transaction.setTransactionID(500);
        assertEquals(500, transaction.getTransactionID(), "Transaction ID should be returned correctly.");
    }

    @Test
    void setTransactionID() {
        transaction.setTransactionID(300);
        assertEquals(300, transaction.getTransactionID(), "Transaction ID should be set and returned correctly.");
    }

    @Test
    void getBookID() {
        assertEquals(1, transaction.getBookID(), "Book ID should be returned correctly.");
    }

    @Test
    void getBookTitle() {
        assertEquals("Effective Java", transaction.getBookTitle(), "Book title should be returned correctly.");
    }

    @Test
    void getPatronID() {
        assertEquals(101, transaction.getPatronID(), "Patron ID should be returned correctly.");
    }

    @Test
    void getPatronName() {
        assertEquals("John Doe", transaction.getPatronName(), "Patron's name should be returned correctly.");
    }

    @Test
    void getDateBorrowed() {
        assertEquals(dateBorrowed, transaction.getDateBorrowed(), "Date borrowed should be returned correctly.");
    }

    @Test
    void getDateDue() {
        assertEquals(dateDue, transaction.getDateDue(), "Date due should be returned correctly.");
    }

    @Test
    void getDateReturned() {
        transaction.setDateReturned(dateReturned);
        assertEquals(dateReturned, transaction.getDateReturned(), "Date returned should be returned correctly.");
    }

    @Test
    void setDateReturned() {
        Date newReturnDate = Date.valueOf("2023-07-12");
        transaction.setDateReturned(newReturnDate);
        assertEquals(newReturnDate, transaction.getDateReturned(), "Date returned should be set and returned correctly.");
    }
}
