package com.yuraima.quest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainQuestActivity extends AppCompatActivity {

    final static String TAG = "MainQuestActivity";
    final static int ADD_QUEST_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask(view);
            }
        });

        /* Sets onClickListener and onClick method for Add Quest Button */
        Button addQuestBtn = (Button) findViewById(R.id.addQuestBtn);
        addQuestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask(view);
            }
        });

        /* Sets onClickListener for Lab4 activity */
        Button saBtn = (Button) findViewById(R.id.saBtn);
        saBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saActivity(v);
            }
        });
    }

    /**
     * Starts a a new AddQuestActivity
     * @param view  view object passed by the onClickListener event
     */
    public void addTask(View view) {
        Log.i(TAG, "Clicked Add");
        final Intent intent = new Intent(this, AddQuestActivity.class);
        startActivity(intent);
    }

    public void saActivity(View view) {
        Log.i(TAG, "Clicked Add");
        final Intent intent = new Intent(this, SimpleActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(0, menu.FIRST, 0, "Quest Details");
        getMenuInflater().inflate(R.menu.menu_main_quest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
