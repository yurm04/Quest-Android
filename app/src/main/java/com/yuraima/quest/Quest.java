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
 * Sugar ORM dependency used as parent object.  Sugar ORM
 * allows object to accomplish common CRUD and query operations
 * via an easy to use API.
 *
 * http://satyan.github.io/sugar/
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

    /**
     * Checks the completion status of the quest and returns
     * the id of a drawable resource according to "complete"
     * or "not complete".  Called by list view adapter to set
     * appropriate icon for list item image view
     * @return int id of drawable resource
     */
    public int getIcon() {
        if (this.complete) {
            Log.i(TAG, this.name + ": is complete");
            return R.drawable.ic_verified_user_black_24dp;
        } else {
            Log.i(TAG, this.name + ": not complete");
            return R.drawable.ic_explore_black_24dp;
        }
    }

    /**
     * Gets a list of associated tasks.  Optional argument of
     * the quest id can be passed in order to bypass some quirky
     * behavior with Sugar ORM and Serializable interface.
     * @param passedId long optional quest id
     * @return List of all tasks associated with quest
     */
    public List<Task> getTasks(@Nullable Long passedId) {
        String questId;
        if (passedId != null) {
            questId = String.valueOf(passedId);
        } else {
            questId = String.valueOf(this.getId());
        }
        return Task.find(Task.class, "quest = ?", questId);
    }

    /**
     * Gets the string associated with the count of tasks for quest.
     * Used for list view to stringify how many tasks a quest has or
     * if a quest is completed
     * @return String description of how many tasks in a quest
     */
    public String taskCount() {
        if (this.complete) {
            return "Completed";
        } else {
            int count = this.getTasks(null).size();
            return count + " Tasks";
        }

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
}
