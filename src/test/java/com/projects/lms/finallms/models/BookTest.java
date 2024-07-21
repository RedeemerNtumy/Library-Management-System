package com.projects.lms.finallms.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private Book book;

    @BeforeEach
    void setUp() {
        // Initialize a Book object before each test with some default values
        book = new Book("Effective Java", "Joshua Bloch", "0321356683");
        book.setBookID(1);  // Assume the book ID is set as it would be in a database environment
    }

    @Test
    void getBookID() {
        assertEquals(1, book.getBookID(), "Book ID should be returned correctly.");
    }

    @Test
    void setBookID() {
        book.setBookID(2);
        assertEquals(2, book.getBookID(), "Book ID should be set and returned correctly.");
    }

    @Test
    void getTitle() {
        assertEquals("Effective Java", book.getTitle(), "Book title should be returned correctly.");
    }

    @Test
    void getAuthor() {
        assertEquals("Joshua Bloch", book.getAuthor(), "Book author should be returned correctly.");
    }

    @Test
    void getIsbn() {
        assertEquals("0321356683", book.getIsbn(), "Book ISBN should be returned correctly.");
    }

    @Test
    void isAvailable() {
        assertTrue(book.isAvailable(), "Book availability should initially be true.");
    }

    @Test
    void setAvailable() {
        book.setAvailable(false);
        assertFalse(book.isAvailable(), "Book availability should be set to false and returned as such.");
    }
}
