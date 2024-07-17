package com.projects.lms.finallms.dao;

import com.projects.lms.finallms.JDBCUtil;
import com.projects.lms.finallms.models.Patron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatronDAO {
    // Method to add a patron to the database
    public void addPatron(Patron patron) {
        String sql = "INSERT INTO Patrons (Name, Email) VALUES (?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, patron.getName());
            pstmt.setString(2, patron.getEmail());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        patron.setPatronID(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to remove a patron from the database
    public void removePatron(int patronID) {
        String sql = "DELETE FROM Patrons WHERE PatronID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patronID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all patrons from the database
    public List<Patron> getAllPatrons() {
        List<Patron> patrons = new ArrayList<>();
        String sql = "SELECT * FROM Patrons";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Patron patron = new Patron(
                        rs.getString("Name"),
                        rs.getString("Email")
                );
                patron.setPatronID(rs.getInt("PatronID"));
                patrons.add(patron);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patrons;
    }
}
