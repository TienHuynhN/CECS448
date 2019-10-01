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
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.example.quiztaker.QuizContract.*;

/**
 * An activity which lets the user to select what category they want to
 * be tested, how many questions they want to be asked, and how many rounds
 * they want to play.  This activity also has setting and trophy features
 */
public class HomeMenuActivity extends AppCompatActivity
{
    //declare control objects
    private ImageView setting;
    private ImageView trophy;
    private Button start_button;
    private Spinner category_spinner;
    private Spinner rounds_spinner;
    private Spinner questions_spinner;

    //this array stores all categories that we are currently having
    private ArrayList<String> category_list;
    //we have 1 to 5 rounds for each game
    private final String[] ROUNDS = {"1", "2" , "3" , "4", "5"};
    //we have 1 to 10 questions for each round
    private final String[] QUESTIONS = {"1", "2" , "3" , "4", "5", "6", "7", "8", "9", "10"};

    //declare variables
    private String category;
    private String round;
    private String question;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        //create control objects
        setting = (ImageView)findViewById(R.id.setting_image);
        trophy = (ImageView) findViewById(R.id.trophy_image);
        start_button = (Button) findViewById(R.id.start_button);
        category_spinner = (Spinner) findViewById(R.id.category);
        rounds_spinner = (Spinner) findViewById(R.id.round);
        questions_spinner = (Spinner) findViewById(R.id.question);

        //create QuizTakerDatabase object
        QuizTakerDatabase db = new QuizTakerDatabase(this);

        //getting category from the database and store it in category_list
        category_list = new ArrayList<>();
        Cursor cursor = db.getQuestionCategory();

        //check if there is any category is in the database
        if(cursor.getCount() > 0)
        {
            do
            {
                //add category to the list
                category_list.add(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_CATEGORY)));
            }while(cursor.moveToNext());
        }

        //declare array adapter for category spinner
        ArrayAdapter<String> category_adapter;
        //declare array adapter for rounds spinner
        ArrayAdapter<String> rounds_adapter;
        //declare array adapter for questions spinner
        ArrayAdapter<String> questions_adapter;

        //true if device is a tablet, false if device is a phone
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        //if it's a tablet, then make spinner text bigger
        if(isTablet)
        {
            //instantiate category adapter with custom layout
            category_adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_text_size, category_list);
            category_adapter.setDropDownViewResource(R.layout.spinner_text_size);

            //instantiate rounds adapter with custom layout
            rounds_adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_text_size, ROUNDS);
            rounds_adapter.setDropDownViewResource(R.layout.spinner_text_size);

            //instantiate questions adapter with custom layout
            questions_adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_text_size, QUESTIONS);
            questions_adapter.setDropDownViewResource(R.layout.spinner_text_size);
        }
        else
        {
            //instantiate category adapter with android layout
            category_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, category_list);
            category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //instantiate rounds adapter with android layout
            rounds_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, ROUNDS);
            rounds_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //instantiate questions adapter with android layout
            questions_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, QUESTIONS);
            questions_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }

        //display spinner for category, rounds, and questions
        category_spinner.setAdapter(category_adapter);
        rounds_spinner.setAdapter(rounds_adapter);
        questions_spinner.setAdapter(questions_adapter);

        //hide action bar for this splash screen
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //this method is used to listen to setting dialog
        setting.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Reading the value of the intent sent here
                Intent intent = getIntent();
                //getting the value by "user_type" tag
                String user_type = intent.getStringExtra("user_type");

                //send an intent to SettingActivity
                intent = new Intent(getApplication(), SettingActivity.class);
                intent.putExtra("user_type", user_type);
                startActivity(intent);
            }
        });

        //get what category the user wants to be tested on
        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                category = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        //get how many rounds user wants to play
        rounds_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                round = (String) parent.getSelectedItem();
            }

            //a callback method to be invoked when the selection disappears
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        //get how many questions user wants to play per round
        questions_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                question = (String) parent.getSelectedItem();
            }

            //a callback method to be invoked when the selection disappears
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        //this method is used to listen to start quiz event
        start_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //go to quiz activity
                Intent intent = getIntent();
                String in_game_name = intent.getStringExtra("in_game_name");
                String user_type = intent.getStringExtra("user_type");

                intent = new Intent(getApplication(), QuizActivity.class);
                intent.putExtra("user_type", user_type);
                intent.putExtra("in_game_name", in_game_name);
                intent.putExtra("category", category);
                intent.putExtra("num_of_rounds", round);
                intent.putExtra("num_of_questions", question);
                startActivity(intent);
            }
        });

        //this method is used when user clicks on trophy image
        trophy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplication(), LeaderBoardActivity.class);
                startActivity(intent);
            }
        });
    }
}
