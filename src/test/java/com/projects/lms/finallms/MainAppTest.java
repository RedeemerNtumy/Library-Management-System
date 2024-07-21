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
    public void testAddAndRemoveBook(FxRobot robot) {

        robot.clickOn("#booksTab");

        // Enter book details
        robot.clickOn("#titleField").write("New Titles");
        robot.clickOn("#authorField").write("Joshua Bloch");
        robot.clickOn("#isbnField").write("1234");
        robot.sleep(1000);
        robot.clickOn("#addBookButton");

        // Verify addition
        TableView<Book> bookTable = robot.lookup("#bookTableView").queryAs(TableView.class);
        assertEquals(2, bookTable.getItems().size(), "Book should be added to the table.");

        // Remove the book
        robot.clickOn("New Titles");  // Click on the row containing the book
        robot.clickOn("#removeBookButton");
        assertEquals(1, bookTable.getItems().size(), "Book should be removed from the table.");
    }



    @Test
    public void testAddAndRemovePatron(FxRobot robot) {
        // First, navigate to the "Patrons" tab
        robot.clickOn("#patronsTab");

        // Enter patron details in the appropriate fields
        robot.clickOn("#nameField").write("John Doe");
        robot.clickOn("#emailField").write("john.doe@example.com");
        robot.clickOn("#addPatronButton");

        // Verify the patron was added successfully
        TableView<Patron> patronTable = robot.lookup("#patronTableView").queryAs(TableView.class);
        assertEquals(1, patronTable.getItems().size(), "Patron should be added to the table.");

        // Select and remove the patron
        robot.clickOn("John Doe");  // Click on the row containing the patron
        robot.clickOn("#removePatronButton");

        // Check that the patron has been removed
        assertEquals(0, patronTable.getItems().size(), "Patron should be removed from the table.");
    }


    @Test
    public void testBorrowAndReturnBook(FxRobot robot) {
        // Navigate to the "Books" tab if needed
        robot.clickOn("#patronsTab");

        // Enter patron details in the appropriate fields
        robot.clickOn("#nameField").write("John Doe");
        robot.clickOn("#emailField").write("john.doe@example.com");
        robot.clickOn("#addPatronButton");

        robot.clickOn("#booksTab");

        // Add a book
        robot.clickOn("#titleField").write("Effective Java");
        robot.clickOn("#authorField").write("Joshua Bloch");
        robot.clickOn("#isbnField").write("0321356683");
        robot.clickOn("#addBookButton");

        // Assuming the book "Effective Java" is added and is available
        robot.clickOn("Effective Java");  // Select the book in the table
        robot.clickOn("#borrowBookButton");

        // Simulate interaction with the borrow book form
        robot.clickOn("#issueDatePicker").write("20/07/2024");
        robot.clickOn("#returnDatePicker").write("27/07/2024");

        // Select a patron from the combo box
        robot.clickOn("#patronComboBox");
        robot.type(KeyCode.DOWN);  // Assuming "John Doe" is the first in the list
        robot.type(KeyCode.ENTER);

        robot.clickOn("#saveButton");

        // Verify that the book is now marked as borrowed
        TableView<Book> bookTable = robot.lookup("#bookTableView").queryAs(TableView.class);
        Book borrowedBook = bookTable.getItems().stream()
                .filter(book -> book.getTitle().equals("Effective Java"))
                .findFirst()
                .orElse(null);
        assertNotNull(borrowedBook, "Borrowed book should be found.");
        assertFalse(borrowedBook.isAvailable(), "Book should be marked as not available.");

        // Return the book
        robot.clickOn("Effective Java");
        robot.clickOn("#returnBookButton");

        // Verify that the book is marked as available again
        assertTrue(borrowedBook.isAvailable(), "Book should be marked as available again.");

        // Verify the transaction is logged (Assuming there's a transaction tab or similar to validate this)
        robot.clickOn("#transactionsTab");
        robot.sleep(100);
        TableView<Transaction> transactionTable = robot.lookup("#transactionTableView").queryAs(TableView.class);
        assertFalse(transactionTable.getItems().isEmpty(), "Transaction should be listed.");

        robot.clickOn("#patronsTab");
        robot.sleep(1000);
        // Select and remove the patron
        robot.clickOn("john.doe@example.com");
        robot.clickOn("#removePatronButton");
    }

}
