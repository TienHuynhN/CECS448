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

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity acts as a dialog which displays the all the users and their scores in descending order
 */
public class LeaderBoardActivity extends AppCompatActivity
{
    private RecyclerView recycler_view;

    //a list that holds leaderboard information
    private List<Leaderboard> leaderboardList;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_leaderboard);

        //resize this activity window
        this.getWindow().setLayout(1300,1650);

        //create control object
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);

        //instantiate an array list
        leaderboardList = new ArrayList<>();

        //create QuizTakerDatabase object
        QuizTakerDatabase db = new QuizTakerDatabase(this);

        //get all scores from the database
        Cursor cursor = db.getAllScores();

        //check if the database is empty or not
        if(cursor.getCount() > 0)
        {
            //if database if not empty then get the name and score of each player
            do
            {
                //get in_game_name
                String name = cursor.getString(cursor.getColumnIndex(QuizTakerDatabase.COL_IN_GAME_NAME));
                //get scores of particular player
                int scores = cursor.getInt(cursor.getColumnIndex(QuizTakerDatabase.COL_SCORES));

                Leaderboard player = new Leaderboard(name, scores);

                //add to the leaderboardList
                leaderboardList.add(player);
            }while(cursor.moveToNext());

            //get all the ranks as a recycler view
            recycler_view.setAdapter(new LeaderboardRecyclerViewAdapter(leaderboardList));
        }
    }
}
