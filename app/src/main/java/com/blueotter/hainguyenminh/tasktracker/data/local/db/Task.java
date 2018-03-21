package com.blueotter.hainguyenminh.tasktracker.data.local.db;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by HaiNM on 20/03/2018.
 */

@IgnoreExtraProperties
public class Task {

    public String taskName;
    public String taskDescription;

    public Task() {
    }

    public Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}
