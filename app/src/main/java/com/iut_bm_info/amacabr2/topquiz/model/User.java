package com.iut_bm_info.amacabr2.topquiz.model;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by amacabr2 on 19/10/17.
 */

public class User implements Comparable<User> {

    private int id;

    private String firstName;

    private int bestScore;

    public User(String firstName, int bestScore) {
        this.firstName = firstName;
        this.bestScore = bestScore;
    }

    public User(int id, String firstName, int bestScore) {
        this.id = id;
        this.firstName = firstName;
        this.bestScore = bestScore;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    @Override
    public int compareTo(@NonNull User user) {
        return this.bestScore - user.bestScore;
    }
}
