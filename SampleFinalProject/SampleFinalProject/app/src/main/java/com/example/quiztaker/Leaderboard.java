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
 * This class is used to store leaderboard information such as in-game name and scores
 */
public class Leaderboard
{
    private String in_game_name;
    private int scores;

    /**
     * Overloaded constructor
     * @param in_game_name name the player use in game as their identifier
     * @param scores the total score that they have
     */
    public Leaderboard(String in_game_name, int scores)
    {
        this.in_game_name = in_game_name;
        this.scores = scores;
    }

    /**
     * Get in-game name
     */
    public String getInGameName()
    {
        return in_game_name;
    }

    /**
     * Get scores
     */
    public int getScores()
    {
        return scores;
    }
}
