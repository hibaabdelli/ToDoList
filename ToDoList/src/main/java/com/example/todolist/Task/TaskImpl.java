package com.example.todolist.Task;

import javafx.concurrent.Task;

import java.util.Date;

public class TaskImpl implements Taskk{
    private String name;
    private String description;
    private Date date;
    private Complet state;
    private int id_T;
    private String priority;
    private String mood;


    public TaskImpl(int id_of_task,String name, String description, Date date, Complet state, String priority, String mood){
        this.id_T = id_of_task ;
        this.name = name;
        this.description = description;
        this.date = date;
        this.state = state;
        this.priority = priority;
        this.mood = mood;
        System.out.println(mood+ "1");
    }

    public TaskImpl(String name, String description, Date date, String priority, String mood) {

        this.name = name;
        this.description = description;
        this.date = date ;
        this.state = Complet.not_complated ;
        this.priority = priority;
        this.mood = mood;
        System.out.println(mood +"2");
    }

    @Override
    public String toString() {
        return "TaskImpl{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", state=" + state +
                ", id_T=" + id_T +
                '}';
    }

    public void editing1(TaskImpl task) {
        this.name = task.name;
        this.date = task.date;
        this.description = task.description;
    }

    public void edit(Taskk task) {
        this.name = task.getName();
        this.description = task.getDescription();
        this.date = task.getDate();
        this.state = task.getState();
        this.priority = task.getPriority();
        this.mood = task.getMood();
        System.out.println("be me");
    }


    /**
     * this is impl of method mark As Completed
     */
    public void markAsCompleted(){
        this.state = Complet.complated ;
    }

    public void markAsAbandoned(){
        this.state = Complet.abandoned ;
    }

    public void markAsNotComplated(){
        this.state = Complet.not_complated ;
    }

    public int getId_T() {
        return this.id_T;
    }

    public Date getDate() {
        return this.date;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Complet getState() {
        return this.state;
    }

    @Override
    public String getPriority() {
        return this.priority;
    }

    @Override
    public String getMood() {
        return this.mood;
    }


}
