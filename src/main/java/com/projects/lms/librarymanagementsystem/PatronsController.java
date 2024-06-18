package com.projects.lms.librarymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PatronsController {
    @FXML
    private TableView<Patron> patronsTable;
    @FXML
    private TableColumn<Patron, Integer> idColumn;
    @FXML
    private TableColumn<Patron, String> nameColumn;
    @FXML
    private TableColumn<Patron, String> emailColumn;
    @FXML
    private TableColumn<Patron, String> phoneNumberColumn;
    @FXML
    private TextField nameTextField, emailTextField, phoneNumberTextField;
    @FXML
    private Button addButton, deleteButton;

    private ObservableList<Patron> patrons = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // Example data to simulate patron data
        patrons.add(new Patron(1, "John Doe", "john.doe@example.com", "1234567890"));
        patrons.add(new Patron(2, "Jane Smith", "jane.smith@example.com", "0987654321"));

        patronsTable.setItems(patrons);
    }

    @FXML
    private void handleAddPatron() {
        // Logic to add new patron from text fields
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        if (!name.isEmpty() && !email.isEmpty() && !phoneNumber.isEmpty()) {
            int id = patrons.size() + 1;  // Generate a new id
            Patron newPatron = new Patron(id, name, email, phoneNumber);
            patrons.add(newPatron);
            patronsTable.setItems(patrons);  // Refresh the table view
            clearFields();
        }
    }

    @FXML
    private void handleDeletePatron() {
        // Logic to delete selected patron
        Patron selectedPatron = patronsTable.getSelectionModel().getSelectedItem();
        if (selectedPatron != null) {
            patrons.remove(selectedPatron);
            patronsTable.setItems(patrons);  // Refresh the table view
        }
    }

    private void clearFields() {
        nameTextField.clear();
        emailTextField.clear();
        phoneNumberTextField.clear();
    }
}
