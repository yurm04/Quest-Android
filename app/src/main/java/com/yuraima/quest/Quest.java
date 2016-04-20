package com.yuraima.quest;

import android.util.Log;
import com.orm.SugarRecord;

/**
 * Created by yestevez on 4/4/16.
 */
public class Quest extends SugarRecord {
    public final static String TAG = "QuestClass";

    public String name;
    public String description;

    /* CONSTRUCTOR */
    public Quest() {}

    Quest(String name, String description) {
        setName(name);
        setDescription(description);
    }

    @Override
    public String toString() {
        return name + ": " + description;
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
}
