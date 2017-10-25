package com.iut_bm_info.amacabr2.topquiz.service;

import com.iut_bm_info.amacabr2.topquiz.model.Question;

import java.util.Collections;
import java.util.List;

/**
 * Created by amacabr2 on 19/10/17.
 */

public class QuestionBank {

    private List<Question> questions;

    private int nextQuestionIndex;

    public QuestionBank(List<Question> questions) {
        this.questions = questions;
        Collections.shuffle(this.questions);
        this.nextQuestionIndex = 0;
    }

    public Question getQuestion() {
        if (nextQuestionIndex == questions.size()) {
            nextQuestionIndex = 0;
        }
        return questions.get(nextQuestionIndex++);
    }
}
