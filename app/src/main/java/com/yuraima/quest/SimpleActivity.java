package com.yuraima.quest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleActivity extends AppCompatActivity {
    final static String TAG = "SimpleActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Get Quests */
        List<Quest> allQuests = Quest.listAll(Quest.class);
        List<Map<String,String>> questStrings = new ArrayList<Map<String,String>>();

        for (Quest quest : allQuests) {
            HashMap<String, String> singleQuest = new HashMap<String, String>();
            singleQuest.put("quest", quest.toString());

            questStrings.add(singleQuest);
        }

        /* Create List View */
        // ?
    }

}
