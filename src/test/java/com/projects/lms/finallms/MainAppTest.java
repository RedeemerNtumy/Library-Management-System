package com.projects.lms.finallms;

import com.projects.lms.finallms.models.Book;
import com.projects.lms.finallms.models.Patron;
import com.projects.lms.finallms.models.Transaction;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class MainAppTest {

    @Start
    private void start(Stage stage) throws Exception {
        JDBCUtil.changeMode();
        MainApp app = new MainApp();  // your MainApp initializes its components here
        app.start(stage);  // This effectively initializes the JavaFX environment
    }


    @Test
    public void testAddBook(FxRobot robot) {
        robot.sleep(10000);
        // Enter book details
        robot.clickOn("#titleField").write("Effective Java");
        robot.clickOn("#authorField").write("Joshua Bloch");
        robot.clickOn("#isbnField").write("0321356683");
        robot.clickOn("#addBookButton");

        // Verify addition
        TableView<Book> bookTable = robot.lookup("#bookTableView").queryAs(TableView.class);
        assertEquals(1, bookTable.getItems().size(), "Book should be added to the table.");
    }

    @Test
    public void testRemoveBook(FxRobot robot) {
        robot.sleep(10000);
        // Remove the book
        robot.clickOn("Effective Java");  // Click on the row containing the book
        robot.clickOn("#removeBookButton");
        TableView<Book> bookTable = robot.lookup("#bookTableView").queryAs(TableView.class);
        assertEquals(0, bookTable.getItems().size(), "Book should be removed from the table.");
    }

//    @Test
//    public void testAddAndRemovePatron(FxRobot robot) {
//        robot.sleep(10000);
//        // Enter patron details
//        robot.clickOn("#nameField").write("John Doe");
//        robot.clickOn("#emailField").write("john.doe@example.com");
//        robot.clickOn("#addPatronButton");
//
//        // Verify addition
//        TableView<Patron> patronTable = robot.lookup("#patronTableView").queryAs(TableView.class);
//        assertEquals(1, patronTable.getItems().size(), "Patron should be added to the table.");
//
//        // Remove the patron
//        robot.clickOn("John Doe");  // Click on the row containing the patron
//        robot.clickOn("#removePatronButton");
//        assertEquals(0, patronTable.getItems().size(), "Patron should be removed from the table.");
//    }

//    @Test
//    public void testBorrowAndReturnBook(FxRobot robot) {
//        robot.sleep(10000);
//        // Assuming the book "Effective Java" and patron "John Doe" are already added and the book is available
//        robot.clickOn("Effective Java");
//        robot.clickOn("#borrowBookButton");
//
//        // Simulate interaction with the borrow book form
//        robot.clickOn("#issueDatePicker").write("2024-01-01");
//        robot.clickOn("#returnDatePicker").write("2024-01-15");
//        robot.clickOn("#patronComboBox");
//        robot.type(KeyCode.DOWN);  // navigate through combo box
//        robot.type(KeyCode.ENTER);  // select patron
//        robot.clickOn("#saveButton");
//
//        // Verify that the transaction is logged
//        TableView<Transaction> transactionTable = robot.lookup("#transactionTableView").queryAs(TableView.class);
//        assertFalse(transactionTable.getItems().isEmpty(), "Transaction should be listed.");
//
//        // Simulate returning the book
//        robot.clickOn("Effective Java");
//        robot.clickOn("#returnBookButton");
//
//        // Additional checks can be added to verify the return status
//    }
}
