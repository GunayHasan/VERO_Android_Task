package com.gnyapp.vero_android_task.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.gnyapp.vero_android_task.models.Task;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks")
    Flowable<List<Task>> getTasks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToTasks(Task task);

    @Delete
    Completable  removeFromTask(Task task);

}
