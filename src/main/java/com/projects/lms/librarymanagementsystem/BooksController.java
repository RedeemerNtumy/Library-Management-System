package com.projects.lms.librarymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class BooksController {
    @FXML
    private TableView<Book> booksTable;
    @FXML
    private TableColumn<Book, Integer> idColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> isbnColumn;
    @FXML
    private TableColumn<Book, String> shelfLocationColumn;
    @FXML
    private TableColumn<Book, String> statusColumn;
    @FXML
    private TextField titleTextField, authorTextField, isbnTextField, shelfLocationTextField, statusTextField;
    @FXML
    private Button addButton, deleteButton;

    private ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        shelfLocationColumn.setCellValueFactory(new PropertyValueFactory<>("shelfLocation"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Example data to simulate book data
        books.add(new Book(1, "1984", "George Orwell", "9780451524935", "A1", "Available"));
        books.add(new Book(2, "Brave New World", "Aldous Huxley", "9780060850524", "A2", "Borrowed"));

        booksTable.setItems(books);
    }

    @FXML
    private void handleAddBook() {
        // Logic to add new book from text fields
        String title = titleTextField.getText();
        String author = authorTextField.getText();
        String isbn = isbnTextField.getText();
        String shelfLocation = shelfLocationTextField.getText();
        String status = statusTextField.getText();
        if (!title.isEmpty() && !author.isEmpty() && !isbn.isEmpty() && !shelfLocation.isEmpty() && !status.isEmpty()) {
            int id = books.size() + 1;  // Generate a new id
            Book newBook = new Book(id, title, author, isbn, shelfLocation, status);
            books.add(newBook);
            booksTable.setItems(books);  // Refresh the table view
            clearFields();
        }
    }

    @FXML
    private void handleDeleteBook() {
        // Logic to delete selected book
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            books.remove(selectedBook);
            booksTable.setItems(books);  // Refresh the table view
        }
    }

    private void clearFields() {
        titleTextField.clear();
        authorTextField.clear();
        isbnTextField.clear();
        shelfLocationTextField.clear();
        statusTextField.clear();
    }
}
