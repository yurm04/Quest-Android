package com.yuraima.quest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {
    final static String TAG = "TaskListActivity";
    ArrayAdapter<String> adapter;
    ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO MAKE THIS ADD A NEW TASK TO THIS QUEST
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

        /* Get all tasks in this list */
        getTaskList();
    }

    public void getTaskList() {
        ArrayList<Task> allTasks = (ArrayList<Task>) Task.listAll(Task.class);
        ArrayList<String> taskNames = new ArrayList<>();

        /* Loop through all tasks and add them to the taskNames list */
        for ( Task task : allTasks ) {
            taskNames.add(task.name);
        }

        ListView taskListView = (ListView) findViewById(R.id.taskListView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskNames);
        taskListView.setAdapter(adapter);
    }

    public void addTask() {

    }

}
