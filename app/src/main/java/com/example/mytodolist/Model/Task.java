package com.example.mytodolist.Model;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.mytodolist.Data.Priority;

import java.util.Date;

@Entity(tableName = "toDoList_database")
public class Task {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="taskId")
    private long taskId;

    @ColumnInfo(name="Task")
    private String task;

    @ColumnInfo(name="dueDate")
    private Date dueDate;

    @ColumnInfo(name="createdDate")
    private Date created_date;

    @ColumnInfo(name="isDone")
    private boolean isDone;

    public Priority priority;

    @Ignore
    public Task(){}

    public Task( String task, Date dueDate, Date created_date, Priority priority,boolean isDone) {
        //this.taskId = taskId;
        this.task = task;
        this.dueDate = dueDate;
        this.created_date = created_date;
        this.priority = priority;
        this.isDone = isDone;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
