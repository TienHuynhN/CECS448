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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.lombokcyberlab.android.multicolortextview.MultiColorTextView;

/**
 * An activity that acts as a splash screen.  After about 5 seconds,
 * it automatically goes to login page {@Link LogInActivity}
 */
public class SplashScreenActivity extends AppCompatActivity
{
    //declare objects for MultiColorTextView and ImageView
    private MultiColorTextView game_title;
    private ImageView game_logo;

    /**
     * Sets up the splash screen and disables the action bar for this
     * activity.  The splash screen will appear for 5 seconds before
     * going to the login page
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //need to wait for 4 seconds to go to login page
        final int time = 4000;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        game_title = (MultiColorTextView) findViewById(R.id.game_title);
        game_logo = (ImageView) findViewById(R.id.game_logo);

        //loading animation for logo and game title
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_screen_animation);
        game_title.startAnimation(animation);
        game_logo.startAnimation(animation);

        //hide action bar for this splash screen
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //send an intent to the LogInActivity
        final Intent intent = new Intent(getApplication(), LogInActivity.class);

        //sleep for 5 seconds
        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(time);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    //go to login page after 5 seconds
                    startActivity(intent);
                }
            }
        };

        //start sleeping
        timer.start();
    }
}
