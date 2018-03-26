package com.blueotter.hainguyenminh.tasktracker.data.remote.repository;

import android.support.annotation.NonNull;

import com.blueotter.hainguyenminh.tasktracker.base.DataCallback;
import com.blueotter.hainguyenminh.tasktracker.data.local.db.Task;
import com.blueotter.hainguyenminh.tasktracker.ui.task.TasksPresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by HaiNM on 21/03/2018.
 */

public interface TasksDataSource {

    void getTasks(DatabaseReference databaseReference, DataCallback dataCallback);

    void saveTask(@NonNull Task task);

    void completeTask(@NonNull Task task);

    void completeTask(@NonNull String taskId);

    void activateTask(@NonNull Task task);

    void activateTask(@NonNull String taskId);

    void getTasks(DatabaseReference databaseReference, TasksPresenter.OnTaskEventListener onTaskEventListener);
}
