package com.example.todolist.Task;

import java.util.ArrayList;

public abstract  class TaskList {
    public abstract void addTask(Taskk task, String s);
    public abstract void deleteTask(Taskk task, String s);
    public abstract void editTask(int idOfTask,Taskk task ,String s);
    public abstract ArrayList<Taskk> displayTasks();
    public abstract void sortByDate() ;
    public abstract ArrayList<Taskk> search(String word) ;
    public abstract void help (String s);
}
