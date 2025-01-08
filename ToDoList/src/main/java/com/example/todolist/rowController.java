package com.example.todolist;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class rowController {

    @FXML
    private Label task1;
    @FXML
    private Label state;
    @FXML
    private Button delite;
    @FXML
    private HBox hbox;

    public void edit(ActionEvent event)throws IOException {
        try {
            // Load the FXML file for Page 2
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Task.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("task");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
