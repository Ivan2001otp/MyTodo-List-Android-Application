package com.example.mytodolist.Model;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    public MutableLiveData<Task>selectItem = new MutableLiveData<>();
    private boolean isEdit;
    //setters for the mutable live data
    public void setSelectItem(Task task){
        selectItem.setValue(task);
    }
    public LiveData<Task> getSelectedItem(){
        return selectItem;
    }

    //getters
    public void setIsEdit(boolean isEdit){
        this.isEdit = isEdit;
    }
    public boolean getIsEdit(){
        return this.isEdit;
    }


}
