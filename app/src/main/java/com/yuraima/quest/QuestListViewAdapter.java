package com.yuraima.quest;

import android.app.Activity;
import android.content.Context;
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

    Context context;

    public QuestListViewAdapter(Context context, int resource, List<Quest> objects) {
        super(context, resource, objects);

        this.context = context;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView mainText;
        TextView subText;
    }

    public View getView(int position, View convertView, ViewGroup Parent) {
        ViewHolder holder = null;
        Quest quest = getItem(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView != null) {
            convertView = inflater.inflate(R.layout.quest_list_item, null);
            holder = new ViewHolder();
            holder.mainText = (TextView) convertView.findViewById(R.id.mainText);
            holder.subText = (TextView) convertView.findViewById(R.id.subText);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mainText.setText(quest.name);
        holder.subText.setText(quest.description);
        holder.imageView.setImageResource(quest.getIcon());

        return convertView;
    }
}
