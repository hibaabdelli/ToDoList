package com.example.todolist;

import com.example.todolist.Task.TaskImpl;
import com.example.todolist.Task.TaskList;
import com.example.todolist.Task.TaskListImpl;
import com.example.todolist.Task.Taskk;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class Taskcontroller implements Initializable {
    @FXML
    private TextField name ;
    @FXML
    private TextField description ;
    @FXML
    private DatePicker date ;
    @FXML
    private Button done;
    @FXML
    private ChoiceBox<String> choiceBox;
    private String [] types={"high","low","medium"};
    String ues;
    TaskList tasks = new TaskListImpl();
    public void getus(String s){
        this.ues = s;
        tasks.help(s);
    }


    private String selected;
    private String set;
    @FXML
    private ChoiceBox<String> mood;
    private String [] moods={"Home&Family","Education","Health","Work"};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll(types);
        mood.getItems().addAll(moods);
        mood.setOnAction(e -> {
            set = mood.getValue();
        });
        choiceBox.setOnAction(event -> {
            selected = choiceBox.getValue();
        });
    }

    //check if the user inter info
    public int checkinputvalid(){
        if(name.getText().isEmpty())return 0;
        if(description.getText().isEmpty())return 0;
        if(date.getValue()==null)return 0;
        return 1;
    }

    public TaskList getTasks() {
        return tasks;
    }


    //addition of the task
    public void onAddbtn(ActionEvent event) throws IOException {

        if(checkinputvalid()==1){
            //add task of database;
            tasks.addTask(new TaskImpl(name.getText(), description.getText(), Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),selected,set),ues);
            //System.out.println(ues);
            try {
                // Load the FXML file for Page 2
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Home1.fxml"));
                Parent root = loader.load();
                HomeController page2 = loader.getController();
                page2.getuser(ues);
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
    // back return me too home page ;
    public void handleBackButton(ActionEvent event) throws IOException{
        try {
            // Load the FXML file for Page 2
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home1.fxml"));
            Parent root = loader.load();
            HomeController page2 = loader.getController();
            page2.getuser(ues);
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

    public void DisplayInfo(Taskk task){
        name.setText(task.getName());
        description.setText(task.getDescription());
        choiceBox.setValue(task.getPriority());
        LocalDate li = LocalDate.of(task.getDate().getYear(), task.getDate().getMonth(), task.getDate().getDay()) ;
        date.setValue(li);
        mood.setValue(task.getMood());
        //LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //date.setValue(localDate);
        /**/

    }

    public void onEdit(ActionEvent event) throws IOException{

    }

}

















