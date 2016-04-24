package com.yuraima.quest;

import android.util.Log;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;

/**
 * Created by yestevez on 4/4/16.
 */
public class Quest extends SugarRecord
    implements Serializable {
    public final static String TAG = "QuestClass";

    public String name;
    public String description;
    public boolean complete;

    @Ignore
    public int icon;

    /* CONSTRUCTORS */

    public Quest() {}

    Quest(String name, String description) {
        setName(name);
        setDescription(description);
        setComplete(false);
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

    public void setIcon() {
        if (this.complete) {
            icon = R.drawable.ic_done_black_24dp;
        } else {
            icon = R.drawable.ic_explore_black_24dp;
        }
    }

    public int getIcon() {
        return this.icon;
    }
}
