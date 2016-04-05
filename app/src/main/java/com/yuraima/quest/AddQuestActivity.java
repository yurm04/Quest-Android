package com.yuraima.quest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import android.widget.Toast;

public class AddQuestActivity extends AppCompatActivity {
    final static String TAG = "AddQuestActivity";
    QuestDBHelper qdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Sets MainQuestActivity as default when the UP button is hit */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.qdb = new QuestDBHelper(getApplicationContext());
        Log.i(TAG, "Created DB helper");

        /* Event Listener*/
        Button addQuestBtn = (Button) findViewById(R.id.addQuestBtn);
        addQuestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestEventListener();
            }
        });
    }


    private void addQuestEventListener() {
        EditText nameField = (EditText) findViewById(R.id.editQuestName);
        EditText descField = (EditText) findViewById(R.id.editQuestDesc);

        String name = nameField.getText().toString();
        String desc = descField.getText().toString();

        boolean result = this.qdb.addQuest(name, desc);

        if (result == true) {
            Context context = getApplicationContext();
            CharSequence text = "Added Quest";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
