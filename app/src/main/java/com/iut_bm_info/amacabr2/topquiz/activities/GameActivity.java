package com.iut_bm_info.amacabr2.topquiz.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iut_bm_info.amacabr2.topquiz.R;
import com.iut_bm_info.amacabr2.topquiz.managers.QuestionsDatabaseHandler;
import com.iut_bm_info.amacabr2.topquiz.managers.UsersDatabaseHandler;
import com.iut_bm_info.amacabr2.topquiz.model.Question;
import com.iut_bm_info.amacabr2.topquiz.model.User;
import com.iut_bm_info.amacabr2.topquiz.service.QuestionBank;

import java.util.Arrays;

import static android.view.View.*;

public class GameActivity extends AppCompatActivity implements OnClickListener {

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    public static final String BUNDLE_STATE_SCORE = "currentScore";

    public static final String BUNDLE_STATE_POINTCURRENTQUESTION = "pointCurrentQuestion";

    public static final String BUNDLE_STATE_NBQUESTION = "currentNbQuestion";

    public static final String BUNDLE_STATE_CURRENTQUESTION = "currentQuestion";

    private static final String PREFERENCE_FIRSTNAME = "firstname";

    private static final String PREFERENCE_LASTSCORE = "lastscore";

    private QuestionsDatabaseHandler dbQuestions;

    private UsersDatabaseHandler dbUsers;

    private TextView textViewQuestion;

    private Button buttonAnswer1;

    private Button buttonAnswer2;

    private Button buttonAnswer3;

    private Button buttonAnswer4;

    private QuestionBank questionBank;

    private Question currentQuestion;

    private int numberOfQuestion;

    private int pointOfQuestion;

    private int score;

    private boolean enableTouchEvent;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textViewQuestion = (TextView)findViewById(R.id.activityGame_textViewQuestion);
        buttonAnswer1 = (Button)findViewById(R.id.activityGame_buttonAnswer1);
        buttonAnswer2 = (Button)findViewById(R.id.activityGame_buttonAnswer2);
        buttonAnswer3 = (Button)findViewById(R.id.activityGame_buttonAnswer3);
        buttonAnswer4 = (Button)findViewById(R.id.activityGame_buttonAnswer4);

        dbUsers = new UsersDatabaseHandler(this);
        dbQuestions = new QuestionsDatabaseHandler(this);
        if (dbQuestions.getQuestionsCount() == 0) {
            createQuestions();
        }
        enableTouchEvent = true;
        questionBank = generateQuestions();

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            numberOfQuestion = savedInstanceState.getInt(BUNDLE_STATE_NBQUESTION);
            pointOfQuestion = savedInstanceState.getInt(BUNDLE_STATE_POINTCURRENTQUESTION);
            currentQuestion = (Question) savedInstanceState.getSerializable(BUNDLE_STATE_CURRENTQUESTION);
        } else {
            numberOfQuestion = 4;
            pointOfQuestion = 3;
            score = 0;
            currentQuestion = questionBank.getQuestion();
        }

        displayQuestion(currentQuestion);
        setTag();

        buttonAnswer1.setOnClickListener(this);
        buttonAnswer2.setOnClickListener(this);
        buttonAnswer3.setOnClickListener(this);
        buttonAnswer4.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, score);
        outState.putInt(BUNDLE_STATE_NBQUESTION, numberOfQuestion);
        outState.putInt(BUNDLE_STATE_POINTCURRENTQUESTION, pointOfQuestion);
        outState.putSerializable(BUNDLE_STATE_CURRENTQUESTION, currentQuestion);
        super.onSaveInstanceState(outState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        if ((int)v.getTag() == currentQuestion.getIndexAnwer()) {
            changeColorButton((int)v.getTag(), getResources().getColor(R.color.colorGreen, null));
            score += pointOfQuestion;
            afficheToast("Good anwer");
            enableTouchEvent = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finishGame();
                }
            }, 1000);
        } else {
            changeColorButton((int)v.getTag(), getResources().getColor(R.color.colorRed, null));
            if (pointOfQuestion > 0) {
                pointOfQuestion--;
            }
            afficheToast("Bad anwer");
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return enableTouchEvent && super.dispatchTouchEvent(ev);
    }

    private void setTag() {
        buttonAnswer1.setTag(0);
        buttonAnswer2.setTag(1);
        buttonAnswer3.setTag(2);
        buttonAnswer4.setTag(3);
    }

    private QuestionBank generateQuestions() {
        return new QuestionBank(dbQuestions.getQuestions());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void displayQuestion(Question question) {
        buttonAnswer1.setBackgroundColor(getResources().getColor(R.color.background_light, null));
        buttonAnswer1.setTextColor(getResources().getColor(R.color.colorAccent, null));
        buttonAnswer2.setBackgroundColor(getResources().getColor(R.color.background_light, null));
        buttonAnswer2.setTextColor(getResources().getColor(R.color.colorAccent, null));
        buttonAnswer3.setBackgroundColor(getResources().getColor(R.color.background_light, null));
        buttonAnswer3.setTextColor(getResources().getColor(R.color.colorAccent, null));
        buttonAnswer4.setBackgroundColor(getResources().getColor(R.color.background_light, null));
        buttonAnswer4.setTextColor(getResources().getColor(R.color.colorAccent, null));
        textViewQuestion.setText(question.getIntitule());
        buttonAnswer1.setText(question.getChoicesList().get(0));
        buttonAnswer2.setText(question.getChoicesList().get(1));
        buttonAnswer3.setText(question.getChoicesList().get(2));
        buttonAnswer4.setText(question.getChoicesList().get(3));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void finishGame() {
        if (--numberOfQuestion == 0) {
            changeBestScore();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Well done!")
                    .setMessage("Your score is " + score)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.putExtra(BUNDLE_EXTRA_SCORE, score);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }).create().show();
        } else {
            currentQuestion = questionBank.getQuestion();
            displayQuestion(currentQuestion);
            pointOfQuestion = 3;
            enableTouchEvent = true;
        }
    }

    private void changeBestScore() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(GameActivity.this);
        preferences.edit().putString(PREFERENCE_LASTSCORE, String.valueOf(score)).apply();
        String firstname = preferences.getString(PREFERENCE_FIRSTNAME, "");
        User user = dbUsers.getUserByFirstname(firstname);
        if (score > user.getBestScore()) {
            user.setBestScore(score);
            dbUsers.updateUser(user);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeColorButton(int tag, int color) {
        if (tag == (int)buttonAnswer1.getTag()) {
            buttonAnswer1.setBackgroundColor(color);
            buttonAnswer1.setTextColor(Color.WHITE);
        } else if (tag == (int)buttonAnswer2.getTag()) {
            buttonAnswer2.setBackgroundColor(color);
            buttonAnswer2.setTextColor(Color.WHITE);
        } else if (tag == (int)buttonAnswer3.getTag()) {
            buttonAnswer3.setBackgroundColor(color);
            buttonAnswer3.setTextColor(Color.WHITE);
        } else if (tag == (int)buttonAnswer4.getTag()) {
            buttonAnswer4.setBackgroundColor(color);
            buttonAnswer4.setTextColor(Color.WHITE);
        }
    }

    private void afficheToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void createQuestions() {
        dbQuestions.addQuestion(new Question("Who is the creator of Android?",
                Arrays.asList("Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"),
                0));

        dbQuestions.addQuestion(new Question("When did the first man land on the moon?",
                Arrays.asList("1958",
                        "1962",
                        "1967",
                        "1969"),
                3));

        dbQuestions.addQuestion(new Question("What is the house number of The Simpsons?",
                Arrays.asList("42",
                        "101",
                        "742",
                        "666"),
                2));
    }
}
