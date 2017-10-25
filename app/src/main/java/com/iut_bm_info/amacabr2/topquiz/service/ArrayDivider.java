package com.iut_bm_info.amacabr2.topquiz.service;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amacabr2 on 22/10/17.
 */

public class ArrayDivider {

    private static final String ARRAY_DIVIDER = "#a1r2ra5yd2iv1i9der";

    public static String serialize(List<String> content) {
        return TextUtils.join(ARRAY_DIVIDER, content);
    }

    public static List<String> derialize(String content) {
        List<String> list = new ArrayList<>();
        String[] tab =  content.split(ARRAY_DIVIDER);
        for (int i = 0; i < tab.length; i++) {
            list.add(tab[i]);
        }
        return list;
    }
}
