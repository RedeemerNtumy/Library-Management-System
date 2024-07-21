package com.projects.lms.finallms.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        // Initialize a User object before each test
        user = new User("John Doe", "johndoe@example.com");
    }

    @AfterEach
    void tearDown() {
        user = null;  // Clear the reference after each test
    }

    @Test
    void getName() {
        assertEquals("John Doe", user.getName(), "The getName method should return the correct name.");
    }

    @Test
    void getEmail() {
        assertEquals("johndoe@example.com", user.getEmail(), "The getEmail method should return the correct email.");
    }
}
