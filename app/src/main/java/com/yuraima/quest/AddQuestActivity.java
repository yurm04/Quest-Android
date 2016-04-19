package com.yuraima.quest;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddQuestActivity extends AppCompatActivity {
    final static String TAG = "AddQuestActivity";
    final static int CALENDAR_REQUEST = 1;
//    QuestDBHelper qdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Sets MainQuestActivity as default when the UP button is hit */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Create DB helper for creating new quests */
//        this.qdb = new QuestDBHelper(getApplicationContext());
        Log.i(TAG, "Created DB helper");

        /* Event Listener for "Add Quest" Button */
        Button addQuestBtn = (Button) findViewById(R.id.addQuestBtn);
        if (addQuestBtn != null) {
            addQuestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean result = addQuestEventListener();
                    if (result) {
                        // return to main activity
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
    }

    /**
     * Creates a calendar component intent, allows user
     * to create an event and returns the event date
     */
    private boolean createCalendarEvent(View view) {
        EditText nameField = (EditText) findViewById(R.id.editQuestName);
        EditText descField = (EditText) findViewById(R.id.editQuestDesc);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CALENDAR_REQUEST) {
            if (resultCode == RESULT_OK) {  // request successful
                Uri calendarUri = data.getData();  // location of data
                String[] projection = {CalendarContract.EventDays.STARTDAY};

                Cursor cursor = getContentResolver().query(calendarUri, projection, null, null, null);
                cursor.moveToFirst();
                // Retrieve phone number from the NUMBER column
                int column = cursor.getColumnIndex(CalendarContract.EventDays.STARTDAY);
                String eventDate = cursor.getString(column);

                Context context = getApplicationContext();
                CharSequence text = "Quest Date: " + eventDate;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }

    /**
     * Event Listener to "Add Quest" button.
     * Uses DB helper to add quest to DB.
     * Creates toast to confirm added quest.
     *
     * @return success or failure to add new quest
     */
    private boolean addQuestEventListener() {
        EditText nameField = (EditText) findViewById(R.id.editQuestName);
        EditText descField = (EditText) findViewById(R.id.editQuestDesc);

        // check for valid name and description
        String name = nameField.getText().toString();
        String desc = descField.getText().toString();

        // End execution if invalid
        if (!validateFields(name, desc)) {
            Log.i(TAG, "FIELDS INVALID");
            return false;
        }

        Quest newQuest = new Quest(name, desc);
        newQuest.save();

        /* Log New Quest */
        Log.i(TAG, "New quest added - " + newQuest.toString());

        /* Log all Quests, including new one */
        List<Quest> allQuests = Quest.listAll(Quest.class);
        Log.i(TAG, allQuests.toString());

        /* Show toast to confirm new quest*/
        Context context = getApplicationContext();
        CharSequence text = "Added Quest";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        return true;
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

        TextView nameWarning = (TextView) findViewById(R.id.questNameWarning);
        TextView descWarning = (TextView) findViewById(R.id.questDescWarning);
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
