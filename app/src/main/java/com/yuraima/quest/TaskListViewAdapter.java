package com.yuraima.quest;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yestevez on 4/24/16.
 */
public class TaskListViewAdapter extends ArrayAdapter<Task> {
    final static String TAG = "TaskListViewAdapter";
    Context context;
    int resource;
    List<Task> quests;

    public TaskListViewAdapter(Context context, int resource, List<Task> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.quests = objects;
    }

    private class TaskViewHolder {
        ImageView imageView;
        TextView mainText;
        TextView taskStatus;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TaskViewHolder holder = null;
        Task task = getItem(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(this.resource, parent, false);
            holder = new TaskViewHolder();
            holder.mainText = (TextView) convertView.findViewById(R.id.task_main_text);
            holder.taskStatus = (TextView) convertView.findViewById(R.id.task_status);
            holder.imageView = (ImageView) convertView.findViewById(R.id.task_icon);
            convertView.setTag(holder);
        } else {
            holder = (TaskViewHolder) convertView.getTag();
        }

        holder.mainText.setText(task.name);
        holder.taskStatus.setText(task.getStatus());
        holder.imageView.setImageResource(task.getIcon());

        if (task.isComplete()) {
            holder.mainText.setTextColor(ContextCompat.getColor(context, R.color.colorComplete));
        }

        return convertView;
    }
}
