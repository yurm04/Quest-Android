package com.yuraima.quest;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yestevez on 4/23/16.
 */
public class QuestListViewAdapter extends ArrayAdapter<Quest> {

    final static String TAG = "QuestListViewAdapter";
    Context context;
    int resource;
    List<Quest> quests;

    public QuestListViewAdapter(Context context, int resource, List<Quest> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.quests = objects;
    }

    private class QuestViewHolder {
        ImageView imageView;
        TextView mainText;
        TextView taskCount;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        QuestViewHolder holder = null;
        Quest quest = getItem(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(this.resource, parent, false);
            holder = new QuestViewHolder();
            holder.mainText = (TextView) convertView.findViewById(R.id.mainText);
            holder.taskCount = (TextView) convertView.findViewById(R.id.taskCount);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else {
            holder = (QuestViewHolder) convertView.getTag();
        }

        holder.mainText.setText(quest.name);
        holder.taskCount.setText(quest.taskCount());
        holder.imageView.setImageResource(quest.getIcon());

        if (quest.isComplete()) {
            holder.mainText.setTextColor(ContextCompat.getColor(context, R.color.colorComplete));
        }

        return convertView;
    }
}
