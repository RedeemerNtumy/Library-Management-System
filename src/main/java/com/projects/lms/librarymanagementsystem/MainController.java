package com.projects.lms.librarymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class MainController {
    @FXML
    private StackPane contentArea;

    public void showBooks() {
        loadView("Books.fxml");
    }

    public void showPatrons() {
        loadView("Patrons.fxml");
    }

    public void showTransactions() {
        loadView("Transactions.fxml");
    }

    private void loadView(String fxml) {
        try {
            Node view = FXMLLoader.load(getClass().getResource(fxml));
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the view " + fxml);
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
