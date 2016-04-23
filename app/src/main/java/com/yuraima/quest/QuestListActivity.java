package com.yuraima.quest;

import android.content.Intent;
import android.gesture.Gesture;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class QuestListActivity extends AppCompatActivity
    implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    final static String TAG = "QuestListActivity";
    private GestureDetectorCompat detector;
    ArrayAdapter<String> adapter;
    ListView questListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Instantiate double tap gesture detector for activity */
        this.detector = new GestureDetectorCompat(this, this);
        this.detector.setOnDoubleTapListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

        /* Get Quests for ListView*/
        getQuestList();
    }


    /**
     * Starts a a new AddQuestActivity
     */
    public void addTask() {
        Log.i(TAG, "Clicked Add");
        final Intent intent = new Intent(this, AddItemActivity.class);
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

    /**
     * Calls the getQuestList method when activity restarts
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        getQuestList();
    }

    /**
     * Calls the getQuestList method when activity resumes
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();
        getQuestList();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i(TAG, "onDoubleTap");
        this.addTask();
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
