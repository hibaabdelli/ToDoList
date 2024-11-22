package com.example.todolist.Task;

public abstract  class TaskList {
    public abstract Boolean adding (TaskImpl task);
    public abstract Boolean deleting (TaskImpl task);
    public abstract void editing (int id_of_task,TaskImpl task);
    public abstract void displying ();
}
