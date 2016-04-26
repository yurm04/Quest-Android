package com.yuraima.quest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    final static String TAG = "TaskListActivity";
    TextView noTasksMsg;
    TextView noTasksSubMsg;
    TextView questName;
    TextView questDescSub;
    long questId;
    ListView taskListView;
    TaskListViewAdapter taskListAdapter;
    Quest quest;
    int REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

        Log.i(TAG, "onCreate");
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
        init();
    }

    protected void init() {
        getQuest();
        getMessages();
        getTaskList();
    }

    protected void getMessages() {
        if (noTasksMsg == null || noTasksSubMsg == null) {
            noTasksMsg = (TextView) findViewById(R.id.emptyTasksMsg);
            noTasksSubMsg = (TextView) findViewById(R.id.noTasksSubMsg);
        }
    }

    protected void setQuestName(String name, String desc) {
        questName = (TextView) findViewById(R.id.questName);
        questDescSub = (TextView) findViewById(R.id.questDescSub);
        questName.setText(name);
        questDescSub.setText(desc);
    }
    protected boolean getTaskList() {
        getQuest();
        List<Task> allTasks = quest.getTasks(this.questId);
        
        if (allTasks.size() == 0) {
            showEmptyListMsg();
            return false;
        } else {
            hideEmptyListMsg();
        }

        taskListView = (ListView) findViewById(R.id.task_listView);
        taskListAdapter = new TaskListViewAdapter(this, R.layout.task_list_item, allTasks);
        taskListView.setAdapter(taskListAdapter);
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editTask(position);
            }
        });

        taskListView.setLongClickable(true);
        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                setTaskStatus(position);
                return true;
            }
        });

        return true;
    }

    public void setTaskStatus(int position) {
        Task task = taskListAdapter.getItem(position);
        task.complete = !task.complete;
        task.save();
        init();
        if (task.complete) {
            toast("Task Completed!");
        }
    }

    protected void showEmptyListMsg() {
        if (noTasksMsg != null && noTasksSubMsg != null) {
            noTasksMsg.setVisibility(View.VISIBLE);
            noTasksSubMsg.setVisibility(View.VISIBLE);
        }
    }

    protected void hideEmptyListMsg() {
        if (noTasksMsg.getVisibility() == View.VISIBLE ||
            noTasksSubMsg.getVisibility() == View.VISIBLE) {
            noTasksMsg.setVisibility(View.INVISIBLE);
            noTasksSubMsg.setVisibility(View.INVISIBLE);
        }
    }

    protected void getQuest() {
        this.quest = (Quest) getIntent().getSerializableExtra("quest");
        this.questId = getIntent().getLongExtra("questId", 0);
        this.quest = Quest.findById(Quest.class, questId);
        Log.i(TAG, "quest name: " + quest.name);
        setQuestName(quest.name, quest.description);
    }

    protected void addTask() {
        Intent addItemIntent = new Intent(this, AddItemActivity.class);
        addItemIntent.putExtra("quest", quest);
        addItemIntent.putExtra("action", "add");
        addItemIntent.putExtra("itemType", "task");
        addItemIntent.putExtra("questId", this.questId);
        startActivityForResult(addItemIntent, REQUEST_CODE);
    }

    protected void editQuest() {
        Log.i(TAG, "clicked edit quest");
        final Intent editIntent = new Intent(this, AddItemActivity.class);
        editIntent.putExtra("action", "edit");
        editIntent.putExtra("itemType", "quest");
        editIntent.putExtra("questId", this.questId);
        startActivityForResult(editIntent, REQUEST_CODE);
    }

    protected void editTask(int position) {
        Log.i(TAG, "editTask");
        final Intent editTaskIntent = new Intent(this, AddItemActivity.class);
        editTaskIntent.putExtra("action", "edit");
        editTaskIntent.putExtra("itemType", "task");
        long taskId = taskListAdapter.getItem(position).getId();
        editTaskIntent.putExtra("taskId", taskId);
        startActivityForResult(editTaskIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                init();
            } else {
                Log.i(TAG, "no result to show");
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        menu.add(0, menu.FIRST, 0, "Edit Quest");
        getMenuInflater().inflate(R.menu.menu_task_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Log.i(TAG, "edit menu hit");
            editQuest();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
