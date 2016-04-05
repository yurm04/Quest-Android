package com.yuraima.quest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by yestevez on 4/4/16.
 */
public class QuestDBHelper extends SQLiteOpenHelper {
    public final static String TAG = "DBHelper";
    private final static String DATABASE_NAME = "QUEST";
    private final static int DATABASE_VERSION = 1;

    /* QUEST DATABASE AND TABLE DEFINITION */
    public final static String QUEST_TABLE_NAME = "quest";
    public final static String QUEST_NAME = "name";
    public final static String QUEST_DESC = "description";

    private SQLiteDatabase qdb = this.getWritableDatabase();

    private final static String DB_CREATE_QUEST_TABLE =
            "CREATE TABLE " + QUEST_TABLE_NAME + " (" +
            "id PRIMARY_KEY, " +
            QUEST_NAME + " TEXT, " +
            QUEST_DESC + " TEXT)";

    /* METHOD TO ADD ROW */
    public boolean addQuest(String name, String description) {
        Log.i(TAG, "adding new quest");
        ContentValues values = new ContentValues();
        values.put(QUEST_NAME, name);
        values.put(QUEST_DESC, description);

        long insertion = this.qdb.insert(QUEST_TABLE_NAME, null, values);

        String[] columns = {QUEST_NAME, QUEST_DESC};

        SQLiteDatabase readable = this.getReadableDatabase();

        Cursor c = readable.query(QUEST_TABLE_NAME, columns, null, null, null, null, null);

        String rowName = null;
        String rowDesc = null;
        try {
            while (c.moveToNext()) {
                rowName = c.getString(c.getColumnIndexOrThrow(QUEST_NAME));
                rowDesc = c.getString(c.getColumnIndexOrThrow(QUEST_DESC));
                Log.i(TAG, rowName + ": " + rowDesc);
            }
        } finally {
            c.close();
        }

        if (rowName != null && rowDesc != null) {
            return true;
        } else {
            return false;
        }
    }

    QuestDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_QUEST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // NOT SURE WHAT TO DO HERE BUT HAD TO IMPLEMENT
    }
}
