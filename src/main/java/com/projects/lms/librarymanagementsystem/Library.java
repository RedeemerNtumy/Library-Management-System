package com.projects.lms.librarymanagementsystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;

public class Library {

    private LibraryJDBC dbConnector;


    public ObservableList<Object> search(String query) {
        ObservableList<Object> searchResults = FXCollections.observableArrayList();
        // Assuming we can determine the type of search or just search in all tables
        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = createSearchStatement(conn, query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                // Assuming a method to determine the type of result object based on returned columns
                Object result = convertResultSetToObject(rs);
                searchResults.add(result);
            }
        } catch (SQLException e) {
            System.out.println("Error searching: " + e.getMessage());
        }
        return searchResults;
    }

    private PreparedStatement createSearchStatement(Connection conn, String query) throws SQLException {
        // This is a very basic example. You should modify it based on your schema and needs
        String sql = "SELECT * FROM Books WHERE title LIKE ? UNION ALL " +
                "SELECT * FROM Patrons WHERE name LIKE ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%" + query + "%");
        pstmt.setString(2, "%" + query + "%");
        return pstmt;
    }

    private Object convertResultSetToObject(ResultSet rs) throws SQLException {
        // Determine type based on available columns
        ResultSetMetaData metaData = rs.getMetaData();
        if (metaData.getColumnCount() == 5) {  // Example column count for Books
            return new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getString("shelf_location"),
                    rs.getString("status")
            );
        } else if (metaData.getColumnCount() == 4) {  // Example column count for Patrons
            return new Patron(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone_number")

            );
        }
        return null;  // In case of no match or unsupported type
    }

    public void addPatron(Patron patron) {
        String sql = "INSERT INTO Patrons (name, email, phone_number, membership_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patron.getName());
            pstmt.setString(2, patron.getEmail());
            pstmt.setString(3, patron.getPhoneNumber());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("A new patron was added successfully!");
            } else {
                System.out.println("A problem occurred and the patron was not added.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding patron: " + e.getMessage());
        }
    }

    public void updatePatron(Patron patron) {
        String sql = "UPDATE Patrons SET name = ?, email = ?, phone_number = ? WHERE id = ?";

        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patron.getName());
            pstmt.setString(2, patron.getEmail());
            pstmt.setString(3, patron.getPhoneNumber());
            pstmt.setInt(4, patron.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patron updated successfully!");
            } else {
                System.out.println("No changes were made. Check if the patron ID exists.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating patron: " + e.getMessage());
        }
    }

    public ObservableList<Patron> getAllPatrons() {
        ObservableList<Patron> patrons = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Patrons";

        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Patron patron = new Patron(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone_number")
                );
                patrons.add(patron);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching patrons: " + e.getMessage());
        }
        return patrons;
    }
    public String generateUsageReport() {
        StringBuilder report = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection conn = dbConnector.connect();
             Statement stmt = conn.createStatement()) {
            // Example SQL to fetch transaction data
            String query = "SELECT COUNT(*) AS transaction_count FROM Transactions";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                report.append("Total Transactions: ").append(rs.getInt("transaction_count")).append("\n");
            }

            // More complex queries can be added here to fetch more detailed data
            query = "SELECT title, COUNT(*) as count FROM Books JOIN Transactions ON Books.id = Transactions.book_id GROUP BY book_id ORDER BY count DESC LIMIT 1";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                report.append("Most Borrowed Book: ").append(rs.getString("title")).append("\n");
            }

            // Assume additional stats like active patrons
            query = "SELECT COUNT(DISTINCT patron_id) AS active_patrons FROM Transactions WHERE issue_date >= DATE_SUB(NOW(), INTERVAL 1 YEAR)";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                report.append("Active Patrons Last Year: ").append(rs.getInt("active_patrons")).append("\n");
            }

        } catch (SQLException e) {
            report.append("Error generating report: ").append(e.getMessage());
        }

        return report.toString();
    }

    public Library() {
        dbConnector = new LibraryJDBC();
    }

    public ObservableList<Book> searchBooks(String title) {
        ObservableList<Book> books = FXCollections.observableArrayList();
        // Example: Query database to fetch books based on title
        // This is a simplification. Actual implementation should use PreparedStatement to avoid SQL injection
        String query = "SELECT * FROM Books WHERE title LIKE '%" + title + "%'";
        try (Connection conn = dbConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                books.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                        rs.getString("isbn"), rs.getString("shelf_location"), rs.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Additional methods for managing patrons and transactions
}
