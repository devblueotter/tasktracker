package com.blueotter.hainguyenminh.tasktracker.data.remote.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.blueotter.hainguyenminh.tasktracker.base.DataCallback;
import com.blueotter.hainguyenminh.tasktracker.data.local.db.Task;
import com.blueotter.hainguyenminh.tasktracker.data.local.prefs.SharedPreferenceHelper;
import com.blueotter.hainguyenminh.tasktracker.ui.task.TasksPresenter;
import com.facebook.AccessToken;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by HaiNM on 21/03/2018.
 */

public class TasksRepository implements TasksDataSource {

    @Nullable
    private static TasksRepository instance = null;

    private DatabaseReference databaseReference;
    private AccessToken accessToken;
    private Context context;

    public static TasksRepository getInstance(Context context) {
        if (instance == null) {
            instance = new TasksRepository(context);
        }
        return instance;
    }

    private TasksRepository(Context context) {
        accessToken = SharedPreferenceHelper.getInstance(context)
                .get(SharedPreferenceHelper.PREF_KEY_FACEBOOK_ACCESS_TOKEN, AccessToken.class);
        databaseReference = FirebaseDatabase.getInstance().getReference(accessToken.getUserId());
    }

    @Override
    public void getTasks(final DataCallback dataCallback) {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                dataCallback.onSuccess(task);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                dataCallback.onSuccess(task);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void createTask(@NonNull Task task) {
        databaseReference.child(task.getTaskRef())
                .setValue(task);
    }

    @Override
    public void saveTask(@NonNull Task task) {
        databaseReference.getDatabase()
                .getReference(accessToken.getUserId())
                .child(task.getTaskRef())
                .setValue(task);
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

    @Override
    public void getTasks(final TasksPresenter.OnTaskEventListener onTaskEventListener) {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                onTaskEventListener.onTaskAdded(task);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                onTaskEventListener.onTaskChanged(task);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void checkTaskAvailable(ValueEventListener valueEventListener) {
        databaseReference.addListenerForSingleValueEvent(valueEventListener);

    }
}
