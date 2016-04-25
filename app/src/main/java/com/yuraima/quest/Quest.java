package com.yuraima.quest;

import android.support.annotation.Nullable;
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

    Quest(String name, String description, boolean complete) {
        this.name = name;
        this.description = description;
        this.complete = complete;
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
            Log.i(TAG, this.name + ": is complete");
            return R.drawable.ic_verified_user_black_24dp;
        } else {
            Log.i(TAG, this.name + ": not complete");
            return R.drawable.ic_explore_black_24dp;
        }
    }

    public List<Task> getTasks(@Nullable Long passedId) {
        String questId;
        if (passedId != null) {
            questId = String.valueOf(passedId);
        } else {
            questId = String.valueOf(this.getId());
        }
        return Task.find(Task.class, "quest = ?", questId);
    }

    public String taskCount() {
        if (this.complete) {
            return "Completed";
        } else {
            int count = this.getTasks(null).size();
            return count + " Tasks";
        }

    }
}
