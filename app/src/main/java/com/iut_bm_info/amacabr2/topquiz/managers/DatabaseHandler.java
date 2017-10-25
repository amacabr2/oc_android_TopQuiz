package com.iut_bm_info.amacabr2.topquiz.managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by amacabr2 on 22/10/17.
 */

public abstract class DatabaseHandler extends SQLiteOpenHelper {

    protected static final int DATABASE_VERSION = 6;

    protected static final String DATABASE_NAME = "topQuizManager";

    protected static final String TABLE_USERS = "users";

    protected static final String KEY_USERS_ID = "id";

    protected static final String KEY_USERS_FIRSTNAME = "firstname";

    protected static final String KEY_USERS_BESTSCORE = "bestscore";

    protected static final String TABLE_QUESTIONS = "questions";

    protected static final String KEY_QUESTIONS_ID = "id";

    protected static final String KEY_QUESTIONS_INTITULE = "intitule";

    protected static final String KEY_QUESTIONS_ANSWERS = "answers";

    protected static final String KEY_QUESTIONS_INDEXANSWER = "index_answer";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTIONS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTIONS + "("
                + KEY_USERS_ID + " INTEGER PRIMARY KEY, "
                + KEY_QUESTIONS_INTITULE + " TEXT, "
                + KEY_QUESTIONS_ANSWERS + " TEXT, "
                + KEY_QUESTIONS_INDEXANSWER + " INTEGER)";
        Log.i("CREATE QUESTIONS TABLE", CREATE_QUESTIONS_TABLE);
        db.execSQL(CREATE_QUESTIONS_TABLE);

        String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "("
                + KEY_USERS_ID + " INTEGER PRIMARY KEY, "
                + KEY_USERS_FIRSTNAME + " TEXT, "
                + KEY_USERS_BESTSCORE + " INTEGER)";
        Log.i("CREATE USERS TABLE", CREATE_USERS_TABLE);
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
