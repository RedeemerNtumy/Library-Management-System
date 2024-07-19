package com.projects.lms.finallms.testdao;

import com.projects.lms.finallms.dao.BookDAO;
import com.projects.lms.finallms.models.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.LinkedList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BookDAOTest {
    @Mock
    public BookDAO bookDAO;
    public AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        Book book = new Book("A week indeed", "Redeemer Ntumy", "511");
        when(bookDAO.getAllBooks()).thenReturn(new LinkedList<>(Collections.singletonList(book)));
        when(bookDAO.bookExistsByISBN("511")).thenReturn(false);

    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @ParameterizedTest
    @CsvSource({
            "New, Redeemer Ntumy, 511",
            "This is a story, James Harden, 600"
    })
    void addBookCheckProperties(String title, String author, String isbn) {
        Book book = new Book(title, author, isbn);
        bookDAO.addBook(book);
        verify(bookDAO, times(1)).addBook(book);
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(isbn, book.getIsbn());
        assertEquals(true,book.isAvailable());


    }

    @ParameterizedTest
    @CsvSource({
            "31",
            "65",
            "54"
    })
    void removeBook(int id) {
        bookDAO.removeBook(id);
        verify(bookDAO).removeBook(id);
    }


    @ParameterizedTest
    @ValueSource(booleans = {true, false,true})
    void updateBookAvailability(boolean availability) {
        Book lastBook = bookDAO.getAllBooks().getLast();
        lastBook.setAvailable(availability);
        assertEquals(availability, bookDAO.getAllBooks().getLast().isAvailable());
    }


    @Test
    void getAllBooks() {
        assertEquals(1, bookDAO.getAllBooks().size());
    }
}
