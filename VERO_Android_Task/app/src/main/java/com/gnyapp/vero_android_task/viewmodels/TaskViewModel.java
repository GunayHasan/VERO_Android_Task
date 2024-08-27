package com.gnyapp.vero_android_task.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.gnyapp.vero_android_task.models.Task;
import com.gnyapp.vero_android_task.repositories.TaskRepository;
import com.gnyapp.vero_android_task.request.LoginRequest;
import com.gnyapp.vero_android_task.response.LoginResponse;

import java.util.List;

public class TaskViewModel extends ViewModel {

    private TaskRepository taskRepository;

    public TaskViewModel() {
        taskRepository = new TaskRepository();
    }

    public LiveData<List<Task>> getAllTasks(String authorization) {
        return taskRepository.getAllTasks(authorization);
    }

    public LiveData<LoginResponse> login(String authorization, LoginRequest loginRequest) {
        return taskRepository.login(authorization, loginRequest);
    }

}
