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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * An activity which check all the sign up requirements whether they are
 * valid or not.  If everything meets the requirement then the account will
 * be setup for the user to use, else inform the user all the fields that
 * are not met the requirements
 */
public class SignUpActivity extends AppCompatActivity
{
    //minimum characters for username and password
    public static final int MIN_CHARS = 6;
    public static final int MAX_CHARS = 20;

    //declare variables for controls
    private Button sign_up;
    private EditText username_edit;
    private EditText password_edit;
    private EditText retype_password_edit;
    private EditText email_edit;
    private EditText in_game_name_edit;

    private LogInActivity logInActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //display up button for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //instantiate control objects
        sign_up = (Button) findViewById(R.id.sign_up_button);
        username_edit = (EditText) findViewById(R.id.username);
        password_edit = (EditText) findViewById(R.id.password);
        retype_password_edit = (EditText) findViewById(R.id.retype_password);
        email_edit = (EditText) findViewById(R.id.email);
        in_game_name_edit = (EditText) findViewById(R.id.in_game_name);

        logInActivity = new LogInActivity();

        //this method is used to listen to sign-up event
        sign_up.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean isAllValid = true;
                boolean isUsernameValid = true;
                boolean isPasswordValid = true;
                boolean isEmailValid = true;
                boolean isInGameNameValid = true;
                String error_msg = "";

                //getting user's input
                String username = username_edit.getText().toString();
                String password = password_edit.getText().toString();
                String retype_password = retype_password_edit.getText().toString();
                String email = email_edit.getText().toString();
                String in_game_name = in_game_name_edit.getText().toString();

                //create text view objects
                TextView username_error_msg = (TextView) findViewById(R.id.username_error_msg);
                TextView password_error_msg = (TextView) findViewById(R.id.password_error_msg);
                TextView retype_password_error_msg = (TextView) findViewById(R.id.retype_password_error_msg);
                TextView email_error_msg = (TextView) findViewById(R.id.email_error_msg);
                TextView in_game_name_error_msg = (TextView) findViewById(R.id.in_game_error_msg);

                Cursor username_cursor = logInActivity.db.getSpecificAccountInfo(username, "username");
                Cursor in_game_name_cursor = logInActivity.db.getSpecificAccountInfo(in_game_name, "in_game_name");

                //check if username is empty or not
                //if empty then set isUsernameValid to false
                if(username.isEmpty())
                {
                    error_msg = "Username field is empty!";
                    isUsernameValid = false;
                }
                //check if username length is at least 6 chars long
                //if not then set isUsernameValid to false
                else if(username.length() < MIN_CHARS)
                {
                    error_msg = "Username must be at least " + MIN_CHARS + " characters long!";
                    isUsernameValid = false;
                }
                else if(username.length() > MAX_CHARS)
                {
                    error_msg = "Username is too large";
                    isUsernameValid = false;
                }
                //check if username is already registered in database
                //if yes then set isUsernameValid to false
                else if(username_cursor.getCount() > 0)
                {
                    error_msg = "This username already exists!";
                    isUsernameValid = false;
                }

                //print username error if have any
                if(!isUsernameValid)
                {
                    printErrorMsg(error_msg, username_error_msg, username_edit);
                    //set isAllValid to false since username doesn't meet the username criteria
                    isAllValid = false;
                }
                //set the username field to default if it meets username criteria
                else
                {
                    username_error_msg.setText("");
                    username_edit.setBackgroundResource(R.drawable.white_rounded_rectangle_border);
                }

                //check if password is empty or not
                //if empty then set isPasswordValid to false
                if(password.isEmpty())
                {
                    error_msg = "Password field is empty!";
                    isPasswordValid = false;
                }
                //check if password length is at least 6 chars long
                //if not then set isPasswordValid to false
                else if(password.length() < MIN_CHARS)
                {
                    error_msg = "Password must be at least " + MIN_CHARS + " characters long!";
                    isPasswordValid = false;
                }
                else if(password.length() > MAX_CHARS)
                {
                    error_msg = "Password is too large";
                    isPasswordValid = false;
                }
                //check if retype password is matched with password
                //if not then set isPasswordValid to false
                else if(!retype_password.equals(password))
                {
                    error_msg = "Retype password does not match!";
                    printErrorMsg(error_msg, retype_password_error_msg, retype_password_edit);
                    //set isAllValid to false since retype password does not match with password field
                    isAllValid = false;
                }
                //set the retype password field to default if it matches password
                else if(retype_password.equals(password))
                {
                    retype_password_error_msg.setText("");
                    retype_password_edit.setBackgroundResource(R.drawable.white_rounded_rectangle_border);
                }

                //print password error if have any
                if(!isPasswordValid)
                {
                    printErrorMsg(error_msg, password_error_msg, password_edit);
                    //set isAllValid to false since password doesn't meet the password criteria
                    isAllValid = false;
                }
                //set the password field to default if it meets password criteria
                else
                {
                    password_error_msg.setText("");
                    password_edit.setBackgroundResource(R.drawable.white_rounded_rectangle_border);
                }

                //check for valid email
                if(!email.equals("") && !isValidEmail(email))
                {
                    error_msg = "This email is invalid!";
                    isEmailValid = false;
                }

                //print email error if have any
                if(!isEmailValid)
                {
                    printErrorMsg(error_msg, email_error_msg, email_edit);
                }
                //set the email field to default if email is valid
                else
                {
                    email_error_msg.setText("");
                    email_edit.setBackgroundResource(R.drawable.white_rounded_rectangle_border);
                }

                //check if in-game name is empty or not
                //if empty then set isInGameNameValid to false
                if(in_game_name.isEmpty())
                {
                    error_msg = "In-game name field is empty!";
                    isInGameNameValid = false;
                }
                else if(in_game_name.length() > MAX_CHARS)
                {
                    error_msg = "In-game name is too large";
                    isInGameNameValid = false;
                }
                //check if in-game name is already registered in database
                //if yes then set isInGameNameValid to false
                else if(in_game_name_cursor.getCount() > 0)
                {
                    error_msg = "This in-game name already exists!";
                    isInGameNameValid = false;
                }

                //print in-game name error if have any
                if(!isInGameNameValid)
                {
                    printErrorMsg(error_msg, in_game_name_error_msg, in_game_name_edit);
                    isAllValid = false;
                }
                //set the in-game name field to default if in-game name is valid
                else
                {
                    in_game_name_error_msg.setText("");
                    in_game_name_edit.setBackgroundResource(R.drawable.white_rounded_rectangle_border);
                }

                //if everything is good then store account into the database
                if(isAllValid)
                {
                    if(email.equals(""))
                    {
                        email = "NULL";
                    }
                    //add this account to database
                    LogInActivity.db.insertAccount(username, password, email, in_game_name);

                    displayConfirmationMsg();
                }
            }
        });
    }

    /**
     * Print different error message depends on what type of error user has
     * @param error_msg error message that will be displayed
     * @param textView the text that will be fed by error_msg
     * @param editText the text which receives input from user
     */
    public void printErrorMsg(String error_msg, TextView textView, EditText editText)
    {
        //print the error to the screen
        textView.setText(error_msg);
        //set the text to red
        textView.setTextColor(getResources().getColor(R.color.red));
        //set border of edit text to red
        editText.setBackgroundResource(R.drawable.red_rounded_rectangle_border);
    }

    /**
     * Display confirmation message that shows account is created
     */
    public void displayConfirmationMsg()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        //inflate the confirmation layout
        View view = getLayoutInflater().inflate(R.layout.confirmation_msg, null);

        //create control objects
        Button ok_button = (Button) view.findViewById(R.id.ok_button);
        TextView confirmation_msg = (TextView) view.findViewById(R.id.confirmation_msg);

        confirmation_msg.setText("Your account has been created successfully.");

        builder.setView(view);

        //create the dialog
        final AlertDialog alertDialog = builder.create();

        //make the background untouchable
        alertDialog.setCanceledOnTouchOutside(false);

        ok_button.setOnClickListener(new View.OnClickListener()
        {
            /**
             * Dismiss the confirmation dialog
             * @param view
             */
            @Override
            public void onClick(View view)
            {
                alertDialog.dismiss();

                //go back to login page
                finish();
            }
        });

        //display the confirmation dialog
        alertDialog.show();
    }

    /**
     *This email validation code is borrowed from https://www.tutorialspoint.com/validate-email-address-in-java
     *This method matches regular expression for email
     */
    public boolean isValidEmail(String email)
    {
        //regular expression of email to which the user's email is to be matched
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        return email.matches(regex);
    }

    /**
     * Performs action if the user selects the Up button.
     *
     * @param item Menu item selected (Up button)
     * @return True if options menu item is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown.

            //go back to previous activity
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
