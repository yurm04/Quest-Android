package com.yuraima.quest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class QuestListActivity extends AppCompatActivity {
    final static String TAG = "QuestListActivity";
    ArrayAdapter<String> adapter;
    ListView questListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask(view);
            }
        });

        /* Get Quests for ListView*/
        getQuestList();


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

    /**
     * Checks for quest to populate ListView with.
     */
    public void getQuestList() {
        ArrayList<Quest> allQuests = (ArrayList<Quest>) Quest.listAll(Quest.class);
        ArrayList<String> questNames = new ArrayList<>();
        ArrayList<String> questDescs = new ArrayList<>();

        for (Quest quest : allQuests) {
            questNames.add(quest.name);
            questDescs.add(quest.description);
        }

        questListView = (ListView) findViewById(R.id.questListView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, questNames);

        questListView.setAdapter(adapter);
    }

}
