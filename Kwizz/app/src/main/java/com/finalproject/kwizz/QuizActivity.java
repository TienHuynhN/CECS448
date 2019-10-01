package com.finalproject.kwizz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class QuizActivity extends AppCompatActivity
{
    private ProgressBar progressBarCircle;
    private TextView timer_text;

    private long timeCountInMillis = 60000;

    private CountDownTimer countDownTimer;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //hide action bar for this activity
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        String category = intent.getStringExtra(QuizCategoryContent.quiz_key);
        System.out.println(category);

        progressBarCircle = (ProgressBar)findViewById(R.id.progressBarCircle);
        timer_text = (TextView) findViewById(R.id.timer_text);

        progressBarCircle.setMax((int) (timeCountInMillis / 1000));

        startTimer();
    }

    private void startTimer()
    {
        countDownTimer = new CountDownTimer(timeCountInMillis, 1000)
        {
            @Override
            public void onTick(long l)
            {
                timer_text.setText(Long.toString(TimeUnit.MILLISECONDS.toSeconds(l)));
                progressBarCircle.setProgress((int) (l / 1000));
            }

            @Override
            public void onFinish()
            {

            }
        }.start();

        countDownTimer.start();
    }
}
