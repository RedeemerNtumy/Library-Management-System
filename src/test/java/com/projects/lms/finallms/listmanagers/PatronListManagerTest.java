package com.projects.lms.finallms.listmanagers;

import com.projects.lms.finallms.dao.PatronDAO;
import com.projects.lms.finallms.models.Patron;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PatronListManagerTest {
    private PatronListManager patronListManager;

    @Mock
    private PatronDAO mockPatronDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks annotated with @Mock
        patronListManager = new PatronListManager();
        patronListManager.patronDAO = mockPatronDAO; // Inject the mocked DAO

        // Setup the DAO to return a List when getAllPatrons is called
        when(mockPatronDAO.getAllPatrons()).thenReturn(new ArrayList<>());
    }

    @AfterEach
    void tearDown() {
        patronListManager = null;
    }

    @Test
    void addPatron() {
        Patron patron = new Patron("Jane Doe", "jane.doe@example.com");
        patronListManager.addPatron(patron);
        verify(mockPatronDAO).addPatron(patron); // Verify that the patron is added to the DAO
        assertTrue(patronListManager.getPatrons().contains(patron), "The patron should be added to the local list.");
    }

    @Test
    void removePatron() {
        Patron patron = new Patron("John Doe", "john.doe@example.com");
        patron.setPatronID(1); // Set ID to simulate a real-world scenario where ID is assigned by the DB
        patronListManager.getPatrons().add(patron);

        patronListManager.removePatron(1);
        verify(mockPatronDAO).removePatron(1); // Verify that the patron is removed from the DAO
        assertFalse(patronListManager.getPatrons().contains(patron), "The patron should be removed from the local list.");
    }

    @Test
    void getPatrons() {
        List<Patron> patrons = patronListManager.getPatrons();
        assertNotNull(patrons, "Should return a list, not null.");
        assertFalse(patrons.isEmpty(), "Initially, the patron list should be empty if the DAO returns an empty list.");
    }
}
