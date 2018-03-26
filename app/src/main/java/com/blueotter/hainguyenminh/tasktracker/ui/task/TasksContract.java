package com.blueotter.hainguyenminh.tasktracker.ui.task;

import android.support.annotation.NonNull;
import com.blueotter.hainguyenminh.tasktracker.base.BasePresenter;
import com.blueotter.hainguyenminh.tasktracker.base.BaseView;
import com.blueotter.hainguyenminh.tasktracker.data.local.db.Task;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by HaiNM on 21/03/2018.
 */

public interface TasksContract {

    interface View extends BaseView<TasksContract.Presenter> {

        void loadTasks();

        void showTask(Task task);

        void showAddTask();

        void showNoTasks();

        void updateTask(Task task);
    }

    interface Presenter extends BasePresenter {

        void loadTasks(DatabaseReference databaseReference);

        void addNewTask();

        void completeTask(@NonNull Task completedTask);

        void activateTask(@NonNull Task activeTask);

        void checkTaskAvailable(DatabaseReference databaseReference);
    }
}
