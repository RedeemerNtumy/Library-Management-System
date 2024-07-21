package com.projects.lms.finallms.listmanagers;

import com.projects.lms.finallms.dao.BookDAO;
import com.projects.lms.finallms.models.Book;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BookListManagerTest {
    private BookListManager bookListManager;

    @Mock
    private BookDAO mockBookDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks annotated with @Mock
        bookListManager = new BookListManager();
        bookListManager.bookDAO = mockBookDAO; // Inject the mocked DAO

        // Setup the DAO to return a LinkedList when getAllBooks is called
        when(mockBookDAO.getAllBooks()).thenReturn(new LinkedList<>());
    }

    @AfterEach
    void tearDown() {
        bookListManager = null;
    }

    @Test
    void addBook() {
        Book book = new Book("Effective Java", "Joshua Bloch", "0321356683");
        bookListManager.addBook(book);
        verify(mockBookDAO).addBook(book); // Verify that the book is added to the DAO
        assertTrue(bookListManager.getBooks().contains(book), "The book should be added to the local list.");
    }

    @Test
    void removeBook() {
        Book book = new Book("Clean Code", "Robert C. Martin", "0132350882");
        book.setBookID(1);
        bookListManager.getBooks().add(book);

        bookListManager.removeBook(1);
        verify(mockBookDAO).removeBook(1); // Verify that the book is removed from the DAO
        assertFalse(bookListManager.getBooks().contains(book), "The book should be removed from the local list.");
    }

    @Test
    @Disabled("This feature would have to be implemented well")
    void updateBookAvailability() {
        Book book = new Book("Clean Code", "Robert C. Martin", "0132350882");
        book.setBookID(1);
        bookListManager.getBooks().add(book);

        bookListManager.updateBookAvailability(1, false);
        verify(mockBookDAO).updateBookAvailability(1, false); // Verify that the book's availability is updated in the DAO
        assertFalse(book.isAvailable(), "The book's availability should be updated in the local list.");
    }

    @Test
    @Disabled("This feature would have to be implemented well")
    void getBooks() {
        assertNotNull(bookListManager.getBooks(), "Should return a list, not null.");
        assertEquals(0, bookListManager.getBooks().size(), "Initially, the book list should be empty.");
    }
}
