package com.blueotter.hainguyenminh.tasktracker.ui.task;

import android.support.annotation.NonNull;
import com.blueotter.hainguyenminh.tasktracker.data.local.db.Task;
import com.blueotter.hainguyenminh.tasktracker.data.remote.repository.TasksRepository;
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
    public void loadTasks(DatabaseReference databaseReference) {

        TasksRepository.getInstance().getTasks(databaseReference, new OnTaskEventListener() {
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
    public void addNewTask() {

    }

    @Override
    public void completeTask(@NonNull Task completedTask) {

    }

    @Override
    public void activateTask(@NonNull Task activeTask) {

    }

    @Override
    public void checkTaskAvailable(DatabaseReference databaseReference) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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
