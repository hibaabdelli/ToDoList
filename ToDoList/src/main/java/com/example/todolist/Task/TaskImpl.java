package com.example.todolist.Task;

import java.time.LocalDate;

public class TaskImpl implements Taskk{
    private String name;
    private String description;
    private LocalDate date;
    private Complet state;
    private int id_of_task;

    public TaskImpl(int id_of_task,String name, String description, LocalDate date, Complet state){
        this.id_of_task = id_of_task ;
        this.name = name;
        this.description = description;
        this.date = date;
        this.state = Complet.not_copmleted;
    }

    @Override
    public void editing1(TaskImpl task) {
        this.name = task.name;
        this.date = task.date;
        this.description = task.description;
    }

    /*@Override
    public void deleting(TaskImpl task) {

    }*/

    @Override
    public void cencel(TaskImpl task) {
        this.state = Complet.abandoned ;
    }

    @Override
    public void MarkinAsComp(TaskImpl task) {
        this.state = Complet.copmleted ;
    }
    public int getId_of_task(){
        return this.id_of_task;
    }
    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }
}
