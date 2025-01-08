package com.example.todolist.Task;

public class Users {
    private String username;
    private String password;
    private int id;

    public Users(String name, String pass){
        this.username = name;
        this.password = pass;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Users(String name, String pass, int id){
        this.username = name;
        this.password = pass;
        this.id = id;
    }

}
