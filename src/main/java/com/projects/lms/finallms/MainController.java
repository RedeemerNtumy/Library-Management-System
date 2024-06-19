package com.projects.lms.finallms;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.Date;

public class MainController {
    @FXML private TableView<Book> bookTableView;
    @FXML private TableView<Patron> patronTableView;
    @FXML private TableView<Transaction> transactionTableView;
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField isbnField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private Button addBookButton;
    @FXML private Button removeBookButton;
    @FXML private Button addPatronButton;
    @FXML private Button removePatronButton;
    @FXML private Button borrowBookButton;
    @FXML private Button returnBookButton;

    private BookListManager bookListManager = new BookListManager();
    private PatronListManager patronListManager = new PatronListManager();
    private TransactionDAO transactionDAO = new TransactionDAO();

    @FXML
    public void initialize() {
        initializeBookTable();
        initializePatronTable();
        initializeTransactionTable();

        addBookButton.setOnAction(e -> addBook());
        removeBookButton.setOnAction(e -> removeBook());
        addPatronButton.setOnAction(e -> addPatron());
        removePatronButton.setOnAction(e -> removePatron());
        borrowBookButton.setOnAction(e -> borrowBook());
        returnBookButton.setOnAction(e -> returnBook());
    }

    private void initializeBookTable() {
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));
        TableColumn<Book, Boolean> availableColumn = new TableColumn<>("Available");
        availableColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isAvailable()));
        bookTableView.getColumns().addAll(titleColumn, authorColumn, isbnColumn, availableColumn);
        bookTableView.setItems(FXCollections.observableArrayList(bookListManager.getBooks()));
    }

    private void initializePatronTable() {
        TableColumn<Patron, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        TableColumn<Patron, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        patronTableView.getColumns().addAll(nameColumn, emailColumn);
        patronTableView.setItems(FXCollections.observableArrayList(patronListManager.getPatrons()));
    }

    private void initializeTransactionTable() {
        TableColumn<Transaction, Integer> bookIdColumn = new TableColumn<>("BookID");
        bookIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBookID()).asObject());
        TableColumn<Transaction, String> patronNameColumn = new TableColumn<>("Patron Name");
        patronNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatronName()));
        TableColumn<Transaction, Date> dateBorrowedColumn = new TableColumn<>("Date Borrowed");
        dateBorrowedColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateBorrowed()));
        TableColumn<Transaction, Date> dateDueColumn = new TableColumn<>("Date Due");
        dateDueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateDue()));
        TableColumn<Transaction, Date> dateReturnedColumn = new TableColumn<>("Date Returned");
        dateReturnedColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateReturned()));
        transactionTableView.getColumns().addAll(bookIdColumn, patronNameColumn, dateBorrowedColumn, dateDueColumn, dateReturnedColumn);
        transactionTableView.setItems(FXCollections.observableArrayList(transactionDAO.getAllTransactions()));
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            showAlert("Please fill all fields.");
            return;
        }
        Book newBook = new Book(title, author, isbn);
        bookListManager.addBook(newBook);
        bookTableView.setItems(FXCollections.observableArrayList(bookListManager.getBooks()));
        clearBookFields();
    }

    private void removeBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            bookListManager.removeBook(selectedBook.getBookID());
            bookTableView.setItems(FXCollections.observableArrayList(bookListManager.getBooks()));
        } else {
            showAlert("No book selected.");
        }
    }

    private void addPatron() {
        String name = nameField.getText();
        String email = emailField.getText();
        if (name.isEmpty() || email.isEmpty()) {
            showAlert("Please fill all fields.");
            return;
        }
        Patron newPatron = new Patron(name, email);
        patronListManager.addPatron(newPatron);
        patronTableView.setItems(FXCollections.observableArrayList(patronListManager.getPatrons()));
        clearPatronFields();
    }

    private void removePatron() {
        Patron selectedPatron = patronTableView.getSelectionModel().getSelectedItem();
        if (selectedPatron != null) {
            patronListManager.removePatron(selectedPatron.getPatronID());
            patronTableView.setItems(FXCollections.observableArrayList(patronListManager.getPatrons()));
        } else {
            showAlert("No patron selected.");
        }
    }

    private void borrowBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        Patron selectedPatron = patronTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null && selectedPatron != null && selectedBook.isAvailable()) {
            Date dateBorrowed = new Date();
            Date dateDue = new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000)); // Due in one week
            Transaction newTransaction = new Transaction(
                    selectedBook.getBookID(),
                    selectedPatron.getPatronID(),
                    dateBorrowed,
                    dateDue
            );
            transactionDAO.addTransaction(newTransaction);
            selectedBook.setAvailable(false);
            bookListManager.updateBookAvailability(selectedBook.getBookID(), false);
            bookTableView.refresh();
            transactionTableView.setItems(FXCollections.observableArrayList(transactionDAO.getAllTransactions()));
        } else {
            showAlert("Please select an available book and a patron.");
        }
    }

    private void returnBook() {
        Transaction selectedTransaction = transactionTableView.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null && selectedTransaction.getDateReturned() == null) {
            selectedTransaction.setDateReturned(new Date());
            transactionDAO.updateTransaction(selectedTransaction);
            bookListManager.updateBookAvailability(selectedTransaction.getBookID(), true);
            bookTableView.refresh();
            transactionTableView.setItems(FXCollections.observableArrayList(transactionDAO.getAllTransactions()));
        } else {
            showAlert("No valid transaction selected.");
        }
    }

    private void clearBookFields() {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
    }

    private void clearPatronFields() {
        nameField.clear();
        emailField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
