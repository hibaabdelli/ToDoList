package com.example.todolist;

import com.example.todolist.Task.TaskImpl;
import com.example.todolist.Task.TaskListImpl;
import com.example.todolist.Task.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import com.example.todolist.DataBaseControler;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class loginController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button login;
    @FXML
    private Button reg;
    String pass, user;

    public int check(){
        if(username.getText().isEmpty())return 0;
        if(password.getText().isEmpty())return 0;
        return 1;
    }

    public void log_in(ActionEvent event) throws IOException {

        if(check()==1){
            //add task of database;
            String user = DataBaseControler.login(username.getText(),password.getText());
            if(user!=null) {
                //TaskListImpl taskList = new TaskListImpl(user);
                try {
                    // Load the FXML file for Page 2
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Home1.fxml"));
                    Parent root = loader.load();
                    HomeController page2 = loader.getController();
                    page2.getuser(user);
                    // Get the current stage and set the new scene
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("home");
                    stage.show();

                    Notifications.create()
                            .title("Login Successful")
                            .text("Welcome back, " + user + "!")
                            .showInformation();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Node source = (Node) event.getSource();
                Scene scene = source.getScene();
                scene.getRoot().setStyle("-fx-background-color: #ff0000;"); // Light red background
                System.out.println("encoracttt");
            }
        }
        else {
            // chanege collor to red
            Node source = (Node) event.getSource();
            Scene scene = source.getScene();
            scene.getRoot().setStyle("-fx-background-color: #ff0000;"); // Light red background
            System.out.println("Invalid input! Highlighting errors.");
        }

    }


    public void regester(ActionEvent event) throws IOException {

        if(check()==1){

            //add task of database;
            System.out.println("selected99");
            DataBaseControler.createUser(username.getText(),password.getText());
            try {
                // Load the FXML file for Page 2
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                Parent root = loader.load();

                // Get the current stage and set the new scene
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("home");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            // chanege collor to red
            Node source = (Node) event.getSource();
            Scene scene = source.getScene();
            scene.getRoot().setStyle("-fx-background-color: #ff0000;"); // Light red background
            System.out.println("Invalid input! Highlighting errors.");
        }

    }
}
