package com.example.todolist;


import com.example.todolist.DBConnection;
import com.example.todolist.Task.TaskImpl;
import com.example.todolist.Task.Complet;
import com.example.todolist.Task.Taskk;
import com.example.todolist.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseControler {

    public static String login(String username, String password) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return null;
        }

        String query = "SELECT * FROM Users WHERE username = ?;";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPasswordHash = resultSet.getString("password");
                if (DataBaseControler.checkPassword(username,password)) {
                    return username;
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return null;
    }

    public static ArrayList<Taskk> findAll(int userId) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return null;
        }

        ArrayList<Taskk> tasks = new ArrayList<>();
        String query = "SELECT * FROM Tasks WHERE user_id = ?;";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Taskk task = new TaskImpl(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDate("date"),
                        Complet.valueOf(resultSet.getString("state")),
                        resultSet.getString("priority"),
                        resultSet.getString("mood")
                );
                tasks.add(task);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }

    public static Taskk findById(int id, int userId) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return null;
        }
        String query = "SELECT * FROM Tasks WHERE id = ? AND user_id = ?;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new TaskImpl(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDate("date"),
                        Complet.valueOf(resultSet.getString("state")),
                        resultSet.getString("priority"),
                        resultSet.getString("mood")
                );
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return null;
    }

    public static void create(Taskk task, int userId) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            System.out.println("Database connection error.");
            return;
        }

        String query = "INSERT INTO Tasks(user_id, name, description, date, state, priority, mood) VALUES(?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, task.getName());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setDate(4, Utils.getSqlDate(task.getDate()));
            preparedStatement.setString(5, task.getState().name());
            preparedStatement.setString(6, task.getPriority());
            preparedStatement.setString(7,task.getMood());
            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void update(Taskk task, int userId) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        String query = "UPDATE Tasks SET name = ?, description = ?, date = ?, state = ? ,priority = ? , mood = ? WHERE id = ? AND user_id = ?;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setDate(3, Utils.getSqlDate(task.getDate()));
            preparedStatement.setString(4, task.getState().name());
            preparedStatement.setString(5, task.getPriority());
            preparedStatement.setString(6, task.getMood());
            preparedStatement.setInt(7, task.getId_T());
            preparedStatement.setInt(8, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void deleteById(int id, int userId) {

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }
        String query = "DELETE FROM Tasks WHERE id = ? AND user_id = ?;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void createUser(String username, String password) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            System.out.println("Database connection error.");
            return;
        }
        String query = "INSERT INTO Users(username, password) VALUES(?, ?);";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            System.out.println("User created successfully.");
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static boolean checkPassword(String username, String password) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            System.out.println("Database connection error.");
            return false;
        }

        String query = "SELECT password FROM Users WHERE username = ?;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                // Compare provided password with the stored password
                if (storedPassword.equals(password)) {
                    System.out.println("Password is correct.");
                    return true;
                } else {
                    System.out.println("Password is incorrect.");
                    return false;
                }
            } else {
                System.out.println("User not found.");
                return false;
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static int getUserId(String username) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            System.out.println("Database connection error.");
            return -1; // Return -1 to indicate an error
        }
        System.out.println(username);
        String query = "SELECT id FROM Users WHERE username = ?;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                System.out.println("User not found get id.");
                return -1; // Return -1 if the user doesn't exist
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return -1; // Return -1 in case of a database error
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
