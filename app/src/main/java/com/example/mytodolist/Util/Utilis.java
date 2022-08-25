package com.example.mytodolist.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;

import com.example.mytodolist.Data.Priority;
import com.example.mytodolist.MainActivity;
import com.example.mytodolist.Model.Task;
import java.util.Date;

public class Utilis {
    public static String formatDate(long date){
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("EEE, MMM d");
        return simpleDateFormat.format(date);
    }
    public static void hideSoftKeyboardUtil(View view){
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int priorityColor(Task task){
        int color = 0;
        if(task.getPriority() == Priority.HIGH){
            color = Color.argb(200,201,21,23);
        }else if(task.getPriority() == Priority.LOW){
            color = Color.argb(200,236,23,218);
        }else if(task.getPriority() == Priority.MEDIUM){
            color = Color.argb(200,232,128,32);
        }
        return color;
    }


}
