package com.projects.lms.finallms.testdao;

import com.projects.lms.finallms.JDBCUtil;
import com.projects.lms.finallms.dao.TransactionDAO;
import com.projects.lms.finallms.models.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.*;
import java.util.Queue;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TransactionDAOTest {

    @Mock
    public Connection conn;
    @Mock
    public PreparedStatement pstmt;
    @Mock
    public ResultSet rs;

    @InjectMocks
    public TransactionDAO transactionDAO;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(JDBCUtil.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(anyString(), Mockito.anyInt())).thenReturn(pstmt);
        when(conn.prepareStatement(anyString())).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rs);
        when(pstmt.getGeneratedKeys()).thenReturn(rs);
        doNothing().when(pstmt).setInt(anyInt(), anyInt());
        doNothing().when(pstmt).setDate(anyInt(), any(java.sql.Date.class));
        doNothing().when(rs).close();
        doNothing().when(pstmt).close();
        doNothing().when(conn).close();
    }

    @AfterEach
    void tearDown() throws Exception {
        rs.close();
        pstmt.close();
        conn.close();
    }

    @Test
    void addTransaction() throws SQLException {
        Transaction transaction = new Transaction(1, "Moby Dick", 100, "John Doe", Date.valueOf("2023-01-01"), Date.valueOf("2023-01-15"));
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(1);

        transactionDAO.addTransaction(transaction);

        verify(pstmt, times(1)).executeUpdate();
        verify(pstmt, times(1)).getGeneratedKeys();
        assertEquals(1, transaction.getTransactionID());
    }

    @Test
    void updateTransaction() throws SQLException {
        Transaction transaction = new Transaction(1, 1, "Moby Dick", "John Doe", Date.valueOf("2023-01-01"), Date.valueOf("2023-01-15"), null);
        transaction.setDateReturned(Date.valueOf("2023-01-10"));

        transactionDAO.updateTransaction(transaction);

        verify(pstmt, times(1)).executeUpdate();
        verify(pstmt).setDate(1, transaction.getDateReturned());
    }

    @Test
    void getAllTransactions() throws SQLException {
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getInt(anyString())).thenReturn(1);
        when(rs.getString("BookTitle")).thenReturn("Moby Dick");
        when(rs.getString("PatronName")).thenReturn("John Doe");
        when(rs.getDate("DateBorrowed")).thenReturn(Date.valueOf("2023-01-01"));
        when(rs.getDate("DateDue")).thenReturn(Date.valueOf("2023-01-15"));
        when(rs.getDate("DateReturned")).thenReturn(Date.valueOf("2023-01-10"));

        Queue<Transaction> transactions = transactionDAO.getAllTransactions();

        assertEquals(2, transactions.size());
        Transaction firstTransaction = transactions.poll();
        assertNotNull(firstTransaction);
        assertEquals("Moby Dick", firstTransaction.getBookTitle());
    }
}