package com.projects.lms.finallms.testdao;

import com.projects.lms.finallms.dao.PatronDAO;
import com.projects.lms.finallms.models.Patron;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PatronDAOTest {

    @Mock
    public PatronDAO patronDAO;
    public AutoCloseable closeable;

    @InjectMocks
    private Patron patron;



    @BeforeEach
    void setUp() {
         closeable = MockitoAnnotations.openMocks(this);
        patron = new Patron("The man","theman@gmail.com");
        lenient().when(patronDAO.getAllPatrons()).thenReturn(new LinkedList<>(List.of(patron)));
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void addPatronCheckName() {
        assertEquals("The man", patronDAO.getAllPatrons().getLast().getName());
    }

    @Test
    void addPatronCheckEmail() {
        assertEquals("theman@gmail.com", patronDAO.getAllPatrons().getLast().getEmail());
    }
    @ParameterizedTest
    @CsvSource({
            "The man, theman@gmail.com",
            "Another Man, anotherman@example.com"
    })
    void addPatronParametrized(String name, String email) {
        Patron newPatron = new Patron(name, email);  // No stubbing needed just to create an object
        patronDAO.addPatron(newPatron);
        verify(patronDAO, times(1)).addPatron(newPatron);  // Verify the call
        assertEquals(email, newPatron.getEmail());  // Check the object's state, no DAO interaction
        assertEquals(name, newPatron.getName());    // Check the object's state, no DAO interaction
    }

    @Test
    void addPatronWrongEmail() {
        /*
        Ideally this should not be added to
        the database since it is an invalid email but it added,
        which means it has failed the test.
         */
        Patron wrongEmailPatron = new Patron("Testing", "wrongemail");
        when(patronDAO.getAllPatrons()).thenReturn(new LinkedList<>(List.of(wrongEmailPatron)));
        assertNotEquals("wrongemail", patronDAO.getAllPatrons().getLast().getEmail());
    }

    @Test
    void removePatron() {
        patronDAO.removePatron(patron.getPatronID());
        verify(patronDAO, times(1)).removePatron(patron.getPatronID());
    }

    @Test
    void getAllPatrons() {
        assertNotNull(patronDAO.getAllPatrons());
        assertFalse(patronDAO.getAllPatrons().isEmpty());
        assertEquals("The man", patronDAO.getAllPatrons().getFirst().getName());
    }
}
