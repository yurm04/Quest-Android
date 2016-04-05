package com.yuraima.quest;

import android.util.Log;

/**
 * Created by yestevez on 4/4/16.
 */
public class Quest {
    final static String TAG = "QuestClass";

    String name;
    String description;
    int id;


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
