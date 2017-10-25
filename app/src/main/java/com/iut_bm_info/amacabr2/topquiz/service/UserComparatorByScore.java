package com.iut_bm_info.amacabr2.topquiz.service;

import com.iut_bm_info.amacabr2.topquiz.model.User;

import java.util.Comparator;

/**
 * Created by amacabr2 on 25/10/17.
 */

public class UserComparatorByScore implements Comparator<User> {

    @Override
    public int compare(User user1, User user2) {
        return user1.compareTo(user2);
    }
}
