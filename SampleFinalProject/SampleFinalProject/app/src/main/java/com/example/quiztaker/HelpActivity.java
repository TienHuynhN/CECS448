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

import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity acts as a help dialog that shows user how to play
 * the game
 */
public class HelpActivity extends AppCompatActivity
{
    //declare control objects
    private TextView exit;
    private TextView help_msg;
    private Button back_button;

    private String user_type;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        //get information from intent
        Intent intent = getIntent();
        user_type = intent.getStringExtra("user_type");

        //create control objects
        exit = (TextView) findViewById(R.id.exit);
        help_msg = (TextView) findViewById(R.id.help_msg);
        back_button = (Button) findViewById(R.id.back_button);

        //disable the background, make it untouchable
        this.setFinishOnTouchOutside(false);

        //adjust the size of the help dialog
        this.getWindow().setLayout(1300,1450);

        //dismiss the dialog if user clicks on "x" button
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //go back to setting dialog if user clicks on back button
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), SettingActivity.class);
                intent.putExtra("user_type", user_type);
                startActivity(intent);
                finish();
            }
        });
    }
}
