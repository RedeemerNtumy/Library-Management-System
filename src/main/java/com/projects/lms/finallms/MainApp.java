package com.projects.lms.finallms;

import com.projects.lms.finallms.listmanagers.BookListManager;
import com.projects.lms.finallms.listmanagers.PatronListManager;
import com.projects.lms.finallms.listmanagers.TransactionListManager;
import com.projects.lms.finallms.models.Book;
import com.projects.lms.finallms.models.Patron;
import com.projects.lms.finallms.models.Transaction;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Date;
import java.util.Queue;


public class MainApp extends Application {
    TableView<Book> bookTableView = new TableView<>();
    TableView<Patron> patronTableView = new TableView<>();
    TableView<Transaction> transactionTableView = new TableView<>();
    public BookListManager bookListManager = new BookListManager();
    public PatronListManager patronListManager = new PatronListManager();
    public TransactionListManager transactionListManager = new TransactionListManager();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: #F6E6CB;");

        VBox booksLayout = new VBox(10);
        booksLayout.setPadding(new Insets(30));
        booksLayout.setAlignment(Pos.CENTER);

        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        titleField.setId("titleField");
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        authorField.setId("authorField");
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");
        isbnField.setId("isbnField");

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);

        Button addBookButton = new Button("Add Book");
        addBookButton.setId("addBookButton");
        addBookButton.setOnAction(e -> addBook(titleField.getText(), authorField.getText(), isbnField.getText()));
        Button removeBookButton = new Button("Remove Book");
        removeBookButton.setOnAction(e -> removeBook());
        removeBookButton.setId("removeBookButton");
        Button borrowBookButton = new Button("Borrow Book");
        borrowBookButton.setId("borrowBookButton");
        borrowBookButton.setOnAction(e -> showBorrowBookForm());
        Button returnBookButton = new Button("Return Book");
        returnBookButton.setOnAction(e -> returnBook());
        returnBookButton.setId("returnBookButton");

        buttonLayout.getChildren().addAll(addBookButton, removeBookButton, borrowBookButton, returnBookButton);
        booksLayout.getChildren().addAll(new Label("Book Management"), bookTableView, titleField, authorField, isbnField, buttonLayout);
        Tab booksTab = new Tab("Books", booksLayout);
        booksTab.setId("booksTab");
        booksTab.setClosable(false);

        VBox patronsLayout = new VBox(10);
        patronsLayout.setPadding(new Insets(30));
        patronsLayout.setAlignment(Pos.CENTER);

        TextField nameField = new TextField();
        nameField.setId("nameField");
        nameField.setPromptText("Name");
        TextField emailField = new TextField();
        emailField.setId("emailField");
        emailField.setPromptText("Email");

        Button addPatronButton = new Button("Add Patron");
        addPatronButton.setId("addPatronButton");
        addPatronButton.setOnAction(e -> addPatron(nameField.getText(), emailField.getText()));
        Button removePatronButton = new Button("Remove Patron");
        removePatronButton.setId("removePatronButton");
        removePatronButton.setOnAction(e -> removePatron());

        HBox patronButtonLayout = new HBox(10);
        patronButtonLayout.setAlignment(Pos.CENTER);
        patronButtonLayout.getChildren().addAll(addPatronButton, removePatronButton);
        patronsLayout.getChildren().addAll(new Label("Patron Management"), patronTableView, nameField, emailField, patronButtonLayout);
        Tab patronsTab = new Tab("Patrons", patronsLayout);
        patronsTab.setId("patronsTab");
        patronsTab.setClosable(false);

        VBox transactionsLayout = new VBox(10);
        transactionsLayout.setPadding(new Insets(30));
        transactionsLayout.setAlignment(Pos.CENTER);
        transactionsLayout.getChildren().addAll(new Label("Transaction History"), transactionTableView);
        Tab transactionsTab = new Tab("Transactions", transactionsLayout);
        transactionsTab.setId("transactionsTab");
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
        bookTableView.setId("bookTableView");
        bookTableView.setItems(FXCollections.observableArrayList(bookListManager.getBooks()));

        bookTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Initialize patron table columns
        TableColumn<Patron, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        TableColumn<Patron, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        patronTableView.getColumns().addAll(nameColumn, emailColumn);
        patronTableView.setId("patronTableView");
        patronTableView.setItems(FXCollections.observableArrayList(patronListManager.getPatrons()));

        patronTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Initialize transaction table columns
        TableColumn<Transaction, Integer> bookIdColumn = new TableColumn<>("Book ID");
        bookIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBookID()).asObject());
        TableColumn<Transaction, String> patronNameColumn = new TableColumn<>("Patron Name");
        patronNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatronName()));
        TableColumn<Transaction, String> bookTitleColumn = new TableColumn<>("Book Title");
        bookTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookTitle()));
        TableColumn<Transaction, Date> dateBorrowedColumn = new TableColumn<>("Date Borrowed");
        dateBorrowedColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateBorrowed()));
        TableColumn<Transaction, Date> dateDueColumn = new TableColumn<>("Date Due");
        dateDueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateDue()));
        TableColumn<Transaction, Date> dateReturnedColumn = new TableColumn<>("Date Returned");
        dateReturnedColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateReturned()));
        transactionTableView.getColumns().addAll(bookIdColumn, patronNameColumn, bookTitleColumn, dateBorrowedColumn, dateDueColumn, dateReturnedColumn);
        transactionTableView.setId("transactionTableView");
        transactionTableView.setItems(FXCollections.observableArrayList(transactionListManager.loadTransactions()));


        transactionTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void addBook(String title, String author, String isbn) {
        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            showAlert("Please fill all fields.");
            return;
        }
        if (bookListManager.getBooks().stream().anyMatch(book -> book.getIsbn().equals(isbn))) {
            showAlert("A book with this ISBN already exists.");
            return;
        }
        Book newBook = new Book(title, author, isbn);
        bookListManager.addBook(newBook);
        bookTableView.setItems(FXCollections.observableArrayList(bookListManager.getBooks()));
    }


    private void removeBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            boolean hasTransactions = checkIfBookHasTransactions(selectedBook.getBookID());
            if (hasTransactions) {
                showAlert("Cannot delete the book. There are transactions associated with this book.");
            } else {
                bookListManager.removeBook(selectedBook.getBookID());
                bookTableView.setItems(FXCollections.observableArrayList(bookListManager.getBooks()));
            }
        } else {
            showAlert("No book selected.");
        }
    }

    // Helper method to check if a book has transactions
    private boolean checkIfBookHasTransactions(int bookID) {
        Queue<Transaction> transactions = transactionListManager.loadTransactions();
        for (Transaction transaction : transactions) {
            if (transaction.getBookID() == bookID) {
                return true;
            }
        }
        return false;
    }


    private void showBorrowBookForm() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook == null || !selectedBook.isAvailable()) {
            showAlert("Please select an available book.");
            return;
        }
        VBox layout = new VBox(10);
        Stage borrowStage = new Stage();
        borrowStage.initModality(Modality.APPLICATION_MODAL);
        borrowStage.setTitle("Borrow Book");

        layout.setPadding(new Insets(30));
        DatePicker issueDatePicker = new DatePicker();
        issueDatePicker.setId("issueDatePicker");
        DatePicker returnDatePicker = new DatePicker();
        returnDatePicker.setId("returnDatePicker");
        ComboBox<Patron> patronComboBox = new ComboBox<>(FXCollections.observableArrayList(patronListManager.getPatrons()));
        patronComboBox.setId("patronComboBox");
        Button saveButton = new Button("Save");
        saveButton.setId("saveButton");
        saveButton.setOnAction(e -> {
            if (java.sql.Date.valueOf(returnDatePicker.getValue()).before(java.sql.Date.valueOf(issueDatePicker.getValue()))) {
                showAlert("Return date cannot be before the issue date.");
                return;
            }
            if (issueDatePicker.getValue() != null && returnDatePicker.getValue() != null && patronComboBox.getValue() != null) {
                borrowBook(selectedBook, patronComboBox.getValue(), java.sql.Date.valueOf(issueDatePicker.getValue()), java.sql.Date.valueOf(returnDatePicker.getValue()));
                borrowStage.close();
            } else {
                showAlert("Please fill all fields.");
            }

        });

        layout.getChildren().addAll(new Label("Issue Date:"), issueDatePicker, new Label("Return Date:"), returnDatePicker, new Label("Select Patron:"), patronComboBox, saveButton);
        Scene scene = new Scene(layout, 400, 300);
        borrowStage.setScene(scene);
        borrowStage.show();
    }

    public void borrowBook(Book book, Patron patron, java.sql.Date issueDate, java.sql.Date returnDate) {
        Transaction newTransaction = new Transaction(book.getBookID(), book.getTitle(), patron.getPatronID(), patron.getName(), issueDate, returnDate);
        transactionListManager.addTransaction(newTransaction);
        book.setAvailable(false);
        bookListManager.updateBookAvailability(book.getBookID(), false);
        bookTableView.refresh();
        transactionTableView.setItems(FXCollections.observableArrayList(transactionListManager.loadTransactions()));
    }

    public void returnBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null && !selectedBook.isAvailable()) {
            Queue<Transaction> transactions = transactionListManager.loadTransactions();
            while (!transactions.isEmpty()) {
                Transaction transaction = transactions.poll();
                if (transaction.getBookID() == selectedBook.getBookID() && transaction.getDateReturned() == null) {
                    transaction.setDateReturned(new java.sql.Date(System.currentTimeMillis()));
                    transactionListManager.updateTransaction(transaction);
                    System.out.println(transaction.getDateReturned());
                    bookListManager.updateBookAvailability(selectedBook.getBookID(), true);
                    bookTableView.refresh();
                    transactionTableView.setItems(FXCollections.observableArrayList(transactionListManager.loadTransactions()));
                    break;
                }
            }
        } else {
            showAlert("No borrowed book selected.");
        }
    }


    private void addPatron(String name, String email) {
        if (name.isEmpty() || email.isEmpty()) {
            showAlert("Please fill all fields.");
            return;
        }
        if (patronListManager.getPatrons().stream().anyMatch(patron -> patron.getEmail().equals(email))) {
            showAlert("This user has already been registered.");
            return;
        }
        Patron newPatron = new Patron(name, email);
        patronListManager.addPatron(newPatron);
        patronTableView.setItems(FXCollections.observableArrayList(patronListManager.getPatrons()));
    }

    public void removePatron() {
        Patron selectedPatron = patronTableView.getSelectionModel().getSelectedItem();
        if (selectedPatron != null) {
            boolean hasTransactions = checkIfPatronHasTransactions(selectedPatron.getPatronID());
            if (hasTransactions) {
                showAlert("Cannot delete the patron. There are transactions associated with this patron.");
            } else {
                patronListManager.removePatron(selectedPatron.getPatronID());
                patronTableView.setItems(FXCollections.observableArrayList(patronListManager.getPatrons()));
            }
        } else {
            showAlert("No patron selected.");
        }
    }

    // Helper method to check if a patron has transactions
    private boolean checkIfPatronHasTransactions(int patronID) {
        Queue<Transaction> transactions = transactionListManager.loadTransactions();
        for (Transaction transaction : transactions) {
            if (transaction.getPatronID() == patronID) {
                return true;
            }
        }
        return false;
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
