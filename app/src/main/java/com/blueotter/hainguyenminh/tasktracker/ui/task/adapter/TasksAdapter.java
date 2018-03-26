package com.blueotter.hainguyenminh.tasktracker.ui.task.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blueotter.hainguyenminh.tasktracker.R;
import com.blueotter.hainguyenminh.tasktracker.data.local.db.Task;

import java.util.ArrayList;

/**
 * Created by HaiNM on 22/03/2018.
 */

public class TasksAdapter extends RecyclerView.Adapter {

    private ArrayList<Task> arrTask;
    private OnTaskClicked onTaskClicked;
    private Context context;

    public TasksAdapter(ArrayList<Task> arrTask, Context context,
            OnTaskClicked onItemClickListener) {
        this.arrTask = arrTask;
        this.context = context;
        this.onTaskClicked = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskItemVH(viewHolder);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TaskItemVH) holder).setTaskData(position);
    }

    @Override
    public int getItemCount() {
        return arrTask.size();
    }

    public void updateData(ArrayList<Task> arrTask) {
        this.arrTask = arrTask;
        notifyDataSetChanged();
    }

    public class TaskItemVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatButton btnTaskName;
        private AppCompatButton btnTaskDetail;

        public TaskItemVH(View itemView) {
            super(itemView);
            btnTaskName = itemView.findViewById(R.id.btnTaskName);
            btnTaskDetail = itemView.findViewById(R.id.btnTaskDetail);
            btnTaskName.setSelected(true);
            btnTaskDetail.setSelected(true);
            btnTaskName.setOnClickListener(this);
            btnTaskDetail.setOnClickListener(this);
        }

        @SuppressLint("RestrictedApi")
        public void setTaskData(int position) {
            Task task = arrTask.get(position);
            btnTaskName.setText(task.getTaskName());
            btnTaskDetail.setText(task.getTaskDescription());
            btnTaskName.setTag(position);
            btnTaskDetail.setTag(position);
            switch (task.getTaskStatus()) {
                case Task.Status.CANCELLED:
                    btnTaskName.setSupportBackgroundTintList(
                            ContextCompat.getColorStateList(context,
                                    R.color.com_fb_button_background_color_focused_disabled));
                    break;
                case Task.Status.DONE:
                    btnTaskName.setSupportBackgroundTintList(
                            ContextCompat.getColorStateList(context, R.color.colorAccent));
                    break;
                case Task.Status.IDLE:
                    btnTaskName.setSupportBackgroundTintList(
                            ContextCompat.getColorStateList(context, R.color.task_status_idle));
                    break;
                case Task.Status.PROCESSING:
                    btnTaskName.setSupportBackgroundTintList(
                            ContextCompat.getColorStateList(context,
                                    R.color.task_status_processing));
                    break;
            }

            switch (task.getTaskDetailDisplay()) {
                case Task.TaskDetailDisplay.DESCRIPTION:
                    btnTaskDetail.setText(task.getTaskDescription());
                    break;
                case Task.TaskDetailDisplay.ACTION:
                    btnTaskDetail.setText(task.getTaskAction() + "");
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            switch (v.getId()) {
                case R.id.btnTaskName:
                    onTaskClicked.onTaskNameClicked(arrTask.get(position));
                    break;
                case R.id.btnTaskDetail:
                    onTaskClicked.onTaskDetailClicked(arrTask.get(position));
                    break;
            }
        }
    }

    public interface OnTaskClicked {

        void onTaskNameClicked(Task task);

        void onTaskDetailClicked(Task task);
    }
}
