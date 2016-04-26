package com.yuraima.quest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QuestListActivity extends AppCompatActivity
    implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    final static String TAG = "QuestListActivity";
    private GestureDetectorCompat detector;
    ArrayAdapter<Quest> adapter;
    QuestListViewAdapter questListViewAdapter;
    int REQUEST_CODE = 1;

    ListView questListView;
    TextView emptyMessage;
    TextView emptyMessageSub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_quest_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Sets MainQuestActivity as default when the UP button is hit */
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Instantiate double tap gesture detector for activity */
        this.detector = new GestureDetectorCompat(this, this);
        this.detector.setOnDoubleTapListener(this);
        this.detector.setIsLongpressEnabled(true);

        /* Get TextView messages for empty quest list */
        emptyMessage = (TextView) findViewById(R.id.noQuestsMessage);
        emptyMessageSub = (TextView) findViewById(R.id.noQuestsSub);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuest();
            }
        });

        /* Get Quests for ListView*/
        getQuestList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    /**
     * Starts a a new AddQuestActivity
     */
    public void addQuest() {
        Log.i(TAG, "Clicked Add");
        final Intent addIntent = new Intent(this, AddItemActivity.class);
        addIntent.putExtra("action", "add");
        addIntent.putExtra("itemType", "quest");
        startActivity(addIntent);
    }

    /**
     * Checks for quest to populate ListView with.
     */
    public boolean getQuestList() {
        ArrayList<Quest> allQuests = (ArrayList<Quest>) Quest.listAll(Quest.class);

        if (allQuests.size() == 0) {
            showEmptyListMessage();
            return false;
        } else {
            hideEmptyListMessage();
        }

        if ( questListView == null ) {
            questListView = (ListView) findViewById(R.id.questListView);
        }

//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allQuests);
        adapter = new QuestListViewAdapter(this, R.layout.quest_list_item, allQuests);
        questListView.setAdapter(adapter);

        questListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showQuestTaskList(position);
            }
        });

        questListView.setLongClickable(true);
        questListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                setQuestStatus(position);
                return true;
            }
        });

        return true;
    }

    public void showEmptyListMessage() {
        if (emptyMessage != null && emptyMessageSub != null) {
            emptyMessage.setVisibility(View.VISIBLE);
            emptyMessageSub.setVisibility(View.VISIBLE);
        }
    }

    public void setQuestStatus(int position) {
        Quest quest = adapter.getItem(position);
        quest.complete = !quest.complete;
        quest.save();
        getQuestList();
        if (quest.complete) {
            toast("Quest Completed!");
        }
    }

    public void hideEmptyListMessage() {
        if (emptyMessage.getVisibility() == View.VISIBLE ||
            emptyMessageSub.getVisibility() == View.VISIBLE) {
            emptyMessage.setVisibility(View.GONE);
            emptyMessageSub.setVisibility(View.GONE);
        }
    }

    public void showQuestTaskList(int position) {
        Quest selected = adapter.getItem(position);
//        Log.i(TAG, "quest Id: " + selected.getId());
        final Intent taskIntent = new Intent(this, TaskListActivity.class);
        taskIntent.putExtra("quest", selected);
        taskIntent.putExtra("questId", selected.getId());
        startActivityForResult(taskIntent, REQUEST_CODE);
    }

    private void toast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i(TAG, "result: " + data.getStringExtra("result"));
            } else {
                Log.i(TAG, "no result to show");
            }
        }
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
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i(TAG, "onDoubleTap");
        this.addQuest();
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
        Log.i(TAG, "onLongPress");
        this.addQuest();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
