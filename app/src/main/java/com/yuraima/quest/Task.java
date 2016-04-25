package com.yuraima.quest;

import com.orm.SugarRecord;

/**
 * Created by yestevez on 4/19/16.
 */
public class Task extends SugarRecord {
    public long quest;     // the quest that this task belongs to
    public String name;
    public String description;
    public boolean complete;
    public String date_completed;

    /* CONSTRUCTORS */

    public Task() {}

    public Task(String name, String description, boolean complete, long quest) {
        this.name = name;
        this.description = description;
        this.complete = complete;
        this.quest = quest;
    }

    public String getStatus() {
        if (!complete) {
            return "In progress";
        } else {
            return "Completed";
        }
    }

    public boolean isComplete() {
        return this.complete;
    }

    public int getIcon() {
        if (complete) {
            return R.drawable.ic_done_black_24dp;
        } else {
            return R.drawable.ic_explore_black_24dp;
        }
    }
    public String toString() {
        return this.name + ": " + this.description;
    }

    /* GETTERS AND SETTERS */

    public void setName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
