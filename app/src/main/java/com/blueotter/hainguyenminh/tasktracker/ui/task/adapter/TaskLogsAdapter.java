package com.blueotter.hainguyenminh.tasktracker.ui.task.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blueotter.hainguyenminh.tasktracker.R;
import com.blueotter.hainguyenminh.tasktracker.data.local.db.Task;

import java.util.ArrayList;

/**
 * Created by HaiNM on 22/03/2018.
 */

public class TaskLogsAdapter extends RecyclerView.Adapter {

    private ArrayList<Task> arrTask;

    public TaskLogsAdapter(ArrayList<Task> arrTask) {
        this.arrTask = arrTask;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task_log, parent, false);
        return new TaskLogsItemVH(viewHolder);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TaskLogsItemVH) holder).setTaskData(position);
    }

    @Override
    public int getItemCount() {
        return arrTask.size();
    }

    public void updateData(ArrayList<Task> arrTask) {
        this.arrTask = arrTask;
        notifyDataSetChanged();
    }

    public class TaskLogsItemVH extends RecyclerView.ViewHolder {

        private TextView tvTaskName;
        private TextView tvTaskAction;
        private TextView tvTaskCreateDate;
        private TextView tvTaskCreateTime;

        public TaskLogsItemVH(View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            tvTaskAction = itemView.findViewById(R.id.tvTaskAction);
            tvTaskCreateDate = itemView.findViewById(R.id.tvTaskCreateDate);
            tvTaskCreateTime = itemView.findViewById(R.id.tvTaskCreateTime);
        }

        public void setTaskData(int position) {
            Task task = arrTask.get(position);
            tvTaskName.setText(task.getTaskName());
            tvTaskAction.setText(task.getTaskAction() + "");

            String taskCreateDate = task.getCreateDate();
            String[] taskCreateDateTime = taskCreateDate.split(", ");

            tvTaskCreateDate.setText(taskCreateDateTime[0]);
            tvTaskCreateTime.setText(taskCreateDateTime[1]);
        }
    }
}
