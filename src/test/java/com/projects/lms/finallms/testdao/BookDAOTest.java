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
        bookDAO.addBook(book);

//        fail("Test not yet implemented");
    }

    @AfterEach
    void tearDown() {
        bookDAO.removeBook(book.getBookID());
//        fail("Test not yet implemented");
    }



    @Test
    void addBookCheckTitle() {
        assertEquals("A week indeed", bookDAO.getLastBook().getTitle() );
    }

    @Test
    void addBookCheckAuthor() {
        assertEquals("Redeemer Ntumy", bookDAO.getLastBook().getAuthor() );
    }

    @Test
    void addBookCheckIsbn() {
        assertEquals("511", bookDAO.getLastBook().getIsbn());
    }

    @Test
    void addBookCheckAvailability() {
        assertTrue(bookDAO.getLastBook().isAvailable());
    }


    @Test
    void removeBook() {
        bookDAO.removeBook(book.getBookID());
        assertFalse(bookDAO.bookExistsByISBN(book.getIsbn()));
    }

    @Test
    void updateBookAvailability() {
//        fail("Test not yet implemented");
    }

    @Test
    void getAllBooks() {
//        fail("Test not yet implemented");
    }
}
