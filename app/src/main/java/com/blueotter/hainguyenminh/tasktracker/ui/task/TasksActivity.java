package com.blueotter.hainguyenminh.tasktracker.ui.task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.blueotter.hainguyenminh.tasktracker.R;
import com.blueotter.hainguyenminh.tasktracker.data.local.db.Task;
import com.blueotter.hainguyenminh.tasktracker.data.local.prefs.SharedPreferenceHelper;
import com.blueotter.hainguyenminh.tasktracker.ui.login.LoginContract;
import com.facebook.AccessToken;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

/**
 * Created by hainguyen on 3/17/18.
 */

public class TasksActivity extends AppCompatActivity
        implements TasksContract.View, View.OnClickListener {

    private TasksPresenter tasksPresenter;
    private Button btnAddNewTask;
    private RecyclerView rcvTasks;
    private DatabaseReference databaseReference;
    private AccessToken accessToken;
    private Task task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        btnAddNewTask = findViewById(R.id.btnAddNewTask);
        rcvTasks = findViewById(R.id.rcvTasks);

        btnAddNewTask.setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        accessToken = SharedPreferenceHelper.getInstance(getApplicationContext())
                .get(SharedPreferenceHelper.PREF_KEY_FACEBOOK_ACCESS_TOKEN, AccessToken.class);
        databaseReference.child(accessToken.getUserId());
        tasksPresenter = new TasksPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddNewTask:
                databaseReference.child(accessToken.getUserId())
                        .setValue(new Task("name", "description"));
                break;
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    @Override
    public void showTasks(List<Task> tasks) {

    }

    @Override
    public void showAddTask() {

    }

    @Override
    public void showNoTasks() {

    }
}
