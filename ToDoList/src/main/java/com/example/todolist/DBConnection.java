package com.example.todolist;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


    public class DBConnection {

        private static final String host = "127.0.0.1";
        private static final int port = 3306;
        private static final String name = "newdata";
        private static final String username = "root";
        private static final String password = "";
        private  static Connection connection ;

        public static Connection getConnection(){
            try {
                connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", host, port, name), username, password);
            }catch (SQLException se){
                se.printStackTrace();
            }
            return connection;
        }

    }

