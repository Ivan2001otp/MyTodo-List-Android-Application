package com.example.mytodolist.Adapter;

import com.example.mytodolist.Model.Task;

public interface OnToDoClickListener {
    void toDoOnclick(int position,Task task);
    void radioOnClick(Task task);
    void chipOnClick(Task task);
}
