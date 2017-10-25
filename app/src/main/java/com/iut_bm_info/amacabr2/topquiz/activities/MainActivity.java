package com.iut_bm_info.amacabr2.topquiz.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iut_bm_info.amacabr2.topquiz.R;
import com.iut_bm_info.amacabr2.topquiz.managers.UsersDatabaseHandler;
import com.iut_bm_info.amacabr2.topquiz.model.User;

public class MainActivity extends AppCompatActivity implements OnClickListener, TextWatcher{

    private static int GAME_ACTIVITY_REQUEST_CODE = 2;

    private static final String PREFERENCE_FIRSTNAME = "firstname";

    private static final String PREFERENCE_LASTSCORE = "lastscore";

    private UsersDatabaseHandler dbUser;

    private TextView textViewLastScore;

    private EditText editTextNameUser;

    private Button buttonLetsGo;

    private Button buttonBestScore;

    private User user;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbUser = new UsersDatabaseHandler(this);

        textViewLastScore = (TextView)findViewById(R.id.activityMain_lastScore);
        editTextNameUser = (EditText)findViewById(R.id.activityMain_editTextNameUser);
        buttonLetsGo = (Button)findViewById(R.id.activityMain_buttonLetsGo);
        buttonBestScore = (Button)findViewById(R.id.activityMain_buttonBestScore);

        editTextNameUser.addTextChangedListener(this);
        buttonLetsGo.setOnClickListener(this);
        buttonBestScore.setOnClickListener(this);

        setEnabledLetGo(false);
        setEnabledBestScore();
        setTag();
        printLastScore();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
            sharedPreferences.edit().putString(PREFERENCE_LASTSCORE, String.valueOf(data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0))).apply();
            printLastScore();
        }
    }

    @Override
    public void onClick(View v) {
        if ((int)v.getTag() == (int)buttonLetsGo.getTag()) {
            createGameActivity();
        } else if ((int)v.getTag() == (int)buttonBestScore.getTag()) {
            createBestScoreActivity();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        setEnabledLetGo(s.toString().length() != 0);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void setTag() {
        buttonLetsGo.setTag(0);
        buttonBestScore.setTag(1);
        textViewLastScore.setTag(2);
    }

    private void createGameActivity() {
        String firstName = editTextNameUser.getText().toString();
        if (dbUser.getUserByFirstname(firstName) == null) {
            user = new User(firstName, 0);
            dbUser.addUser(user);
        } else {
            user = dbUser.getUserByFirstname(firstName);
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        sharedPreferences.edit().putString(PREFERENCE_FIRSTNAME, user.getFirstName()).apply();
        startActivityForResult(new Intent(MainActivity.this, GameActivity.class), GAME_ACTIVITY_REQUEST_CODE);
    }

    private void createBestScoreActivity() {
        startActivity(new Intent(MainActivity.this, BestScoreActivity.class));
    }

    private String getPlayer() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        return preferences.getString(PREFERENCE_FIRSTNAME, "");
    }

    private String getLastScore() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        return preferences.getString(PREFERENCE_LASTSCORE, "");
    }

    private void printLastScore() {
        String lastScore;
        if ((getPlayer() != null) && (lastScore = getLastScore()) != null) {
            textViewLastScore.setText("Your last score is " + lastScore + ", will you do better this time?");
            textViewLastScore.setPadding(40, 0, 20, 0);
            editTextNameUser.setText(getPlayer());
            editTextNameUser.setSelection(editTextNameUser.getText().length());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setEnabledLetGo(boolean enabled) {
        buttonLetsGo.setEnabled(enabled);
        if (!enabled) {
            buttonLetsGo.setBackgroundColor(getResources().getColor(R.color.colorEnabled, null));
            buttonLetsGo.setTextColor(Color.BLACK);
        } else {
            buttonLetsGo.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
            buttonLetsGo.setTextColor(Color.WHITE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setEnabledBestScore() {
        if (dbUser.getUsers().size() == 0) {
            buttonBestScore.setEnabled(false);
            buttonBestScore.setBackgroundColor(getResources().getColor(R.color.colorEnabled, null));
            buttonLetsGo.setTextColor(Color.BLACK);
        } else {
            buttonBestScore.setEnabled(true);
            buttonBestScore.setBackgroundColor(getResources().getColor(R.color.colorSilver, null));
            buttonLetsGo.setTextColor(Color.WHITE);
        }
    }
}
