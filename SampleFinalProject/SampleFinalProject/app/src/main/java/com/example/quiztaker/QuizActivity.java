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

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This activity is used to display the question and answer after each submission
 */
public class QuizActivity extends AppCompatActivity
{
    //declare control objects
    private TextView rounds;
    private TextView questions;
    private TextView question_text;
    private TextView option1;
    private TextView option2;
    private TextView option3;
    private TextView option4;
    private Button quit_button;
    private Button submit_button;

    //declare variables
    private static int question_counter;
    private static int round_counter;
    private int num_of_questions;
    private int num_of_rounds;
    private boolean answered; //true if question answered
    private String user_answer;
    private String in_game_name;

    private static int new_question;
    private static int total_questions;
    private static int total_correct_answers;
    private static int correct_answers_per_round;
    private boolean isResultReady;

    //Question object that holds current question
    private Question currentQuestion;
    //a list of questions of the same category
    private List<Question> questionList;
    //use to keep track of how many scores user made for each round
    public static Map<Integer, Integer> rounds_questions;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //create control objects
        rounds = (TextView) findViewById(R.id.round_text);
        questions = (TextView) findViewById(R.id.question_num);
        question_text = (TextView) findViewById(R.id.question_text);
        option1 = (TextView) findViewById(R.id.option1);
        option2 = (TextView) findViewById(R.id.option2);
        option3 = (TextView) findViewById(R.id.option3);
        option4 = (TextView) findViewById(R.id.option4);
        quit_button = (Button) findViewById(R.id.quit_button);
        submit_button = (Button) findViewById(R.id.submit_button);

        //hide action bar for this splash screen
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //get information that have been passed to this activity through intent
        Intent pre_intent = getIntent();
        String category = pre_intent.getStringExtra("category");
        num_of_rounds = Integer.parseInt(pre_intent.getStringExtra("num_of_rounds"));
        num_of_questions = Integer.parseInt(pre_intent.getStringExtra("num_of_questions"));
        in_game_name = pre_intent.getStringExtra("in_game_name");

        //initialize variables
        new_question = 0;
        isResultReady = false;
        total_questions = num_of_rounds * num_of_questions;
        total_correct_answers = 0;
        question_counter = 0;
        round_counter = 0;
        user_answer = "";
        correct_answers_per_round = 0;

        rounds_questions = new LinkedHashMap<>();

        //get all the questions that have the same category in the database
        QuizTakerDatabase quiz_db = new QuizTakerDatabase(this);
        questionList = quiz_db.getSpecificCategoryQuestions(category);

        //shuffle the all questions
        Collections.shuffle(questionList);

        if(question_counter < num_of_questions) {
            //show the question one after another
            showNextQuestion();
        }

        //go back to home menu if user clicks on quit button
        //this method is used to listen to logout event
        quit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating the dialog builder
                AlertDialog.Builder builder= new AlertDialog.Builder(QuizActivity.this);

                //create SpannableStringBuilder object with the message as an argument
                SpannableStringBuilder title = new SpannableStringBuilder("Quit");
                SpannableStringBuilder message = new SpannableStringBuilder("Are you sure you want to quit?");
                SpannableStringBuilder yes = new SpannableStringBuilder("Yes");
                SpannableStringBuilder cancel = new SpannableStringBuilder("Cancel");

                //true if device is a tablet, false if device is a phone
                boolean isTablet = getResources().getBoolean(R.bool.isTablet);

                //if it's a tablet, then make alert dialog bigger
                if(isTablet)
                {
                    title.setSpan(new RelativeSizeSpan(2.1f), 0, title.length(), 0);
                    message.setSpan(new RelativeSizeSpan(2.1f), 0, message.length(), 0);
                    yes.setSpan(new RelativeSizeSpan(2.1f), 0, yes.length(), 0);
                    cancel.setSpan(new RelativeSizeSpan(2.1f), 0, cancel.length(), 0);
                }

                //set the title for the dialog
                builder.setTitle(title);
                //set the message
                builder.setMessage(message);
                //disable background
                builder.setCancelable(false);

                builder.setNegativeButton(cancel, new DialogInterface.OnClickListener()
                {
                    @Override
                    //if user clicks cancel then do nothing
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });

                builder.setPositiveButton(yes, new DialogInterface.OnClickListener()
                {
                    //if user clicks yes then go to log in page
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        finish();
                    }
                });

                AlertDialog alert = builder.create();

                //show the alert dialog
                alert.show();

                if(isTablet)
                {
                    //resize the window
                    alert.getWindow().setLayout(1300,500);
                }
            }
        });

        //get the textview id based on the which textview user clicks on
        View.OnClickListener handler = new View.OnClickListener()
        {
            public void onClick(View v)
            {
                switch(v.getId())
                {
                    case R.id.option1:
                    {
                        //get the text from text view
                        user_answer = (String) option1.getText().toString();
                        //set the background to white to indicate the user has selected this textview
                        option1.setBackgroundResource(R.color.white);
                        //make others' background to default
                        option2.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);
                        option3.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);
                        option4.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);
                        break;
                    }
                    case R.id.option2:
                    {
                        //get the text from text view
                        user_answer = (String) option2.getText().toString();
                        //set the background to white to indicate the user has selected this textview
                        option2.setBackgroundResource(R.color.white);
                        //make others' background to default
                        option1.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);
                        option3.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);
                        option4.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);
                        break;
                    }
                    case R.id.option3:
                    {
                        //get the text from text view
                        user_answer = (String) option3.getText().toString();
                        //set the background to white to indicate the user has selected this textview
                        option3.setBackgroundResource(R.color.white);
                        //make others' background to default
                        option1.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);
                        option2.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);
                        option4.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);
                        break;
                    }
                    case R.id.option4:
                    {
                        //get the text from text view
                        user_answer = (String) option4.getText().toString();
                        //set the background to white to indicate the user has selected this textview
                        option4.setBackgroundResource(R.color.white);
                        //make others' background to default
                        option1.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);
                        option2.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);
                        option3.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);
                        break;
                    }
                }
            }
        };

        //find the views
        findViewById(R.id.option1).setOnClickListener(handler);
        findViewById(R.id.option2).setOnClickListener(handler);
        findViewById(R.id.option3).setOnClickListener(handler);
        findViewById(R.id.option4).setOnClickListener(handler);

        //this method is used to listen to submit event
        submit_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(isResultReady)
                {
                    int points_gain = 0;
                    int total_points = 0;

                    //get the percentage of the correct answers over total questions
                    int percentage = (int) (((double) total_correct_answers / total_questions) * 100);

                    //if player made all correct answers then he/she gets +5 points
                    if(percentage >= 100)
                    {
                        points_gain = 5;
                    }
                    //get +4 points if getting 90% -100%
                    else if(percentage >= 90)
                    {
                        points_gain = 4;
                    }
                    //get +3 points if getting 80% - 90%
                    else if(percentage >= 80)
                    {
                        points_gain = 3;
                    }
                    //get +2 points  if getting 70% -80%
                    else if(percentage >= 70)
                    {
                        points_gain = 2;
                    }
                    //get +1 point if getting 60% - 70%
                    else if(percentage >= 60)
                    {
                        points_gain = 1;
                    }
                    //get 0 point if getting 50% or above
                    else if(percentage >= 50)
                    {
                        points_gain = 0;
                    }
                    //get -1 point if getting 40% -50%
                    else if(percentage >= 40)
                    {
                        points_gain = -1;
                    }
                    //get -2 points if getting 30% -40%
                    else if(percentage >= 30)
                    {
                        points_gain = -2;
                    }
                    //get -3 points if getting 20% - 30%
                    else if(percentage >= 20)
                    {
                        points_gain = -3;
                    }
                    //get -4 points if getting 10% - 20%
                    else if(percentage >= 10)
                    {
                        points_gain = -4;
                    }
                    //get -5 points if getting 0% - 10%
                    else if(percentage >= 0)
                    {
                        points_gain = -5;
                    }


                    Cursor cursor = LogInActivity.db.getSpecificScores(in_game_name);

                    if(cursor.getCount() > 0)
                    {
                        //total the points that the player get with his/her points in the database
                        total_points = points_gain + cursor.getInt(cursor.getColumnIndex(QuizTakerDatabase.COL_SCORES));

                        if(total_points < 0)
                        {
                            total_points = 0;
                        }

                        //update the score
                        LogInActivity.db.updateScore(in_game_name, total_points);
                    }
                    else
                    {
                        if(points_gain < 0)
                        {
                            points_gain = 0;
                        }
                        //insert the score if this is the first time user plays
                        LogInActivity.db.insertScore(in_game_name, points_gain);
                    }

                    //go to result activity
                    Intent intent = new Intent(getApplication(), ResultActivity.class);
                    intent.putExtra("points_gain", "" + points_gain);
                    intent.putExtra("percentage","" + percentage);
                    intent.putExtra("total_correct_answers", "" + total_correct_answers);
                    intent.putExtra("total_questions", "" + total_questions);
                    startActivity(intent);

                    finish();
                }
                else
                {
                    if(!answered)
                    {
                        if(user_answer.equals(""))
                        {
                            //inform the user to select an answer
                            Toast.makeText(getApplicationContext(), "Please select an answer", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            checkAnswer();
                        }
                    }
                    else
                    {
                        showNextQuestion();
                    }
                }
            }
        });
    }

    /**
     * Show the next question
     */
    private void showNextQuestion()
    {
        //reuse the question if all the questions are used up
        if(new_question >= questionList.size())
        {
            new_question = 0;
        }

        //resets to default background
        option1.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);
        option2.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);
        option3.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);
        option4.setBackgroundResource(R.drawable.zinc_rounded_rectangle_border);

        //display the current round
        rounds.setText("Round " + (round_counter + 1));

            //display the current question
            currentQuestion = questionList.get(new_question++);

            //shuffle all the options
            List<String> options = new ArrayList<>();
            options.add(currentQuestion.getChoiceA());
            options.add(currentQuestion.getChoiceB());
            options.add(currentQuestion.getChoiceC());
            options.add(currentQuestion.getChoiceD());

            //shuffle all the opions before displaying to screen
            Collections.shuffle(options);

            //display quetsion
            question_text.setText(currentQuestion.getQuestion());

            questions.setText("Questions: " + (question_counter + 1) + "/" + num_of_questions);
            //display all the options
            option1.setText(options.get(0));
            option2.setText(options.get(1));
            option3.setText(options.get(2));
            option4.setText(options.get(3));

            //increment the question_counter
            question_counter++;

            //resets answered boolean
            answered = false;

            submit_button.setText(R.string.submit);
    }

    /**
     * This method is used to check answer
     */
    private void checkAnswer()
    {
        answered = true;

        //make a toast if user answer correctly or wrong
        if(user_answer.equals(currentQuestion.getAnswer()))
        {
            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
            total_correct_answers++;
            correct_answers_per_round++;
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
        }

        //reset the question counter in increment round for each round
        if(question_counter >= num_of_questions && round_counter < num_of_rounds)
        {
            rounds_questions.put(round_counter + 1, correct_answers_per_round);
            correct_answers_per_round = 0;
            round_counter++;
            question_counter = 0;
        }

        showSolution();
    }

    /**
     * This method is used to show the solution to screen
     */
    private void showSolution()
    {
        //get text from textviews
        String optionA = (String) option1.getText().toString();
        String optionB = (String) option2.getText().toString();
        String optionC = (String) option3.getText().toString();
        String optionD = (String) option4.getText().toString();

        //make green background for correct answer and red for the rest
        if(optionA.equals(currentQuestion.getAnswer()))
        {
            option1.setBackgroundResource(R.color.green);
        }
        else
        {
            option1.setBackgroundResource(R.color.red);
        }

        //make green background for correct answer and red for the rest
        if(optionB.equals(currentQuestion.getAnswer()))
        {
            option2.setBackgroundResource(R.color.green);
        }
        else
        {
            option2.setBackgroundResource(R.color.red);
        }

        //make green background for correct answer and red for the rest
        if(optionC.equals(currentQuestion.getAnswer()))
        {
            option3.setBackgroundResource(R.color.green);
        }
        else
        {
            option3.setBackgroundResource(R.color.red);
        }

        //make green background for correct answer and red for the rest
        if(optionD.equals(currentQuestion.getAnswer()))
        {
            option4.setBackgroundResource(R.color.green);
        }
        else
        {
            option4.setBackgroundResource(R.color.red);
        }

        //change the button text
        if(round_counter < num_of_rounds)
        {
            submit_button.setText("Next");
        }

        if(round_counter >= num_of_rounds)
        {
            submit_button.setText("Result");
            isResultReady = true;
        }
    }
}
