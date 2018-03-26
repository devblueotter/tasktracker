package com.blueotter.hainguyenminh.tasktracker.ui.task;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blueotter.hainguyenminh.tasktracker.R;
import com.blueotter.hainguyenminh.tasktracker.data.local.db.Task;
import com.blueotter.hainguyenminh.tasktracker.data.local.prefs.SharedPreferenceHelper;
import com.blueotter.hainguyenminh.tasktracker.ui.task.adapter.TaskLogsAdapter;
import com.blueotter.hainguyenminh.tasktracker.ui.task.adapter.TasksAdapter;
import com.blueotter.hainguyenminh.tasktracker.utils.DateUtils;
import com.facebook.AccessToken;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by hainguyen on 3/17/18.
 */

public class TasksActivity extends AppCompatActivity
        implements TasksContract.View, View.OnClickListener, TasksAdapter.OnTaskClicked {

    private TasksContract.Presenter tasksPresenter;
    private LinearLayout llTasks;
    private RelativeLayout rlEmptyTask;
    private Button btnAddNewTask;
    private RecyclerView rcvTasks;
    private TextView tvTaskLogs;
    private AccessToken accessToken;
    private ArrayList<Task> arrTasks;
    private TasksAdapter tasksAdapter;
    private int indexUpdate;
    //define for dialog create task
    private Dialog dlAddTask;
    private ImageView imvClose;
    private MaterialEditText edtTaskName;
    private MaterialEditText edtTaskDescription;
    private AppCompatButton btnAddTask;
    //define for dialog view task logs
    private Dialog dlTasksLog;
    private TextView tvTaskGroupByDate;
    private ImageView imvCloseTaskLogs;
    private RecyclerView rcvTaskLogs;
    private String newTaskName;
    private String newTaskDescription;
    private Random random;
    private TaskLogsAdapter taskLogsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        initView();
        initDefaultValue();
        setAction();
    }

    private void setAction() {
        btnAddNewTask.setOnClickListener(this);
        tvTaskLogs.setOnClickListener(this);
    }

    private void initDefaultValue() {
        random = new Random();
        arrTasks = new ArrayList<>();
        tasksAdapter = new TasksAdapter(arrTasks, this, this);
        rcvTasks.setLayoutManager(new LinearLayoutManager(this));
        rcvTasks.setAdapter(tasksAdapter);

        accessToken = SharedPreferenceHelper.getInstance(getApplicationContext())
                .get(SharedPreferenceHelper.PREF_KEY_FACEBOOK_ACCESS_TOKEN, AccessToken.class);
        tasksPresenter = new TasksPresenter(this);
        tasksPresenter.loadTasks();
        tasksPresenter.checkTaskAvailable();
    }

    private void initView() {
        llTasks = findViewById(R.id.llTasks);
        rlEmptyTask = findViewById(R.id.rlEmptyTask);
        btnAddNewTask = findViewById(R.id.btnAddNewTask);
        rcvTasks = findViewById(R.id.rcvTasks);
        tvTaskLogs = findViewById(R.id.tvTaskLogs);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddNewTask:
                showAddTaskDialog();
                break;
            case R.id.btnAddTask:
                if (!validateTask()) return;
                addNewTask();
                dlAddTask.dismiss();
                break;
            case R.id.imvClose:
                dlAddTask.dismiss();
                break;
            case R.id.tvTaskLogs:
                showTaskLogsDialog();
                break;
            case R.id.imvCloseTaskLogs:
                dlTasksLog.dismiss();
                break;
        }
    }

    private void addNewTask() {
        String taskRef = System.currentTimeMillis() + "";
        Task task = new Task(Task.Status.IDLE, DateUtils.formatDateTime(DateUtils.DATE_FORMAT_MDY), newTaskName,
                newTaskDescription, (random.nextInt(30) + 1), taskRef);
        tasksPresenter.addNewTask(task);
    }

    private boolean validateTask() {
        newTaskName = edtTaskName.getText().toString().trim();
        newTaskDescription = edtTaskDescription.getText().toString();
        if (TextUtils.isEmpty(newTaskName)) {
            edtTaskName.setError("Please input Task Name");
            return false;
        } else if (TextUtils.isEmpty(newTaskDescription)) {
            edtTaskDescription.setError("Please input Task Description");
            return false;
        }
        return true;
    }

    @Override
    public void loadTasks() {
        tasksPresenter.loadTasks();
    }

    @Override
    public void showTask(Task task) {
        llTasks.setVisibility(View.VISIBLE);
        rlEmptyTask.setVisibility(View.GONE);
        arrTasks.add(task);
        tasksAdapter.updateData(arrTasks);
    }

    @Override
    public void showNoTasks() {
        llTasks.setVisibility(View.GONE);
        rlEmptyTask.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateTask(Task task) {
        arrTasks.set(indexUpdate, task);
        tasksAdapter.updateData(arrTasks);
    }

    @Override
    public void setPresenter(TasksContract.Presenter presenter) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onTaskNameClicked(Task task) {
        indexUpdate = arrTasks.indexOf(task);
        tasksPresenter.activateTask(task);
    }

    @Override
    public void onTaskDetailClicked(Task task) {
        indexUpdate = arrTasks.indexOf(task);
        switch (task.getTaskDetailDisplay()) {
            case Task.TaskDetailDisplay.ACTION:
                task.setTaskDetailDisplay(Task.TaskDetailDisplay.DESCRIPTION);
                break;
            case Task.TaskDetailDisplay.DESCRIPTION:
                task.setTaskDetailDisplay(Task.TaskDetailDisplay.ACTION);
                break;
        }
        arrTasks.set(indexUpdate, task);
        tasksAdapter.updateData(arrTasks);
    }

    private void showAddTaskDialog() {
        if (dlAddTask == null) {
            dlAddTask = new Dialog(this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
            dlAddTask.setContentView(R.layout.dialog_add_new_task);

            imvClose = dlAddTask.findViewById(R.id.imvClose);
            edtTaskName = dlAddTask.findViewById(R.id.edtTaskName);
            edtTaskDescription = dlAddTask.findViewById(R.id.edtTaskDescription);
            btnAddTask = dlAddTask.findViewById(R.id.btnAddTask);
            btnAddTask.setOnClickListener(this);
            imvClose.setOnClickListener(this);
        }
        edtTaskName.setText("");
        edtTaskDescription.setText("");
        dlAddTask.show();
    }

    private void showTaskLogsDialog() {
        if (dlTasksLog == null) {
            dlTasksLog = new Dialog(this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
            dlTasksLog.setContentView(R.layout.dialog_task_logs);

            tvTaskGroupByDate = dlTasksLog.findViewById(R.id.tvTaskGroupByDate);
            imvCloseTaskLogs = dlTasksLog.findViewById(R.id.imvCloseTaskLogs);
            rcvTaskLogs = dlTasksLog.findViewById(R.id.rcvTaskLogs);

            imvCloseTaskLogs.setOnClickListener(this);
            taskLogsAdapter = new TaskLogsAdapter(arrTasks);
            rcvTaskLogs.setLayoutManager(new LinearLayoutManager(this));
            rcvTaskLogs.setAdapter(taskLogsAdapter);
        }
        Date dateCreateTask = DateUtils.formatDateWithFormat(arrTasks.get(0).getCreateDate(), DateUtils.DATE_FORMAT_MDY);
        tvTaskGroupByDate.setText(DateUtils.formatDateTime(DateUtils.DATE_FORMAT_MMM_DD_YYYY, dateCreateTask));
        taskLogsAdapter.updateData(arrTasks);
        dlTasksLog.show();
    }
}
