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
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This class is used as a dialog to add the question from the admin
 */
public class AddQuestionActivity extends AppCompatActivity
{
    //declare control objects
    private Spinner category_spinner;
    private EditText question_edit_text;
    private EditText answer1_edit_text;
    private EditText answer2_edit_text;
    private EditText answer3_edit_text;
    private EditText answer4_edit_text;
    private EditText correct_answer_edit_text;
    private TextView question_error_msg_text;
    private TextView exit;
    private Button back_button;
    private Button add_button;

    //declare variables
    private String category;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String correct_answer;

    private String user_type;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        //get information that is passed through intent
        Intent intent = getIntent();
        user_type = intent.getStringExtra("user_type");

        //create control objects
        category_spinner = (Spinner) findViewById(R.id.category);
        question_edit_text = (EditText) findViewById(R.id.question);
        answer1_edit_text = (EditText) findViewById(R.id.answer_1);
        answer2_edit_text = (EditText) findViewById(R.id.answer_2);
        answer3_edit_text = (EditText) findViewById(R.id.answer_3);
        answer4_edit_text = (EditText) findViewById(R.id.answer_4);
        correct_answer_edit_text = (EditText) findViewById(R.id.correct_answer);
        question_error_msg_text = (TextView) findViewById(R.id.add_question_error_msg) ;
        exit = (TextView) findViewById(R.id.exit);
        back_button = (Button) findViewById(R.id.back_button);
        add_button = (Button) findViewById(R.id.add_button);

        //true if device is a tablet, false if device is a phone
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        //declare array adapter for category spinner
        ArrayAdapter category_adapter;

        //if it's a tablet, then make spinner text bigger
        if(isTablet)
        {
            //instantiate category adapter with custom layout
            category_adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.category_items, R.layout.spinner_text_size);
            category_adapter.setDropDownViewResource(R.layout.spinner_text_size);
        }
        else
        {
            category_adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.category_items, R.layout.color_spinner_layout);
            category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }

        //display spinner for category
        category_spinner.setAdapter(category_adapter);

        //disable the background, make it untouchable
        this.setFinishOnTouchOutside(false);

        //adjust the size of the setting dialog
        this.getWindow().setLayout(1300,2000);

        //dismiss the dialog if user clicks on "x" button
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //get selected subject from the user
        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                //get the item that is selected by the user
                category = (String) parent.getSelectedItem();
            }

            //a callback method to be invoked when the selection disappears
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        //go back to setting dialog if user clicks on back button
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to SettingActivity
                Intent intent = new Intent(getApplication(), SettingActivity.class);
                intent.putExtra("user_type", user_type);
                startActivity(intent);

                //exit the AddQuestionActivity
                finish();
            }
        });

        //this method is used to listen to add question event
        add_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(isQuestionValid())
                {
                    //call this method for confirmation
                    confirmAddQuestion();
                }
            }
        });
    }

    /**
     * This method is used to check if question, answer option list, and correct answer
     * are all valid
     */
    public boolean isQuestionValid()
    {
        boolean isAllValid = true;

        //get question and all options from input
        question = question_edit_text.getText().toString();
        answer1 = answer1_edit_text.getText().toString();
        answer2 = answer2_edit_text.getText().toString();
        answer3 = answer3_edit_text.getText().toString();
        answer4 = answer4_edit_text.getText().toString();
        correct_answer = correct_answer_edit_text.getText().toString();

        //check if question field is filled in
        //if not then display red frame border as an error message
        if(question.equals(""))
        {
            //display red border
            question_edit_text.setBackgroundResource(R.drawable.red_rectangle_border);
            isAllValid = false;
        }
        else if(question.equals(answer1) || question.equals(answer2) || question.equals(answer3) || question.equals(answer4))
        {
            getDuplicateOptionErrorMessage();
            isAllValid = false;
        }
        else
        {
            question_edit_text.setBackgroundResource(R.color.white);
        }

        //check if answer1 field is filled in
        //if not then display red frame border as an error message
        if(answer1.equals(""))
        {
            //display red border
            answer1_edit_text.setBackgroundResource(R.drawable.red_rectangle_border);
            isAllValid = false;
        }
        else if(answer1.equals(answer2) || answer1.equals(answer3) || answer1.equals(answer4))
        {
            //show duplicate error message
            getDuplicateOptionErrorMessage();
            isAllValid = false;
        }
        else
        {
            answer1_edit_text.setBackgroundResource(R.color.white);
        }

        //check if answer2 field is filled in
        //if not then display red frame border as an error message
        if(answer2.equals(""))
        {
            //display red border
            answer2_edit_text.setBackgroundResource(R.drawable.red_rectangle_border);
            isAllValid = false;
        }
        else if(answer2.equals(answer3) || answer2.equals(answer4))
        {
            //show duplicate error message
            getDuplicateOptionErrorMessage();
            isAllValid = false;
        }
        else
        {
            answer2_edit_text.setBackgroundResource(R.color.white);
        }

        //check if answer3 field is filled in
        //if not then display red frame border as an error message
        if(answer3.equals(""))
        {
            //display red border
            answer3_edit_text.setBackgroundResource(R.drawable.red_rectangle_border);
            isAllValid = false;
        }
        else if(answer3.equals(answer4))
        {
            //show duplicate error message
            getDuplicateOptionErrorMessage();
            isAllValid = false;
        }
        else
        {
            answer3_edit_text.setBackgroundResource(R.color.white);
        }

        //check if answer4 field is filled in
        //if not then display red frame border as an error message
        if(answer4.equals(""))
        {
            //display red border
            answer4_edit_text.setBackgroundResource(R.drawable.red_rectangle_border);
            isAllValid = false;
        }
        else
        {
            answer4_edit_text.setBackgroundResource(R.color.white);
        }

        //check if correct_answer field is filled in
        //if not then display red frame border as an error message
        if(correct_answer.equals(""))
        {
            correct_answer_edit_text.setBackgroundResource(R.drawable.red_rectangle_border);
            isAllValid = false;
        }
        else if(!correct_answer.equals(answer1) && !correct_answer.equals(answer2) && !correct_answer.equals(answer3) && !correct_answer.equals(answer4))
        {
            question_error_msg_text.setText("Correct answer is not in the answer option list");
            isAllValid = false;
        }
        else
        {
            correct_answer_edit_text.setBackgroundResource(R.color.white);
            question_error_msg_text.setText("");
        }

        if(isAllValid)
        {
            return true;
        }

        return false;
    }

    /**
     * This method is used to add for final confirmation whether admin still wants to add the question
     */
    public void confirmAddQuestion()
    {
        //true if device is a tablet, false if device is a phone
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        //creating the dialog builder
        AlertDialog.Builder builder= new AlertDialog.Builder(AddQuestionActivity.this);

        //create SpannableStringBuilder object with the message as an argument
        SpannableStringBuilder title = new SpannableStringBuilder("Add Question Confirmation");
        SpannableStringBuilder message = new SpannableStringBuilder("Are you sure you want to add this question?");
        SpannableStringBuilder yes = new SpannableStringBuilder("Yes");
        SpannableStringBuilder cancel = new SpannableStringBuilder("Cancel");

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
            //if user clicks yes then add the question to the database
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                LogInActivity.db.insertQuestion(category, question, answer1, answer2, answer3, answer4, correct_answer);
                displayConfirmationMsg();
            }
        });

        AlertDialog alert = builder.create();

        //show the alert dialog
        alert.show();

        if(isTablet)
        {
            //resize the window
            alert.getWindow().setLayout(1200,600);
        }
    }

    /**
     * Display confirmation message that shows new question has been added to the database
     */
    public void displayConfirmationMsg()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddQuestionActivity.this);
        //inflate the confirmation layout
        View view = getLayoutInflater().inflate(R.layout.confirmation_msg, null);

        //create control objects
        Button ok_button = (Button) view.findViewById(R.id.ok_button);
        TextView confirmation_msg = (TextView) view.findViewById(R.id.confirmation_msg);

        confirmation_msg.setText("New question has been added successfully.");

        builder.setView(view);

        //create the dialog
        final AlertDialog alertDialog = builder.create();

        //make the background untouchable
        alertDialog.setCanceledOnTouchOutside(false);

        ok_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Dismiss the confirmation dialog
             * @param view
             */
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

                //go back to AddQuestionActivity
                Intent intent = new Intent(getApplication(), AddQuestionActivity.class);
                intent.putExtra("user_type", user_type);
                startActivity(intent);
                finish();
            }
        });

        //display the confirmation dialog
        alertDialog.show();
    }

    /**
     * This method is used to display error message when 2 or more options are duplicated
     */
    public void getDuplicateOptionErrorMessage()
    {
        //create SpannableStringBuilder object with the message as an argument
        SpannableStringBuilder message = new SpannableStringBuilder("Duplicate texts are not allowed!");

        //true if device is a tablet, false if device is a phone
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        //if it's a tablet, then make toast message bigger
        if(isTablet)
        {
            //span the text size
            message.setSpan(new RelativeSizeSpan(2.1f), 0, message.length(), 0);
        }

        //send a toast message on screen
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
