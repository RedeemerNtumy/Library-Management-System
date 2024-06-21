package com.projects.lms.finallms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TransactionDAO {
    // Method to add a transaction to the database
    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO Transactions (BookID, PatronID, DateBorrowed, DateDue) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, transaction.getBookID());
            pstmt.setInt(2, transaction.getPatronID());
            pstmt.setDate(3, new java.sql.Date(transaction.getDateBorrowed().getTime()));
            pstmt.setDate(4, new java.sql.Date(transaction.getDateDue().getTime()));
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        transaction.setTransactionID(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update a transaction in the database (for returning a book)
    public void updateTransaction(Transaction transaction) {
        String sql = "UPDATE Transactions SET DateReturned = ? WHERE TransactionID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, transaction.getDateReturned());
            pstmt.setInt(2, transaction.getTransactionID());
            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all transactions from the database
    public Queue<Transaction> getAllTransactions() {
        Queue<Transaction> transactions = new LinkedList<>();
        String sql = "SELECT t.TransactionID, t.BookID, b.Title AS BookTitle, p.Name AS PatronName, t.DateBorrowed, t.DateDue, t.DateReturned " +
                "FROM Transactions t " +
                "JOIN Books b ON t.BookID = b.BookID " +
                "JOIN Patrons p ON t.PatronID = p.PatronID";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("TransactionID"),
                        rs.getInt("BookID"),
                        rs.getString("BookTitle"),
                        rs.getString("PatronName"),
                        rs.getDate("DateBorrowed"),
                        rs.getDate("DateDue"),
                        rs.getDate("DateReturned")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

}
