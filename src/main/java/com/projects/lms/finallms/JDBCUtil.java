package com.projects.lms.finallms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/LibraryDB";
    private static final String USER = "root";
    private static final String PASSWORD = "redeemerntumy4019";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}



