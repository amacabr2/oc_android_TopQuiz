package com.iut_bm_info.amacabr2.topquiz.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by amacabr2 on 19/10/17.
 */

public class Question implements Serializable {

    private int id;

    private String intitule;

    private List<String> choices;

    private int indexAnwer;

    public Question(String intitule, List<String> choices, int indexAnwer) {
        this.intitule = intitule;
        this.choices = choices;
        this.indexAnwer = indexAnwer;
    }

    public Question(int id, String intitule, List<String> choices, int indexAnwer) {
        this.id = id;
        this.intitule = intitule;
        this.choices = choices;
        this.indexAnwer = indexAnwer;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public List<String> getChoicesList() {
        return choices;
    }

    public void setChoicesList(List<String> choicesList) {
        this.choices = choicesList;
    }

    public int getIndexAnwer() {
        return indexAnwer;
    }

    public void setIndexAnwer(int indexAnwer) {
        this.indexAnwer = indexAnwer;
    }
}
