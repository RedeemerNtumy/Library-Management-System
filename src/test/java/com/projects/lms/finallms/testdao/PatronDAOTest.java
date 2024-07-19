package com.projects.lms.finallms.testdao;

import com.projects.lms.finallms.dao.PatronDAO;
import com.projects.lms.finallms.models.Patron;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InvalidClassException;

import static org.junit.jupiter.api.Assertions.*;

class PatronDAOTest {
    public Patron patron;
    public PatronDAO patronDAO;

    @BeforeEach
    void setUp() {
        patron = new Patron("The man","theman@gmail.com");
        patronDAO = new PatronDAO();
        patronDAO.addPatron(patron);
    }

    @AfterEach
    void tearDown() {
       patronDAO.removePatron(patron.getPatronID());
    }

    @Test
    void addPatronCheckName() {
        assertEquals("The man",patronDAO.getAllPatrons().getLast().getName());
    }

    @Test
    void addPatronCheckEmail() {
        assertEquals("theman@gmail.com",patronDAO.getAllPatrons().getLast().getEmail());
    }

    @Test
    void addPatronWrongEmail() {
        /*
        Ideally, a wrong email should not be added but this was not catered for in the code so it should fail
         */
        Patron patron = new Patron("Testing","wrongemail");
        PatronDAO patronDAO = new PatronDAO();
        patronDAO.addPatron(patron);
        assertNotEquals(patron.getEmail(),patronDAO.getAllPatrons().getLast().getEmail());
        patronDAO.removePatron(patron.getPatronID());


    }

    @Test
    void removePatron() {
        fail("Test not yet implemented");
    }

    @Test
    void getAllPatrons() {
        fail("Test not yet implemented");
    }
}