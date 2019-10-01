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

/**
 * This class is used to hold the information of each question
 */
public class Question
{
    //declare variables
    private String question;
    private String category;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String correct_answer; //the correct choice that is correct;

    //default constructor with no arguments
    public Question() {}

    /**
     * Overloaded constructor
     * @param category question category
     * @param question the actual question
     * @param choiceA option for user to choose
     * @param choiceB option for user to choose
     * @param choiceC option for user to choose
     * @param choiceD option for user to choose
     * @param correct_answer the correct answer
     */
    public Question(String category, String question, String choiceA, String choiceB, String choiceC, String choiceD, String correct_answer) {
        this.category = category;
        this.question = question;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        this.correct_answer = correct_answer;
}

    /**
     * Get the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set the category
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Get the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Set the question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Get option 1
     */
    public String getChoiceA() {
        return choiceA;
    }

    /**
     * Set option 1
     */
    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    /**
     * Get option 2
     */
    public String getChoiceB() {
        return choiceB;
    }

    /**
     *Set option 2
     */
    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    /**
     * Get option 3
     */
    public String getChoiceC() {
        return choiceC;
    }

    /***
     * Set option 3
     */
    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    /**
     * Get option 4
     */
    public String getChoiceD() {
        return choiceD;
    }

    /**
     * Set option 4
     * @param choiceD
     */
    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    /**
     * Get the correct answer
     */
    public String getAnswer() {
        return correct_answer;
    }

    /**
     * Set the correct answer
     */
    public void setAnswer(String correct_answer) {
        this.correct_answer = correct_answer;
    }
}
