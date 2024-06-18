package com.projects.lms.librarymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionsController {
    @FXML
    private TableView<Transaction> transactionsTable;
    @FXML
    private TableColumn<Transaction, Integer> bookIdColumn;
    @FXML
    private TableColumn<Transaction, Integer> patronIdColumn;
    @FXML
    private TableColumn<Transaction, Date> issueDateColumn;
    @FXML
    private TableColumn<Transaction, Date> returnDateColumn;

    private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    public TransactionsController() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            transactions.add(new Transaction(1, 1, 1, sdf.parse("2024-01-01"), sdf.parse("2024-01-15")));
            transactions.add(new Transaction(2, 2, 1, sdf.parse("2024-02-01"), null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        patronIdColumn.setCellValueFactory(new PropertyValueFactory<>("patronId"));
        issueDateColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        transactionsTable.setItems(transactions);
    }


    // Implement additional methods as needed for transaction management
}
