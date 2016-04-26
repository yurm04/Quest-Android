package com.yuraima.quest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {
    String TAG = "AddQuestActivity";
    int CALENDAR_REQUEST = 1;

    String action;
    String itemType;
    String title;
    final String ADD_ACTION = "add";
    final String EDIT_ACTION = "edit";
    final String TASK_ITEM_TYPE = "task";
    final String QUEST_ITEM_TYPE = "quest";
    final String ADD_QUEST = "Add Quest";
    final String ADD_TASK = "Add Task";
    final String EDIT_QUEST = "Edit Quest";
    final String EDIT_TASK = "Edit Task";
    final int COMPLETE = 1;
    EditText nameField;
    EditText descField;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Sets MainQuestActivity as default when the UP button is hit */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Event Listener for "Add Quest" Button */
        Button addItemBtn = (Button) findViewById(R.id.itemActionBtn);
        if (addItemBtn != null) {
            addItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean result = addItemEventListener();
                    if (result) {
                        // return to main activity
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                }
            });
        }

        /* Create calendar event button handler */
        Button calendarBtn = (Button) findViewById(R.id.calendarBtn);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCalendarEvent(v);
            }
        });
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
        init();
    }

    protected void init() {
        getFields();
        setAction();
        setType();
        checkForEdit();
        setActivityTitle();
        setButton();
    }

    protected void setAction() {
        this.action = getIntent().getStringExtra("action");
    }

    protected void setType() {
        this.itemType = getIntent().getStringExtra("itemType");
    }

    protected void checkForEdit() {
        Log.i(TAG, "checkForEdit");
        Log.i(TAG, "checkForEdit: " + this.action);
        if (this.action.equals(EDIT_ACTION)) {
            Log.i(TAG, "checkForEdit True");
            setUpEdit();
        }
    }

    protected void setUpEdit() {
        Log.i(TAG, "setUpEdit");
        switch (this.itemType) {
            case QUEST_ITEM_TYPE:
                long questId = getIntent().getLongExtra("questId", 0);
                Quest quest = Quest.findById(Quest.class, questId);
                setUpEdit(quest.name, quest.description, quest.complete);
                break;
            case TASK_ITEM_TYPE:
                long taskId = getIntent().getLongExtra("taskId", 0);
                Task task = Task.findById(Task.class, taskId);
                setUpEdit(task.name, task.description, task.complete);
                break;
        }
    }

    protected void setUpEdit(String name, String description, boolean complete) {
        nameField.setText(name);
        descField.setText(description);

        int index;
        if (complete) {
            index = 1;
        } else {
            index = 0;
        }

        RadioButton selected = (RadioButton) radioGroup.getChildAt(index);
        selected.setChecked(true);
    }

    protected void setActivityTitle() {
        String title = "";

        switch (this.action) {
            case ADD_ACTION:
                title = "Add";
                break;
            case EDIT_ACTION:
                title = "Edit";
                break;
        }

        switch (this.itemType) {
            case TASK_ITEM_TYPE:
                title += " Task";
                break;
            case QUEST_ITEM_TYPE:
                title += " Quest";
                break;
        }

        this.title = title;
        getSupportActionBar().setTitle(this.title);
    }

    protected void setButton() {
        Button itemActionBtn = (Button) findViewById(R.id.itemActionBtn);
        itemActionBtn.setText(this.title);
    }

    /**
     * Creates a calendar component intent, allows user
     * to create an event and returns the event date
     */
    private boolean createCalendarEvent(View view) {
        // check for valid name and description
        String name = nameField.getText().toString();
        String desc = descField.getText().toString();

        if (!validateFields(name, desc)) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, name);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CALENDAR_REQUEST);
        }

        return true;
    }



    protected void getFields() {
        nameField = (EditText) findViewById(R.id.editName);
        descField = (EditText) findViewById(R.id.editDesc);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
    }

    /**
     * Event listener for button tap.  When button is tapped,
     * the values of each field is obtained.  Each value is validated,
     * if validation fails execution is terminated.  On validation pass,
     * the desired activity (ADD or EDIT) is evaluated along with the desired
     * item (QUEST or TASK).  The appropriate method is then called to add/edit
     * a quest/task based on the fields that were grabbed from the fields.
     * @return boolean pass or fail completion of add/edit action
     */
    private boolean addItemEventListener() {

        // check for valid name and description
        String name = nameField.getText().toString();
        String desc = descField.getText().toString();

        // End execution if invalid
        if (!validateFields(name, desc)) {
            Log.i(TAG, "FIELDS INVALID");
            return false;
        }
        boolean isComplete = getIsComplete();

        switch (this.title) {
            case ADD_QUEST:
                addQuest(name, desc, isComplete);
                break;
            case ADD_TASK:
                addTask(name, desc, isComplete);
                break;
            case EDIT_QUEST:
                editQuest(name, desc, isComplete);
                break;
            case EDIT_TASK:
                editTask(name, desc, isComplete);
                break;
        }

        return true;
    }

    private void editQuest(String name, String desc, boolean complete) {
        long questId = getIntent().getLongExtra("questId", 0);
        Quest quest = Quest.findById(Quest.class, questId);
        quest.name = name;
        quest.description = desc;
        quest.complete = complete;
        quest.save();
    }

    private void editTask(String name, String desc, boolean complete) {
        long taskId = getIntent().getLongExtra("taskId", 0);
        Task task = Task.findById(Task.class, taskId);
        task.name = name;
        task.description = desc;
        task.complete = complete;
        task.save();
    }

    private boolean getIsComplete() {
        int radioId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(radioId);
        int selected = radioGroup.indexOfChild(radioButton);

        if (selected == COMPLETE) {
            Log.i(TAG, "radio button is complete");
            return true;
        }

        return false;
    }

    private void addQuest(String name, String desc, boolean complete) {
        Quest newQuest = new Quest(name, desc, complete);
        newQuest.save();

        /* Log New Quest */
        Log.i(TAG, "New quest added - " + newQuest.toString());

        /* Log all Quests, including new one */
        List<Quest> allQuests = Quest.listAll(Quest.class);
        Log.i(TAG, allQuests.toString());

        /* Show toast to confirm new quest*/
        this.toast("Added Quest");
    }

    private void addTask(String name, String desc, boolean complete) {
        Quest quest = (Quest) getIntent().getSerializableExtra("quest");
        long questId = getIntent().getLongExtra("questId", 0);
        Log.i(TAG, "quest id: " + questId);
        Task newTask = new Task(name, desc, complete, questId);

        newTask.save();

        /* Log New Quest */
        Log.i(TAG, "New task added - " + newTask.toString());

        /* Show toast to confirm new task */
        this.toast("Added Task");
    }

    private void toast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Checks name and description fields for a value.  If empty,
     * show the appropriate errors.
     * @param name      Value of the name field passed
     * @param desc      Value of the description field passed
     *
     * @return isValid
     */
    private boolean validateFields(String name, String desc) {

        TextView nameWarning = (TextView) findViewById(R.id.nameWarning);
        TextView descWarning = (TextView) findViewById(R.id.descWarning);
        boolean valid = false;      // flag for validity

        // NAME WARNING VISIBILITY
        if (name.isEmpty()) {
            nameWarning.setVisibility(View.VISIBLE);
        } else {
            nameWarning.setVisibility(View.INVISIBLE);
            valid = true;
        }

        // DESCRIPTION WARNING VISIBILITY
        if (desc.isEmpty()) {
            descWarning.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            descWarning.setVisibility(View.INVISIBLE);
            valid = true;
        }

        return valid;
    }
}
