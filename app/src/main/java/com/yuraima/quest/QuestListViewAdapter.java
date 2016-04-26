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

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by yestevez on 4/23/16.
 * Was able to adapt this class from several resources.
 * Explanation of process in comments below:
 * https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 * http://www.ezzylearning.com/tutorial/customizing-android-listview-items-with-custom-arrayadapter
 * http://theopentutorials.com/tutorials/android/listview/android-custom-listview-with-image-and-text-using-arrayadapter/
 */
public class QuestListViewAdapter extends ArrayAdapter<Quest> {

    final static String TAG = "QuestListViewAdapter";
    Context context;
    int resource;
    List<Quest> quests;

    /* Default constructor */
    public QuestListViewAdapter(Context context, int resource, List<Quest> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.quests = objects;
    }

    /* Custom Class for all relevant UI elements*/
    private class QuestViewHolder {
        ImageView imageView;
        TextView mainText;
        TextView descText;
        TextView taskCount;
    }

    /**
     * Method called to generate each row of the listview.
     * Custom row views are defined in the quest_list_item.xml
     * layout file
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        QuestViewHolder holder = null;
        Quest quest = getItem(position);  // get the quest at the current position

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(this.resource, parent, false);
            holder = new QuestViewHolder();
            /* Get all of the UI views that are included in layout file */
            holder.mainText = (TextView) convertView.findViewById(R.id.task_main_text);
            holder.descText = (TextView) convertView.findViewById(R.id.task_desc_text);
            holder.taskCount = (TextView) convertView.findViewById(R.id.taskCount);
            holder.imageView = (ImageView) convertView.findViewById(R.id.task_icon);
            convertView.setTag(holder);
        } else {
            holder = (QuestViewHolder) convertView.getTag();
        }

        // Set the values for each UI view
        holder.mainText.setText(quest.name);
        holder.descText.setText(quest.description);
        holder.taskCount.setText(quest.taskCount());
        holder.imageView.setImageResource(quest.getIcon());

        // If the quest is complete, set the title color to green as a hint
        if (quest.isComplete()) {
            holder.mainText.setTextColor(ContextCompat.getColor(context, R.color.colorComplete));
        }

        // return row view
        return convertView;
    }
}
