package com.example.mytodolist.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mytodolist.Data.DaoRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    public static DaoRepository repository;
    //private DaoRepository repository;
    private final LiveData<List<Task>> getAllTaskViewModel;



    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new DaoRepository(application);
        getAllTaskViewModel = repository.fetchAllTaskRepo();
    }

    public static void insertViewModel(Task task){
        repository.insertRepo(task);
    }

    public static void updateViewModel(Task task){
        repository.updateRepo(task);
    }

    public static void deleteAllViewModel(){
        repository.deleteAllRepo();
    }

    public static void deleteViewModel(Task task){
        repository.deleteRepo(task);
    }

    public LiveData<List<Task>>fetchAllTaskViewModel(){
        return this.getAllTaskViewModel;
    }

    public LiveData<Task>fetchTaskViewModel(long id){
        return repository.fetchSingleTask(id);
    }
}
