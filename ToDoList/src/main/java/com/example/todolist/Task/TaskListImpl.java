package com.example.todolist.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskListImpl extends TaskList{
    private ArrayList<TaskImpl> tasks;
    public TaskListImpl(){
        this.tasks = new ArrayList<>();
    }
    @Override
    public Boolean adding(TaskImpl task) {
        //add to database.
        return this.tasks.add(task);
    }

    @Override
    public Boolean deleting(TaskImpl task) {
        //ramove from database.
        return this.tasks.remove(task);
    }

    @Override
    public void editing(int id_of_task,TaskImpl task) {
        for(TaskImpl t:tasks){
            if(t.getId_of_task()==id_of_task){
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
