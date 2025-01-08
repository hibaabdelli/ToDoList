package com.example.todolist.Task;

import java.util.Date;

public interface Taskk {

    public void edit(Taskk task) ;
    public void markAsCompleted() ;
    public void markAsAbandoned() ;
    public void markAsNotComplated() ;
    public int getId_T() ;
    public Date getDate() ;
    public String getName() ;
    public String getDescription() ;
    public Complet getState() ;
    public String getPriority() ;
    public String getMood();
}
