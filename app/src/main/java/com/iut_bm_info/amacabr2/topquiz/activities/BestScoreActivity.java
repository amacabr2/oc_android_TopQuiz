package com.iut_bm_info.amacabr2.topquiz.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.iut_bm_info.amacabr2.topquiz.R;
import com.iut_bm_info.amacabr2.topquiz.managers.UsersDatabaseHandler;
import com.iut_bm_info.amacabr2.topquiz.model.User;
import com.iut_bm_info.amacabr2.topquiz.service.UserComparatorByName;
import com.iut_bm_info.amacabr2.topquiz.service.UserComparatorByScore;

import java.util.Collections;
import java.util.List;

import static android.view.Gravity.CENTER;
import static android.view.View.*;

public class BestScoreActivity extends AppCompatActivity implements OnClickListener {

    private UsersDatabaseHandler dbUsers;

    private List<User> users;

    private TableLayout tableLayout;

    private Button buttonOrderByName;

    private Button buttonOrderByScore;

    private Button buttonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_score);

        dbUsers = new UsersDatabaseHandler(this);
        users = dbUsers.getUsersByBestScore(5);
        tableLayout = (TableLayout)findViewById(R.id.activityBestScore_tableBestScore);
        buttonOrderByName = (Button)findViewById(R.id.activityBestScore_buttonOrderByName);
        buttonOrderByScore = (Button)findViewById(R.id.activityBestScore_buttonOrderByScore);
        buttonReturn = (Button)findViewById(R.id.activityBestScore_return);

        buttonOrderByName.setOnClickListener(this);
        buttonOrderByScore.setOnClickListener(this);
        buttonReturn.setOnClickListener(this);

        Collections.sort(users, Collections.<User>reverseOrder());
        setTag();
        createTab();
    }

    @Override
    public void onClick(View v) {
        int id = (int)v.getTag();
        if (id == (int)buttonOrderByScore.getTag()) {
            Collections.sort(users, Collections.reverseOrder(new UserComparatorByScore()));
            createTab();
        } else if (id == (int)buttonOrderByName.getTag()) {
            Collections.sort(users, new UserComparatorByName());
            createTab();
        } else if (id == (int)buttonReturn.getTag()) {
            startActivity(new Intent(BestScoreActivity.this, MainActivity.class));
        }
    }

    private void setTag() {
        buttonOrderByName.setTag(0);
        buttonOrderByScore.setTag(1);
        buttonReturn.setTag(2);
    }

    private void createTab() {
        tableLayout.removeAllViews();
        for (User user: users) {
            createNewLine(user);
        }
    }

    private void createNewLine(User user) {
        TextView textViewPseudo = new TextView(this);
        textViewPseudo.setText(user.getFirstName());
        textViewPseudo.setGravity(CENTER);
        textViewPseudo.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );

        TextView textViewBestScore = new TextView(this);
        textViewBestScore.setText(String.valueOf(user.getBestScore()));
        textViewBestScore.setGravity(CENTER);
        textViewBestScore.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );

        TableRow row = new TableRow(this);
        row.addView(textViewPseudo);
        row.addView(textViewBestScore);
        tableLayout.addView(row);
    }
}
