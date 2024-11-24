package com.example.todolist.Task;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskListImpl extends TaskList{
    private ArrayList<TaskImpl> tasks;
    public TaskListImpl(){
        this.tasks = new ArrayList<>();
    }
    @Override
    public Boolean adding(TaskImpl task) {
        //add to database.
        Connection con = DataBseCon.getConnection();
        if (con == null) {
            return;
        }

        String query = "INSERT INTO tasks (id, name, description, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, task.getid_of_task());
            preparedStatement.setString(2, task.getName());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4, task.getDate());

            preparedStatement.executeUpdate();
            System.out.println("Task added successfully!");

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                System.err.println("Error closing connection: " + se.getMessage());
            }
        }
        return this.tasks.add(task);
    }

    @Override
    public Boolean deleting(TaskImpl task) {
        //ramove from database.
        Connection con = DataBseCon.getConnection();
        if (con == null) {
            return;
        }

        String query = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, task.getid_of_task());

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Task deleted successfully!");
            } else {
                System.out.println("No task found with ID: " + task.getid_of_task());
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                System.err.println("Error closing connection: " + se.getMessage());
            }
        }
        return this.tasks.remove(task);
    }

    @Override
    public void editing(int id_of_task,TaskImpl task) {
        Connection con = DataBseCon.getConnection();
        for(TaskImpl t:tasks){
            if(t.getId_of_task()==id_of_task){
                if (con == null) {
                    return;
                }
                String query = "UPDATE tasks SET name = ?, date = ?, description = ? WHERE id = ?";
                try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                    preparedStatement.setString(1, task.getName());
                    preparedStatement.setString(2, task.getDate());
                    preparedStatement.setString(3, task.getDescription());
                    preparedStatement.setInt(4, task.getid_of_task());

                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Task updated successfully!");
                    } else {
                        System.out.println("No task found with ID: " + task.getid_of_task());
                    }

                } catch (SQLException se) {
                    System.err.println("Error while updating task: " + se.getMessage());
                    se.printStackTrace();
                } finally {
                    try {
                        con.close();
                    } catch (SQLException se) {
                        System.err.println("Error closing connection: " + se.getMessage());
                    }
                }
            }
                t.editing1(task);
            }
        }
    }

    @Override
    public void displying() {

    }

    public ArrayList<TaskImpl> searsh(String word) {
        ArrayList<TaskImpl> find_taskes = new ArrayList<>();
        for(TaskImpl t:tasks){
            if(t.getDescription().contains(word) || t.getName().contains(word)){
                find_taskes.add(t);
            }
        }
        return find_taskes;
    }
}
