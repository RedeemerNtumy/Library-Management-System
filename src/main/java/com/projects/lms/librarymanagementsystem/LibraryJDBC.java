package com.projects.lms.librarymanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LibraryJDBC {
    private final String url = "jdbc:mysql://127.0.0.1:3306/LibraryDB";
    private final String user = "root";
    private final String password = "redeemerntumy4019";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}