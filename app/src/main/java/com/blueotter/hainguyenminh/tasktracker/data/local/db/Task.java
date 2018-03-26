package com.blueotter.hainguyenminh.tasktracker.data.local.db;

import android.support.annotation.IntDef;

import com.google.firebase.database.IgnoreExtraProperties;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by HaiNM on 20/03/2018.
 */

@IgnoreExtraProperties
public class Task {

    @IntDef({Status.IDLE, Status.PROCESSING, Status.DONE, Status.CANCELLED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
        int IDLE = 0;
        int PROCESSING = 1;
        int DONE = 2;
        int CANCELLED = 3;
    }

    @IntDef({TaskDetailDisplay.DESCRIPTION, TaskDetailDisplay.ACTION})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TaskDetailDisplay {
        int DESCRIPTION = 0;
        int ACTION = 1;
    }

    @Status
    private int taskStatus;
    private String createDate;
    private String taskName;
    private String taskDescription;
    private int taskAction;
    private String taskRef;
    @TaskDetailDisplay
    private int taskDetailDisplay;

    public Task() {
    }

    public Task(int taskStatus, String createDate, String taskName, String taskDescription, int taskAction, String taskRef) {
        this.taskStatus = taskStatus;
        this.createDate = createDate;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskAction = taskAction;
        this.taskRef = taskRef;
        this.taskDetailDisplay = TaskDetailDisplay.DESCRIPTION;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public int getTaskAction() {
        return taskAction;
    }

    public void setTaskAction(int taskAction) {
        this.taskAction = taskAction;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskRef() {
        return taskRef;
    }

    public void setTaskRef(String taskRef) {
        this.taskRef = taskRef;
    }

    public int getTaskDetailDisplay() {
        return taskDetailDisplay;
    }

    public void setTaskDetailDisplay(int taskDetailDisplay) {
        this.taskDetailDisplay = taskDetailDisplay;
    }
}
