package com.projects.lms.finallms.dao;

import com.projects.lms.finallms.models.Book;
import com.projects.lms.finallms.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class BookDAO {
    public void addBook(Book book) {
        String sql = "INSERT INTO Books (Title, Author, ISBN, Available) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getIsbn());
            pstmt.setBoolean(4, book.isAvailable());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        book.setBookID(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBook(int bookID) {
        String sql = "DELETE FROM Books WHERE BookID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBookAvailability(int bookID, boolean isAvailable) {
        String sql = "UPDATE Books SET Available = ? WHERE BookID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, isAvailable);
            pstmt.setInt(2, bookID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public LinkedList<Book> getAllBooks() {
        LinkedList<Book> books = new LinkedList<>();
        String sql = "SELECT * FROM Books";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("ISBN")
                );
                book.setBookID(rs.getInt("BookID"));
                book.setAvailable(rs.getBoolean("Available"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
