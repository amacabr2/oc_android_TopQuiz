package com.iut_bm_info.amacabr2.topquiz.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iut_bm_info.amacabr2.topquiz.model.User;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by amacabr2 on 22/10/17.
 */

public class UsersDatabaseHandler extends DatabaseHandler {

    public UsersDatabaseHandler(Context context) {
        super(context);
    }

    public void addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_USERS_FIRSTNAME, user.getFirstName());
        values.put(KEY_USERS_BESTSCORE, 0);

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void updateUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.i("USER", String.valueOf(user));

        values.put(KEY_USERS_FIRSTNAME, user.getFirstName());
        values.put(KEY_USERS_BESTSCORE, user.getBestScore());

        db.update(TABLE_USERS, values, "id = " + user.getId(), null);
        db.close();
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_USERS_ID))),
                    cursor.getString(cursor.getColumnIndex(KEY_USERS_FIRSTNAME)),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_USERS_BESTSCORE)))
                );
                users.add(user);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return users;
    }

    public List<User> getUsersByBestScore(int n) {
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " ORDER BY " + KEY_USERS_BESTSCORE + " LIMIT " + n;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<User> users = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_USERS_ID))),
                    cursor.getString(cursor.getColumnIndex(KEY_USERS_FIRSTNAME)),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_USERS_BESTSCORE)))
                );
                users.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return users;
    }

    public User getUserByFirstname(String firstname) {
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USERS_FIRSTNAME + " = '" + firstname + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        User user;

        if (cursor.moveToFirst()) {
            user = new User(
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_USERS_ID))),
                cursor.getString(cursor.getColumnIndex(KEY_USERS_FIRSTNAME)),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_USERS_BESTSCORE)))
            );
        } else {
            user = null;
        }

        db.close();
        cursor.close();
        return user;
    }
}
