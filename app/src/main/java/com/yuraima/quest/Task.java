package com.yuraima.quest;

import com.orm.SugarRecord;

/**
 * Created by yestevez on 4/19/16.
 */
public class Task extends SugarRecord {
    public Quest quest;     // the quest that this task belongs to
    public String name;
    public String description;
    public String date_completed;

    /* CONSTRUCTORS */

    public Task() {}

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
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
