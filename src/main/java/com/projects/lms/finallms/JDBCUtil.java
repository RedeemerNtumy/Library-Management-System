package com.projects.lms.finallms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class JDBCUtil {
    public static String URL = "jdbc:mysql://127.0.0.1:3306/LibraryDB";
    public static String USER = "root";
    public static String PASSWORD = "redeemerntumy4019";
    public static String testUrl = "jdbc:mysql://127.0.0.1:3306/test_libdb";

    public static void changeMode(){
        URL = testUrl;
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);

    }
}



