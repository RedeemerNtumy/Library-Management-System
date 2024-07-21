package com.projects.lms.finallms.listmanagers;

import com.projects.lms.finallms.dao.TransactionDAO;
import com.projects.lms.finallms.models.Transaction;

import java.util.LinkedList;
import java.util.Queue;

public class TransactionListManager {
    private Queue<Transaction> transactions;
    public TransactionDAO transactionDAO;

    public TransactionListManager() {
        this.transactions = new LinkedList<>();
        this.transactionDAO = new TransactionDAO();
        loadTransactions();  // Initialize the transaction list by loading from the database
    }

    // Add a transaction both in the database and local list
    public void addTransaction(Transaction transaction) {
        transactionDAO.addTransaction(transaction);  // Add transaction to the database
        transactions.offer(transaction);  // Add to the local list
    }

    // Update a transaction in the list and the database
    public void updateTransaction(Transaction transaction) {
        transactionDAO.updateTransaction(transaction);  // Update transaction in the database
        transactions.stream()
                .filter(t -> t.getTransactionID() == transaction.getTransactionID())
                .findFirst()
                .ifPresent(t -> {
                    t.setDateReturned(transaction.getDateReturned());  // Update the local list
                });
    }

    // Load all transactions from the database
    public Queue<Transaction> loadTransactions() {
        transactions = transactionDAO.getAllTransactions();  // Load transactions from the database
        return transactions;
    }

    // Getter for the list of transactions
    public Queue<Transaction> getTransactions() {
        return transactions;
    }
}
