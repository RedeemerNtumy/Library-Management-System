package com.projects.lms.finallms.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatronTest {
    private Patron patron;

    @BeforeEach
    void setUp() {
        // Initialize a Patron object before each test
        patron = new Patron("John Doe", "johndoe@example.com");
        patron.setPatronID(10);  // Set a sample patron ID
    }

    @Test
    void getName() {
        assertEquals("John Doe", patron.getName(), "Patron's name should be returned correctly.");
    }

    @Test
    void getEmail() {
        assertEquals("johndoe@example.com", patron.getEmail(), "Patron's email should be returned correctly.");
    }

    @Test
    void getPatronID() {
        assertEquals(10, patron.getPatronID(), "Patron ID should be returned correctly.");
    }

    @Test
    void setPatronID() {
        patron.setPatronID(20);
        assertEquals(20, patron.getPatronID(), "Patron ID should be updated and returned correctly.");
    }

    @Test
    void testToString() {
        assertEquals("John Doe", patron.toString(), "toString should return the patron's name.");
    }
}
