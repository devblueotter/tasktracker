package com.blueotter.hainguyenminh.tasktracker.data.remote.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.blueotter.hainguyenminh.tasktracker.base.DataCallback;
import com.blueotter.hainguyenminh.tasktracker.data.local.db.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by HaiNM on 21/03/2018.
 */

public class TasksRepository implements TasksDataSource {

    @Nullable
    private static TasksRepository instance = null;

    public static TasksRepository getInstance() {
        if (instance == null) {
            instance = new TasksRepository();
        }
        return instance;
    }

    @Override
    public void getTasks(DatabaseReference databaseReference, final DataCallback dataCallback) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tasks tasks = dataSnapshot.getValue(Tasks.class);
                dataCallback.onSuccess(tasks);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dataCallback.onError(databaseError.getCode());
            }
        });
    }

    @Override
    public void saveTask(@NonNull Task task) {

    }

    @Override
    public void completeTask(@NonNull Task task) {

    }

    @Override
    public void completeTask(@NonNull String taskId) {

    }

    @Override
    public void activateTask(@NonNull Task task) {

    }

    @Override
    public void activateTask(@NonNull String taskId) {

    }
}
