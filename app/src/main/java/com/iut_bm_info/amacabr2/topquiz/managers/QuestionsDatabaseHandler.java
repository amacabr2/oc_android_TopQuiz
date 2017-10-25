package com.iut_bm_info.amacabr2.topquiz.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iut_bm_info.amacabr2.topquiz.model.Question;
import com.iut_bm_info.amacabr2.topquiz.service.ArrayDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amacabr2 on 22/10/17.
 */

public class QuestionsDatabaseHandler extends DatabaseHandler{

    public QuestionsDatabaseHandler(Context context) {
        super(context);
    }

    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question(
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_ID))),
                        cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_INTITULE)),
                        ArrayDivider.derialize(cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_ANSWERS))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS_INDEXANSWER)))
                );
                questions.add(question);
            } while (cursor.moveToNext());
        }

        db.close();
        return questions;
    }

    public int getQuestionsCount() {
        String countQuery = "SELECT * FROM " + TABLE_QUESTIONS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    public void addQuestion(Question question) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_QUESTIONS_INTITULE, question.getIntitule());
        values.put(KEY_QUESTIONS_ANSWERS, ArrayDivider.serialize(question.getChoicesList()));
        values.put(KEY_QUESTIONS_INDEXANSWER, question.getIndexAnwer());

        db.insert(TABLE_QUESTIONS, null, values);
        db.close();
    }
}
