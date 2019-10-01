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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity is used to show the result after the game
 */
public class ResultActivity extends AppCompatActivity
{
    //declare control objects
    private TextView percentage_text;
    private TextView result_text;
    private TextView ranked_points_text;
    private Button graph_button;
    private Button continue_button;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //create control objects
        percentage_text = (TextView) findViewById(R.id.percentage);
        result_text = (TextView) findViewById(R.id.result_text);
        ranked_points_text = (TextView) findViewById(R.id.ranked_point);
        graph_button = (Button) findViewById(R.id.graph_button);
        continue_button = (Button) findViewById(R.id.continue_button);

        //get information from intent
        Intent intent = getIntent();
        String points_gain = intent.getStringExtra("points_gain");
        String percentage = intent.getStringExtra("percentage");
        String total_questions = intent.getStringExtra("total_questions");
        String total_correct_answers = intent.getStringExtra("total_correct_answers");

        //hide action bar for this splash screen
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //display the percentage
        percentage_text.setText(percentage + "%");
        result_text.setText("You missed " + (Integer.parseInt(total_questions) - Integer.parseInt(total_correct_answers)) + " out of " + total_questions + " questions");

        //if player gets above 50% then display a text with green color
        if(Integer.parseInt(points_gain) >= 0) {
            ranked_points_text.setTextColor(getResources().getColor(R.color.green));
            ranked_points_text.setText("Ranked points: +" + points_gain);
        }
        //if player gets below 50% then display a text with red color
        else if(Integer.parseInt(points_gain) < 0)
        {
            ranked_points_text.setTextColor(getResources().getColor(R.color.red));
            ranked_points_text.setText("Ranked points: " + points_gain);
        }

        //this method is used to listen to continue event
        continue_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        //this method is used to listen to graph event
        graph_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplication(), ResultGraph.class);
                startActivity(intent);
            }
        });
    }
}
