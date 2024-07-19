package com.projects.lms.finallms.testdao;

import com.projects.lms.finallms.dao.BookDAO;
import com.projects.lms.finallms.models.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookDAOTest {
    public BookDAO bookDAO;
    public Book book;

    @BeforeEach
    void setUp() {
        book = new Book("A week indeed", "Redeemer Ntumy", "511");
        bookDAO = new BookDAO();

    }

    @AfterEach
    void tearDown() {
        bookDAO.removeBook(book.getBookID());
    }


    @Test
    void addBookCheckTitle() {
        assertEquals("A week indeed", bookDAO.getAllBooks().getLast().getTitle() );
    }

    @Test
    void addBookCheckAuthor() {
        assertEquals("Redeemer Ntumy", bookDAO.getAllBooks().getLast().getAuthor() );
    }

    @Test
    void addBookCheckIsbn() {
        assertEquals("511", bookDAO.getAllBooks().getLast().getIsbn());
    }

    @Test
    void addBookCheckAvailability() {
        assertTrue(bookDAO.getAllBooks().getLast().isAvailable());
    }


    @Test
    void removeBook() {
        bookDAO.removeBook(book.getBookID());
        assertFalse(bookDAO.bookExistsByISBN(book.getIsbn()));
    }

    @Test
    void updateBookAvailability() {
        bookDAO.updateBookAvailability(book.getBookID(),true);
        assertTrue(bookDAO.getBookByID(book.getBookID()).isAvailable());
    }

    @Test
    void getAllBooks() {
       assertEquals(27,bookDAO.getAllBooks().size());
//        fail("Test not yet implemented");
    }
}
