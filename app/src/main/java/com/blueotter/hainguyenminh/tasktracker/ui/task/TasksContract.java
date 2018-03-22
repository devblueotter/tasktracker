package com.blueotter.hainguyenminh.tasktracker.ui.task;

import android.support.annotation.NonNull;
import com.blueotter.hainguyenminh.tasktracker.base.BasePresenter;
import com.blueotter.hainguyenminh.tasktracker.base.BaseView;
import com.blueotter.hainguyenminh.tasktracker.data.local.db.Task;
import com.google.firebase.database.DatabaseReference;
import java.util.List;

/**
 * Created by HaiNM on 21/03/2018.
 */

public interface TasksContract {

    interface View extends BaseView<TasksContract.Presenter> {

        void showTasks(List<Task> tasks);

        void showAddTask();

        void showNoTasks();
    }

    interface Presenter extends BasePresenter {

        void loadTasks(DatabaseReference databaseReference);

        void addNewTask();

        void completeTask(@NonNull Task completedTask);

        void activateTask(@NonNull Task activeTask);
    }
}
