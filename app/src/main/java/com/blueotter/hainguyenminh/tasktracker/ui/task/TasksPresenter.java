package com.blueotter.hainguyenminh.tasktracker.ui.task;

import android.support.annotation.NonNull;
import com.blueotter.hainguyenminh.tasktracker.base.DataCallback;
import com.blueotter.hainguyenminh.tasktracker.data.local.db.Task;
import com.blueotter.hainguyenminh.tasktracker.data.remote.repository.TasksRepository;
import com.google.firebase.database.DatabaseReference;
import java.util.List;

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
    public void loadTasks(DatabaseReference databaseReference) {

        TasksRepository.getInstance().getTasks(databaseReference, new DataCallback<List<Task>>() {

            @Override
            public void onSuccess(List<Task> data) {
                tasksView.showTasks(data);
            }

            @Override
            public void onError(int errorCode) {
                tasksView.showNoTasks();
            }
        });
    }

    @Override
    public void addNewTask() {

    }

    @Override
    public void completeTask(@NonNull Task completedTask) {

    }

    @Override
    public void activateTask(@NonNull Task activeTask) {

    }
}
