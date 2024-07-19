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
//    public Book getLastBook() {
//        String sql = "SELECT * FROM Books ORDER BY BookID DESC LIMIT 1";
//        try (Connection conn = JDBCUtil.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//             ResultSet rs = pstmt.executeQuery()) {
//            if (rs.next()) {
//                Book book = new Book(
//                        rs.getString("Title"),
//                        rs.getString("Author"),
//                        rs.getString("ISBN")
//                );
//                book.setBookID(rs.getInt("BookID"));
//                book.setAvailable(rs.getBoolean("Available"));
//                return book;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null; // or throw an exception if preferred
//    }

    public Book getBookByID(int bookID) {
        String sql = "SELECT * FROM Books WHERE BookID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book(
                            rs.getString("Title"),
                            rs.getString("Author"),
                            rs.getString("ISBN")

                    );
                    book.setBookID(rs.getInt("BookID"));
                    book.setAvailable(rs.getBoolean("Available"));
                    return book;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null or throw an exception if the book is not found
    }

    public boolean bookExistsByISBN(String isbn) {
        String sql = "SELECT 1 FROM Books WHERE ISBN = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if there is at least one result
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if an exception occurs or no book is found
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

    @Override
    public String toString() {
        return toString();
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
