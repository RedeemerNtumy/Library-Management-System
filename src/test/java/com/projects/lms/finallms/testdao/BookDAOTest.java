package com.projects.lms.finallms.testdao;
import com.projects.lms.finallms.JDBCUtil;
import com.projects.lms.finallms.dao.BookDAO;
import com.projects.lms.finallms.models.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BookDAOTest {
    public BookDAO bookDAO;
    public Book book;


    @Mock
    public BookDAO mockBookDAO;
    public AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        JDBCUtil.changeMode();
        book = new Book("A week indeed", "Redeemer Ntumy", "78");
        bookDAO = new BookDAO();
        bookDAO.addBook(book);

        closeable = MockitoAnnotations.openMocks(this);
        when(mockBookDAO.getAllBooks()).thenReturn(new LinkedList<>(Collections.singletonList(book)));
        mockBookDAO = new BookDAO();
    }



    @AfterEach
    void tearDown() {
        bookDAO.removeBook(bookDAO.getAllBooks().getLast().getBookID());
        mockBookDAO.removeBook(book.getBookID());
    }


    @Test
    void addBook() {
        assertEquals("A week indeed", bookDAO.getAllBooks().getLast().getTitle() );
        assertEquals("Redeemer Ntumy", bookDAO.getAllBooks().getLast().getAuthor() );
        assertEquals("78", bookDAO.getAllBooks().getLast().getIsbn());
        assertTrue(bookDAO.getAllBooks().getLast().isAvailable());
    }


        @Test
        void updateBookAvailability() {
            mockBookDAO.updateBookAvailability(book.getBookID(),false);
            assertFalse(mockBookDAO.getBookByID(book.getBookID()).isAvailable());
        }

        @Test
        void getAllBooks() {
            assertEquals(1,mockBookDAO.getAllBooks().size());
        }
    }