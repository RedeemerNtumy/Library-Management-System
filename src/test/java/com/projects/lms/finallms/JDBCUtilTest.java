package com.projects.lms.finallms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JDBCUtilTest {

    @BeforeEach
    void setUp() throws Exception {
        // Mock the DriverManager to avoid actual database connections
        mockStatic(DriverManager.class);
    }

    @Test
    void changeMode() {
        // Ensure the URL changes to the test URL
        JDBCUtil.changeMode();
        assertEquals(JDBCUtil.testUrl, JDBCUtil.URL, "The URL should change to the test database URL.");
    }

}
