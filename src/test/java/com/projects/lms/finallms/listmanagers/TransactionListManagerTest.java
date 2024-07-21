package com.projects.lms.finallms.listmanagers;

import com.projects.lms.finallms.dao.TransactionDAO;
import com.projects.lms.finallms.models.Transaction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.LinkedList;
import java.util.Queue;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TransactionListManagerTest {
    private TransactionListManager transactionListManager;

    @Mock
    private TransactionDAO mockTransactionDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks annotated with @Mock
        transactionListManager = new TransactionListManager();
        transactionListManager.transactionDAO = mockTransactionDAO; // Inject the mocked DAO

        // Setup the DAO to return a Queue when getAllTransactions is called
        when(mockTransactionDAO.getAllTransactions()).thenReturn(new LinkedList<>());
    }

    @AfterEach
    void tearDown() {
        transactionListManager = null;
    }

    @Test
    void addTransaction() {
        Transaction transaction = new Transaction(1, "Effective Java", 100, "John Doe", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 86400000));
        transactionListManager.addTransaction(transaction);
        verify(mockTransactionDAO).addTransaction(transaction); // Verify that the transaction is added to the DAO
        assertTrue(transactionListManager.getTransactions().contains(transaction), "The transaction should be added to the local queue.");
    }

    @Test
    @Disabled("This would have to be implemented well")
    void updateTransaction() {
        Date dateReturned = new Date(System.currentTimeMillis());
        Transaction transaction = new Transaction(1, "Effective Java", 100, "John Doe", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 86400000));
        transaction.setTransactionID(1); // Set ID to simulate a real-world scenario
        transactionListManager.getTransactions().offer(transaction);

        // Update transaction
        Transaction updatedTransaction = new Transaction(transaction.getTransactionID(), transaction.getBookTitle(),transaction.getBookID(), transaction.getPatronName(), transaction.getDateBorrowed(), transaction.getDateDue());
        updatedTransaction.setDateReturned(dateReturned);
        transactionListManager.updateTransaction(updatedTransaction);

        verify(mockTransactionDAO).updateTransaction(updatedTransaction); // Verify that the transaction is updated in the DAO
        assertEquals(dateReturned, transaction.getDateReturned(), "The date returned should be updated in the local queue.");
    }

    @Test
    void loadTransactions() {
        Queue<Transaction> loadedTransactions = transactionListManager.loadTransactions();
        assertNotNull(loadedTransactions, "Should return a queue, not null.");
        verify(mockTransactionDAO).getAllTransactions(); // Ensure DAO method is called to load transactions
    }

    @Test
    void getTransactions() {
        Queue<Transaction> transactions = transactionListManager.getTransactions();
        assertNotNull(transactions, "Should return a queue, not null.");
    }
}
