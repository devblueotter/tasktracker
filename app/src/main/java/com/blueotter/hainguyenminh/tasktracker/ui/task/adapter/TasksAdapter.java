package com.blueotter.hainguyenminh.tasktracker.ui.task.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.blueotter.hainguyenminh.tasktracker.data.local.db.Task;
import java.util.ArrayList;

/**
 * Created by HaiNM on 22/03/2018.
 */

public class TasksAdapter extends RecyclerView.Adapter {

    private ArrayList<Task> arrTask;

    public TasksAdapter(ArrayList<Task> arrTask) {
        this.arrTask = arrTask;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return arrTask.size();
    }
}
