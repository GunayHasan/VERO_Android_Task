package com.gnyapp.vero_android_task.response;
import com.gnyapp.vero_android_task.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskResponse {

    private ArrayList<Task> tasks;

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

}
