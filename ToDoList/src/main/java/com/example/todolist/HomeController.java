package com.example.todolist;


import com.example.todolist.Task.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class HomeController implements Initializable {

    @FXML
    private VBox vbox ;
    @FXML
    private TextField tfSearch ;
    @FXML
    private Button add;
    @FXML
    private Button b1;
    @FXML
    private VBox vbox2 ;
    @FXML
    private ChoiceBox<String> choice;
    @FXML
    private ChoiceBox<String> choice2;
    @FXML
    private Label sating ;
    String userr;
    //TaskListImpl taskes;
    TaskListImpl taskes = new TaskListImpl();int k=0;
    public void getuser(String user) throws IOException {
        userr = user;
        taskes.help(user);
        displayAllTasks();
        //this.taskes = new TaskListImpl(user);
    }
    private String [] moods={"Home&Family","Education","Health","Work"};
    private String[] types={"complated","not_complated","abandoned"};
    int x=0,y=0,z=0;


    //display DB.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choice.getItems().addAll(types);
        choice2.getItems().addAll(moods);
        choice.setOnAction(event -> {
            try {
                select();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        choice2.setOnAction(ev -> {
            try {
                select2();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        vbox.getChildren().clear();
        /*try {
            //displayAllTasks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        monitorTaskDeadlines();


    }

    /*private void rowHBox(String name, String discription, Complet state)  throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("rowtask.fxml"));
        HBox row = fxmlLoader.load() ;
        Label namel =  (Label) row.getChildren().get(0) ;
        Label statl =  (Label) row.getChildren().get(1) ;
        CheckBox statc = (CheckBox) row.getChildren().get(2) ;
        Button delete = (Button) row.getChildren().get(3) ;
        Button edit = (Button) row.getChildren().get(4) ;


        namel.setText(name) ;
        statl.setText(state.toString()) ;
        if (state == Complet.not_complated) {
            statc.setSelected(false);
        } else if (state == Complet.complated) {
            statc.setSelected(true);
        }


        row.getChildren().clear();
        row.getChildren().add(namel) ;
        row.getChildren().add(statl);
        row.getChildren().add(statc);
        row.getChildren().add(delete);
        row.getChildren().add(edit);

        vbox.getChildren().add(row);
    }*/
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

    private void monitorTaskDeadlines() {
        Timer timer = new Timer(true); // Daemon thread
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                LocalDate today = LocalDate.now();
                for (Taskk task : taskes.displayTasks()) {
                    LocalDate li = LocalDate.of(task.getDate().getYear(), task.getDate().getMonth(), task.getDate().getDay()) ;
                    if (today.isAfter(li)) {
                        Platform.runLater(() -> {
                            Notifications.create()
                                    .title("Deadline Passed")
                                    .text("The deadline for task \"" + task.getName() + "\" has passed!")
                                    .showWarning();
                        });
                        System.out.println("fuck off1");
                    } else if (today.isEqual(li)) {
                        Platform.runLater(() -> {
                            Notifications.create()
                                    .title("Deadline Today")
                                    .text("The task \"" + task.getName() + "\" is due today!")
                                    .showInformation();
                        });
                        System.out.println("fuck off2");
                    }
                }
            }
        }, 0, 1000); // Check every 24 hours (adjust as needed)
    }

    //***************************************************************

    public void createTodoRow(String task, Complet state,Taskk taskh ) {
        // Create HBox
        HBox hbox = new HBox();
        hbox.setPrefSize(300, 45);
        hbox.setStyle("-fx-background-color: #51717b; -fx-background-radius: 2em;");

        // Create Labels
        Label taskLabel = new Label(task);
        taskLabel.setPrefSize(120, 45);
        taskLabel.setTextFill(javafx.scene.paint.Color.web("#f2eeee"));
        taskLabel.setFont(new Font(17));
        taskLabel.setAlignment(javafx.geometry.Pos.CENTER);

        Label stateLabel = new Label();
        if(state.equals(Complet.not_complated)||state.equals(Complet.abandoned)) {
             stateLabel.setText("not_complated");
            stateLabel.setPrefSize(115, 45);
            stateLabel.setTextFill(javafx.scene.paint.Color.web("#f2ebeb"));
            stateLabel.setFont(new Font(15));
            stateLabel.setAlignment(javafx.geometry.Pos.CENTER);
        }else {
             stateLabel.setText("complated");
            stateLabel.setPrefSize(115, 45);
            stateLabel.setTextFill(javafx.scene.paint.Color.web("#f2ebeb"));
            stateLabel.setFont(new Font(15));
            stateLabel.setAlignment(javafx.geometry.Pos.CENTER);
        }

        // Create CheckBox
        CheckBox checkBox = new CheckBox();
        if(taskh.getState().equals(Complet.complated)){checkBox.setSelected(true);System.out.println("fuch");}
        else checkBox.setSelected(false);
        checkBox.setPrefSize(30, 45);
        checkBox.setOnAction(event -> {
            if (checkBox.isSelected()) {
                taskh.markAsCompleted();
                taskes.editTask(taskh.getId_T(), taskh, userr);
                stateLabel.setText("completed");
                System.out.println("goooooo");
                x++;
            } else {
                taskh.markAsNotComplated();
                taskes.editTask(taskh.getId_T(), taskh, userr);
                stateLabel.setText("not_complated");
                System.out.println("goooooo1");
                y++;
            }
        });
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        Label stateLabel2 = new Label();
        if(state.equals(Complet.abandoned)) {
            stateLabel2.setText("abandoned");
            stateLabel2.setPrefSize(115, 45);
            stateLabel2.setTextFill(javafx.scene.paint.Color.web("#f2ebeb"));
            stateLabel2.setFont(new Font(15));
            stateLabel2.setAlignment(javafx.geometry.Pos.CENTER);
        }else {
            stateLabel2.setText("not_abandoned");
            stateLabel2.setPrefSize(115, 45);
            stateLabel2.setTextFill(javafx.scene.paint.Color.web("#f2ebeb"));
            stateLabel2.setFont(new Font(15));
            stateLabel2.setAlignment(javafx.geometry.Pos.CENTER);
        }

        CheckBox checkBox2 = new CheckBox();
        if(taskh.getState().equals(Complet.abandoned)){checkBox2.setSelected(true);System.out.println("fuch2");}
        else checkBox2.setSelected(false);
        checkBox2.setPrefSize(30, 45);
        checkBox2.setOnAction(event -> {
            if (checkBox2.isSelected()) {
                taskh.markAsAbandoned();
                taskes.editTask(taskh.getId_T(), taskh, userr);
                stateLabel2.setText("abandoned");
                System.out.println("goooooo");
                z++;
            } else {
                taskh.markAsNotComplated();
                taskes.editTask(taskh.getId_T(), taskh,userr);
                stateLabel2.setText("not_abandoned");
                System.out.println("goooooo1");
            }
        });
        //++++++++++++++++++++++++++++++++++++++=


        /*checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                taskh.markAsCompleted();
                stateLabel.setText("completed");
            } else {
                taskh.markAsNotComplated();
                stateLabel.setText(state.toString()); // Reset to original state
            }
            taskes.editTask(taskh.getId_T(), taskh);
        });*/
        // Create Buttons
        Button deleteButton = new Button("delite");
        deleteButton.setPrefSize(57, 45);
        deleteButton.setStyle("-fx-background-color: #3366cc; -fx-background-radius: 2em;");
        deleteButton.setOnAction(event -> {
            // Handle task deletion
            taskes.deleteTask(taskh, userr);
            // Remove the HBox from the VBox
            vbox.getChildren().remove(hbox);
        });
        /* Button editButton = new Button("edit");
        editButton.setPrefSize(54, 46);
        editButton.setStyle("-fx-background-color: #66ff99; -fx-background-radius: 2em;");*/
        //editButton.setOnAction(event -> onEdit());*/
        //$$$$$$$$$$$$$$$$$$$$$$$$$$
        hbox.setOnMouseClicked(event -> {
            try {
                taskes.editTask(taskh.getId_T(), taskh, userr);
                //taskes.deleteTask(taskh);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Task.fxml"));
                Parent root = loader.load();
                // desplay information of tesk on second page
                Taskcontroller page2 = loader.getController();
                Taskcontroller page3 = loader.getController();
                page2.DisplayInfo(taskh);
                page3.getus(userr);
                // Get the Stage from the Event
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("task");
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
        x=x*100;x=x/k;
        y=y*100;y=y/k;
        z=z*100;z=z/k;

        //taskes.editTask(taskh.getId_T(), taskh);
        //$$$$$$$$$$$$$$$$$$$$$$$$$$
        // Add children to HBox
        hbox.getChildren().addAll(taskLabel, stateLabel, checkBox, stateLabel2, checkBox2, deleteButton);
        vbox.getChildren().add(hbox);
    }
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    public void displayAllTasks() throws IOException {

        vbox.getChildren().clear();
        for (Taskk task : taskes.displayTasks()){
            k++;
            //rowHBox(task.getName(),task.getDescription(),task.getState());
            createTodoRow(task.getName(),task.getState(),task);
        }
    }
    /*public void displayAllTasks(ArrayList<Taskk> tasks) throws IOException {

        vbox.getChildren().clear();
        for (Taskk task : tasks){
            createTodoRow(task.getName(),task.getState(),task);
        }
    }*/
    public void ADDB(ActionEvent event) throws IOException {
        try {
            //System.out.println(userr + "fiiil");
            // Load the FXML file for Page 2
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Task.fxml"));
            Parent root = loader.load();
            Taskcontroller page3 = loader.getController();
            page3.getus(userr);
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

    /*public void edit(ActionEvent event)throws IOException {
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
    }*/

    @FXML
    protected void searshh() throws IOException {
        ArrayList<Taskk> searshingTasks = taskes.search(tfSearch.getText());
        System.out.println("hhhhhhhhhh");
        displayAllTasks1(searshingTasks);
    }


    //searching by date
    @FXML
    protected void searshh2() throws IOException, ParseException {
        String text = tfSearch.getText(); // Assume tfSearch is your text field
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(text);
        ArrayList<Taskk> searshingTasks = taskes.search4(date);
        System.out.println("hhhhhhhhhh");
        displayAllTasks1(searshingTasks);
    }

    public void displayAllTasks1(ArrayList<Taskk> tasks) throws IOException {
        vbox.getChildren().clear();
        for (Taskk task : tasks) {
            createTodoRow(task.getName(), task.getState(), task);
        }
    }

    //***************************************************************************$$$$$$$$$$$$$$$$$$$$$$$$$
    public void createTodoRow2(String task, Complet state,Taskk taskh ) {
        // Create HBox
        HBox hbox = new HBox();
        hbox.setPrefSize(300, 45);
        hbox.setStyle("-fx-background-color:  #89A8B2; -fx-background-radius: 2em;");
        // Create Labels
        Label taskLabel = new Label(task);
        taskLabel.setPrefSize(120, 45);
        taskLabel.setTextFill(javafx.scene.paint.Color.web("#f2eeee"));
        taskLabel.setFont(new Font(17));
        taskLabel.setAlignment(javafx.geometry.Pos.CENTER);

        Label stateLabel = new Label(state.toString());
        stateLabel.setPrefSize(115, 45);
        stateLabel.setTextFill(javafx.scene.paint.Color.web("#f2ebeb"));
        stateLabel.setFont(new Font(15));
        stateLabel.setAlignment(javafx.geometry.Pos.CENTER);


        Label dateLabel = new Label(taskh.getDate().toString());
        dateLabel.setPrefSize(115, 45);
        dateLabel.setTextFill(javafx.scene.paint.Color.web("#f2ebeb"));
        dateLabel.setFont(new Font(15));
        dateLabel.setAlignment(javafx.geometry.Pos.CENTER);

        Label moodLabel = new Label(taskh.getMood());
        moodLabel.setPrefSize(115, 45);
        moodLabel.setTextFill(javafx.scene.paint.Color.web("#f2ebeb"));
        moodLabel.setFont(new Font(15));
        moodLabel.setAlignment(javafx.geometry.Pos.CENTER);

        /* Create CheckBox
        CheckBox checkBox = new CheckBox();
        if(taskh.getState().equals(Complet.complated)){checkBox.setSelected(true);System.out.println("fuch");}
        else checkBox.setSelected(false);
        checkBox.setPrefSize(50, 45);
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                taskh.markAsCompleted();
                taskes.editTask(taskh.getId_T(),taskh);
                stateLabel.setText("completed");
            } else {
                taskh.markAsNotComplated();
                taskes.editTask(taskh.getId_T(),taskh);
                stateLabel.setText(state.toString());// Reset to original state
            }
            //taskes.editTask(taskh.getId_T(),taskh);
        });*/

        /*hbox.setOnMouseClicked(event -> {
            try {
                taskes.editTask(taskh.getId_T(), taskh);
                taskes.deleteTask(taskh);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Task.fxml"));
                Parent root = loader.load();
                // desplay information of tesk on second page
                Taskcontroller page2 = loader.getController();
                page2.DisplayInfo(taskh);
                // Get the Stage from the Event
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("task");
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });*/
        hbox.getChildren().addAll(taskLabel, stateLabel, dateLabel, moodLabel);
        vbox2.getChildren().add(hbox);
    }
    public void displayAllTasks3(ArrayList<Taskk> tasks) throws IOException {
        vbox2.getChildren().clear();
        for (Taskk task : tasks) {
            createTodoRow2(task.getName(), task.getState(), task);
        }
    }

    public void select() throws IOException {
        vbox2.getChildren().clear();
        String stut = choice.getValue();
        ArrayList<Taskk> searshingTasks = taskes.search2(stut);
        System.out.println("hhhhhhhhhh22");
        displayAllTasks3(searshingTasks);
        displayAllTasks();
    }

    public void select2() throws IOException {
        vbox2.getChildren().clear();
        String mood = choice2.getValue();
        ArrayList<Taskk> searshingTasks = taskes.search3(mood);
        System.out.println("hhhhhhhhhh22");
        displayAllTasks3(searshingTasks);
        displayAllTasks();
    }
    //***************************************************************************

    public void handleBackButton(ActionEvent event) throws IOException{
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
}