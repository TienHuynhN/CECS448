package com.example.quiztaker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class QuizTypeActivity extends AppCompatActivity
{
    private Button practice_quiz_button;
    private Button class_quiz_button;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz_type);

        practice_quiz_button = (Button) findViewById(R.id.practice_quiz_button);
        class_quiz_button = (Button) findViewById(R.id.class_quiz_button);

        practice_quiz_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {

            }
        });
    }
}
