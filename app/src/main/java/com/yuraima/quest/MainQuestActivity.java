package com.yuraima.quest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

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
        Button addQuestBtn = (Button) findViewById(R.id.addBtn);
        addQuestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask(view);
            }
        });

        /* Sets onClickListener for Lab4 activity */
        Button listBtn = (Button) findViewById(R.id.questListBtn);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuestList(v);
            }
        });

        /* Log all Quests */
        List<Quest> allQuests = Quest.listAll(Quest.class);
        Log.i(TAG, allQuests.toString());
        Log.i(TAG, "onCreate");
    }

    /**
     * Starts a a new AddQuestActivity
     * @param view  view object passed by the onClickListener event
     */
    public void addTask(View view) {
        Log.i(TAG, "Clicked Add");
        final Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    /**
     * Starts a new activity QuestListActivity that displays
     * all quests in a ListView
     * @param view  view object passed by the onClickListener event
     */
    public void showQuestList(View view) {
        Log.i(TAG, "Clicked show quests");
        final Intent intent = new Intent(this, QuestListActivity.class);
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

    public void toast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }
}
