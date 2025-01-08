package com.example.todolist.Task;

import com.example.todolist.DataBaseControler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;


public class TaskListImpl extends TaskList{
    public ArrayList<Taskk> tasks;

    public TaskListImpl() {
        this.tasks = new ArrayList<Taskk>();
    }

    public void help(String s){
        update(s);
    }
    public  void addTask(Taskk task, String s){

        DataBaseControler.create(task , DataBaseControler.getUserId(s)) ;
        System.out.println("add");
        update(s);
    }

    public void deleteTask(Taskk task , String s){
        DataBaseControler.deleteById(task.getId_T(), DataBaseControler.getUserId(s));
        update(s);
    }

    public void editTask(int idOfTask ,Taskk task , String s){
        for (Taskk task1 : this.tasks) {
            if (task1.getId_T() == idOfTask) {
                task1.edit(task);
                System.out.println("be me2");
                DataBaseControler.update(task1 , DataBaseControler.getUserId(s));
                update(s);
                break;
            }
        }
    }

    public ArrayList<Taskk> displayTasks(){
        return this.tasks ;
    }




    //sort by date
    /*public void sortByDate(){
        if(this.tasks!=null)
        this.tasks.sort(Comparator.comparing(item -> item.getDate()));
        else System.out.println("error1");
    }*/

    public void sortByDate() {
        if (this.tasks != null) {
            this.tasks.sort(Comparator.comparing(item -> {
                String priority = item.getPriority().toLowerCase();
                if (priority.equals("high")) return 1;
                else if (priority.equals("medium")) return 2;
                else return 3;
            }));
        } else {
            System.out.println("error1");
        }
    }

    public ArrayList<Taskk> search(String word){
        ArrayList<Taskk> taskgoal = new ArrayList<>();
        for (Taskk task : this.tasks) {
            if (task.getName().contains(word) || task.getDescription().contains(word)) {
                taskgoal.add(task);
            }
        }
        return taskgoal;
    }


    //searching by date
    public ArrayList<Taskk> search4(Date d){
        ArrayList<Taskk> taskgoal = new ArrayList<>();
        for (Taskk task : this.tasks) {
            if (task.getDate()==d) {
                taskgoal.add(task);
            }
        }
        return taskgoal;
    }
    //*********************************************************


    public ArrayList<Taskk> search2(String stut){
        ArrayList<Taskk> taskgoal = new ArrayList<>();
        if(stut.equals("complated")){
           for (Taskk task : this.tasks) {
               if (task.getState().equals(Complet.complated)) {
                   taskgoal.add(task);
               }
           }
        }
        else {
            if(stut.equals("not_complated")) {
                for (Taskk task : this.tasks) {
                    if (task.getState().equals(Complet.not_complated)){
                        taskgoal.add(task);
                    }
                }
            }
            else{
                for (Taskk task : this.tasks) {
                    if (task.getState().equals(Complet.abandoned)){
                        taskgoal.add(task);
                    }
                }
            }
        }
        System.out.println("helllllllllllllo2");
        if(taskgoal.isEmpty())System.out.println("helllllllllllllo3");
        return taskgoal;
    }
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    public ArrayList<Taskk> search3(String mood){
        ArrayList<Taskk> taskgoal = new ArrayList<>();
        for (Taskk task : this.tasks) {
            if (task.getMood().equals(mood)) {
                taskgoal.add(task);
            }
        }
        if(taskgoal.isEmpty())System.out.println("helllllllllllllo3");
        return taskgoal;
    }
    //********************************************************
    @Override
    public String toString() {
        return "TaskListImpl{" + "tasks=" + tasks + '}';
    }

    public void update(String s){

        if(this.tasks!=null) {
            this.tasks = DataBaseControler.findAll(DataBaseControler.getUserId(s));
            sortByDate();
        }
        else System.out.println("error");
    }
}
