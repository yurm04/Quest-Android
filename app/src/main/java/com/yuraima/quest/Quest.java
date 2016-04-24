package com.yuraima.quest;

import android.util.Log;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yestevez on 4/4/16.
 */
public class Quest extends SugarRecord
    implements Serializable {
    public final static String TAG = "QuestClass";

    public String name;
    public String description;
    public boolean complete;


    /* CONSTRUCTORS */

    public Quest() {}

    Quest(String name, String description) {
        this.name = name;
        this.description = description;
        this.complete = false;
    }

    @Override
    public String toString() {
        return name;
    }

    /* GETTERS AND SETTERS */

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        Log.i(TAG, "setting name " + name);
        this.name = name;
    }

    public String getDescription() {
        Log.i(TAG, "getting description " + name);
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public int getIcon() {
        if (this.complete) {
            return R.drawable.ic_verified_user_black_24dp;
        } else {
            return R.drawable.ic_explore_black_24dp;
        }
    }

    public List<Task> getTasks() {
        String questId = String.valueOf(this.getId());
        return Task.find(Task.class, "quest = ?", questId);
    }

    public String taskCount() {
        if (this.complete) {
            return "Completed";
        } else {
            int count = this.getTasks().size();
            return count + " Tasks";
        }

    }
}
