package com.blueotter.hainguyenminh.tasktracker.ui.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.blueotter.hainguyenminh.tasktracker.R;
import com.blueotter.hainguyenminh.tasktracker.data.local.db.Task;
import com.blueotter.hainguyenminh.tasktracker.data.remote.repository.TasksRepository;
import com.blueotter.hainguyenminh.tasktracker.utils.DateUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by HaiNM on 21/03/2018.
 */

public class TasksPresenter implements TasksContract.Presenter {

    private TasksContract.View tasksView;

    public TasksPresenter(TasksContract.View tasksView) {
        this.tasksView = tasksView;
        this.tasksView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void loadTasks() {
        TasksRepository.getInstance(tasksView.getContext()).getTasks(new OnTaskEventListener() {
            @Override
            public void onTaskAdded(Task task) {
                tasksView.showTask(task);
            }

            @Override
            public void onTaskChanged(Task task) {
                tasksView.updateTask(task);
            }
        });
    }

    @Override
    public void addNewTask(Task task) {
        TasksRepository.getInstance(tasksView.getContext()).createTask(task);
    }

    @Override
    public void activateTask(@NonNull Task activeTask) {
        switch (activeTask.getTaskStatus()) {
            case Task.Status.CANCELLED:
                activeTask.setTaskStatus(Task.Status.IDLE);
                break;
            case Task.Status.DONE:
                activeTask.setTaskStatus(Task.Status.CANCELLED);
                break;
            case Task.Status.IDLE:
                activeTask.setTaskStatus(Task.Status.PROCESSING);
                break;
            case Task.Status.PROCESSING:
                activeTask.setTaskStatus(Task.Status.DONE);
                break;
        }
        TasksRepository.getInstance(tasksView.getContext()).saveTask(activeTask);
        tasksView.updateTask(activeTask);

    }

    @Override
    public void checkTaskAvailable() {
        TasksRepository.getInstance(tasksView.getContext()).checkTaskAvailable(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    tasksView.loadTasks();
                } else {
                    tasksView.showNoTasks();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public interface OnTaskEventListener {

        void onTaskAdded(Task task);

        void onTaskChanged(Task task);
    }
}
