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
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

/**
 * An activity that acts as a dialog and has multiple settings for user
 * to change such as in-game name change, password change, help, logout
 */
public class SettingActivity extends AppCompatActivity
{
    //declare control objects
    private Button change_name_button;
    private Button change_password_button;
    private Button create_quiz_button;
    private Button join_quiz_button;
    private Button log_out_button;
    private Button add_question_button;
    private TextView welcome_text;
    private TextView exit;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Reading the value of the intent sent here
        Intent intent = getIntent();

        //getting the value by "user_type" tag
        final String user_type = intent.getStringExtra("user_type");

        //if this is an admin then we use the layout with "add question" feature
        if (user_type.equals("administrator"))
        {
            setContentView(R.layout.activity_setting_admin);
            add_question_button = (Button) findViewById(R.id.add_question_button);

            //adjust the size of the setting dialog
            this.getWindow().setLayout(1300,1650);

            //this method is used to listen to add question event
            //this feature is for admin only
            add_question_button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    //send an intent to AddQuestionActivity
                    Intent intent = new Intent(getApplication(), AddQuestionActivity.class);
                    intent.putExtra("user_type", user_type);
                    startActivity(intent);
                    finish();
                }
            });
        }
        //if not then we use layout without "add question" feature
        else
        {
            setContentView(R.layout.activity_setting);

            //adjust the size of the setting dialog
            this.getWindow().setLayout(1300,1650);
        }

        //creates control objects
        change_name_button = (Button) findViewById(R.id.change_name_button);
        change_password_button = (Button) findViewById(R.id.change_password_button);
        create_quiz_button = (Button) findViewById(R.id.create_group_quiz_button);
        join_quiz_button = (Button) findViewById(R.id.join_group_quiz_button);
        log_out_button = (Button) findViewById(R.id.log_out_button);
        welcome_text = (TextView) findViewById(R.id.welcome_text);
        exit = (TextView) findViewById(R.id.exit);

        final Cursor cursor = LogInActivity.db.getSpecificAccountInfo(user_type, "username");

        //display welcome message in setting dialog
        //in-game name column is at index 3
        welcome_text.setText("Welcome, " + cursor.getString(3) + "!");

        //disable the background, make it untouchable
        this.setFinishOnTouchOutside(false);

        //dismiss this activity if user press "X" button
        exit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        //this method is used to listen to ChangeNameActivity dialog
        change_name_button.setOnClickListener(new View.OnClickListener()
        {
            /**
             * change_name_button clicks sends user to ChangeNameActivity
             */
            public void onClick(View v)
            {
                //send an intent to ChangeNameActivity
                Intent intent = new Intent(getApplication(), ChangeNameActivity.class);
                intent.putExtra("user_type", user_type);
                intent.putExtra("user_name", cursor.getString(0));
                startActivity(intent);
                finish();
            }
        });

        //this method is used to listen to ChangePasswordActivity dialog
        change_password_button.setOnClickListener(new View.OnClickListener()
        {
            /**
             * change_password_button clicks sends user to ChangePasswordActivity
             * @param view
             */
            @Override
            public void onClick(View view)
            {
                //send an intent to ChangeNameActivity
                Intent intent = new Intent(getApplication(), ChangePasswordActivity.class);
                intent.putExtra("user_type", user_type);
                intent.putExtra("user_name", cursor.getString(0));
                intent.putExtra("password", cursor.getString(1));
                startActivity(intent);
                finish();
            }
        });

        //this method is used to listen to Help event
        create_quiz_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Random random = new Random();

                int ranNum = random.nextInt(10);

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setMessage("Your Group ID is " + ranNum);

                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog alert = builder.create();

                //show the alert dialog
                alert.show();
            }
        });

        join_quiz_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);

                //inflate the group id layout
                view = getLayoutInflater().inflate(R.layout.activity_group, null);

                //create control objects
                EditText group_id = (EditText) view.findViewById(R.id.group_id_edit_text);
                Button ok_button = (Button) view.findViewById(R.id.ok_button);

                builder.setView(view);

                //create the dialog
                final AlertDialog alertDialog = builder.create();

                ok_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();

                        //go back to login page
                        finish();
                    }
                });
                //display the confirmation dialog
                alertDialog.show();
            }
        });
        //this method is used to listen to logout event
        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating the dialog builder
                AlertDialog.Builder builder= new AlertDialog.Builder(SettingActivity.this);

                //create SpannableStringBuilder object with the message as an argument
                SpannableStringBuilder title = new SpannableStringBuilder("Logout");
                SpannableStringBuilder message = new SpannableStringBuilder("Are you sure you want to logout?");
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
                        LogInActivity.db.deleteCurrentAccount();

                        Intent intent = new Intent(getApplication(), LogInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                AlertDialog alert = builder.create();

                //show the alert dialog
                alert.show();

                if(isTablet)
                {
                    alert.getWindow().setLayout(1300,500);
                }
            }
        });
    }
}


