/**
 * CECS 453 Mobile Application Development
 * Professor Fahim
 * @author: Tien Huynh, Howard Chen
 * Final Project : Quiz Taker
 * Due: Aug 15, 2019
 * Purpose: This app is an android quiz taker app which challenges the user to multiple categories.
 * The app shows a good implementation of most of the features we learning in our CECS 453 Mobile
 * Application Development app. The app stores a sqlite database for the username/password and saved questions.
 */

package com.example.quiztaker;

import android.provider.BaseColumns;

/**
 * This class is used to store question column and question table names
 */
public final class QuizContract {

    //default class constructor
    private QuizContract() {}

    public static class QuestionsTable implements BaseColumns {

        public static final String TABLE_QUESTION = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_ChoiceA = "optionA";
        public static final String COLUMN_ChoiceB = "optionB";
        public static final String COLUMN_ChoiceC = "optionC";
        public static final String COLUMN_ChoiceD = "optionD";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
    }
}
