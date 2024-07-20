package com.projects.lms.finallms.testdao;

import com.projects.lms.finallms.dao.BookDAO;
import com.projects.lms.finallms.dao.TransactionDAO;
import com.projects.lms.finallms.models.Book;
import com.projects.lms.finallms.models.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransactionDAOTest {

    @Mock
    public TransactionDAO transactionDAO;
    public AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        Transaction transaction = new Transaction(5,"Sample Book",5,"Mr Patron", new Date(2000,10,23), new Date(2000,10,24));
        when(transactionDAO.getAllTransactions()).thenReturn(new LinkedList<>(Collections.singletonList(transaction)));
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    static Stream<Transaction> transactionProvider() {
        return Stream.of(
                new Transaction(5, "Sample Book", 5, "Mr Patron", new Date(100, 9, 23), new Date(100, 9, 24)),
                new Transaction(6, "Another Book", 1, "Ms Patron", new Date(101, 0, 1), new Date(101, 0, 15)),
                new Transaction(7, "Third Book", 2, "Dr Patron", new Date(99, 11, 31), new Date(100, 0, 30))
        );
    }

    @ParameterizedTest
    @MethodSource("transactionProvider")
    void AddTransaction(Transaction transaction) {
        transactionDAO.addTransaction(transaction);
        verify(transactionDAO).addTransaction(transaction);
    }

    @ParameterizedTest
    @MethodSource("transactionProvider")
    void UpdateTransaction(Transaction transaction) {
        transactionDAO.updateTransaction(transaction);
        verify(transactionDAO).updateTransaction(transaction);
    }

    @Test
    void getAllTransactions() {
        Queue<Transaction> expectedTransactions = new LinkedList<>();
        expectedTransactions.add(new Transaction(5, "Sample Book", 5, "Mr Patron", new Date(100, 9, 23), new Date(100, 9, 24)));
        expectedTransactions.add(new Transaction(6, "Another Book", 1, "Ms Patron", new Date(101, 0, 1), new Date(101, 0, 15)));
        expectedTransactions.add(new Transaction(7, "Third Book", 2, "Dr Patron", new Date(99, 11, 31), new Date(100, 0, 30)));
        when(transactionDAO.getAllTransactions()).thenReturn(expectedTransactions);
        Queue<Transaction> actualTransactions = transactionDAO.getAllTransactions();
        assertEquals(expectedTransactions, actualTransactions, "The returned queue of transactions should match the expected queue.");
        verify(transactionDAO).getAllTransactions();
    }
}