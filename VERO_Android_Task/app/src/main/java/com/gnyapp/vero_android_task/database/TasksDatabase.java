package com.gnyapp.vero_android_task.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gnyapp.vero_android_task.dao.TaskDao;
import com.gnyapp.vero_android_task.models.Task;

@Database(entities = Task.class, version = 1, exportSchema = false)
public abstract class TasksDatabase extends RoomDatabase {

    private static TasksDatabase tasksDatabase;

    public static synchronized TasksDatabase getTasksDatabase(Context context) {
        if(tasksDatabase == null) {
            tasksDatabase = Room.databaseBuilder(
                    context,
                    TasksDatabase.class,
                    "task_db"
            ).build();
        }
        return tasksDatabase;
    }
    public abstract TaskDao taskDao();

}
