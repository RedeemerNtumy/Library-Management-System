package com.projects.lms.finallms;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Date;

public class MainApp extends Application {
    private TableView<Book> bookTableView = new TableView<>();
    private TableView<Patron> patronTableView = new TableView<>();
    private TableView<Transaction> transactionTableView = new TableView<>();
    private BookListManager bookListManager = new BookListManager();
    private PatronListManager patronListManager = new PatronListManager();
    private TransactionDAO transactionDAO = new TransactionDAO();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        // Tabs setup
        TabPane tabPane = new TabPane();

        // Books Tab
        VBox booksLayout = new VBox(10);
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");
        Button addBookButton = new Button("Add Book");
        addBookButton.setOnAction(e -> addBook(titleField.getText(), authorField.getText(), isbnField.getText()));
        Button removeBookButton = new Button("Remove Book");
        removeBookButton.setOnAction(e -> removeBook());
        booksLayout.getChildren().addAll(new Label("Book Management"), titleField, authorField, isbnField, addBookButton, removeBookButton, bookTableView);
        Tab booksTab = new Tab("Books", booksLayout);
        booksTab.setClosable(false);

        // Patrons Tab
        VBox patronsLayout = new VBox(10);
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        Button addPatronButton = new Button("Add Patron");
        addPatronButton.setOnAction(e -> addPatron(nameField.getText(), emailField.getText()));
        Button removePatronButton = new Button("Remove Patron");
        removePatronButton.setOnAction(e -> removePatron());
        patronsLayout.getChildren().addAll(new Label("Patron Management"), nameField, emailField, addPatronButton, removePatronButton, patronTableView);
        Tab patronsTab = new Tab("Patrons", patronsLayout);
        patronsTab.setClosable(false);

        // Transactions Tab
        VBox transactionsLayout = new VBox(10);
        Button borrowBookButton = new Button("Borrow Book");
        borrowBookButton.setOnAction(e -> borrowBook());
        Button returnBookButton = new Button("Return Book");
        returnBookButton.setOnAction(e -> returnBook());
        transactionsLayout.getChildren().addAll(new Label("Transaction Management"), borrowBookButton, returnBookButton, transactionTableView);
        Tab transactionsTab = new Tab("Transactions", transactionsLayout);
        transactionsTab.setClosable(false);

        tabPane.getTabs().addAll(booksTab, patronsTab, transactionsTab);

        Scene scene = new Scene(tabPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        initializeTables();
    }

    private void initializeTables() {
        // Initialize book table columns
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

        // Initialize patron table columns
        TableColumn<Patron, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        TableColumn<Patron, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        patronTableView.getColumns().addAll(nameColumn, emailColumn);
        patronTableView.setItems(FXCollections.observableArrayList(patronListManager.getPatrons()));

        // Initialize transaction table columns
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

    private void addBook(String title, String author, String isbn) {
        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            showAlert("Please fill all fields.");
            return;
        }
        Book newBook = new Book(title, author, isbn);
        bookListManager.addBook(newBook);
        bookTableView.setItems(FXCollections.observableArrayList(bookListManager.getBooks()));
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

    private void addPatron(String name, String email) {
        if (name.isEmpty() || email.isEmpty()) {
            showAlert("Please fill all fields.");
            return;
        }
        Patron newPatron = new Patron(name, email);
        patronListManager.addPatron(newPatron);
        patronTableView.setItems(FXCollections.observableArrayList(patronListManager.getPatrons()));
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
