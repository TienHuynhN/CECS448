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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity acts as a dialog which helps the user to change the password
 */
public class ChangePasswordActivity extends AppCompatActivity
{
    //declare control objects
    private TextView exit;
    private TextView old_pass_error_msg;
    private TextView new_pass_error_msg;
    private TextView retype_new_pass_error_msg;
    private EditText old_pass_edit_text;
    private EditText new_pass_edit_text;
    private EditText retype_new_pass_edit_text;
    private Button back_button;
    private Button change_button;

    private String user_type;
    private String key;
    private String password;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //get information from intent
        Intent intent = getIntent();
        user_type = intent.getStringExtra("user_type");
        key = intent.getStringExtra("user_name");
        password = intent.getStringExtra("password");


        //create control objects
        exit = (TextView) findViewById(R.id.exit);
        old_pass_error_msg = (TextView) findViewById(R.id.current_password_error_msg);
        new_pass_error_msg = (TextView) findViewById(R.id.new_password_error_msg);
        retype_new_pass_error_msg = (TextView) findViewById(R.id.retype_new_password_error_msg);
        old_pass_edit_text = (EditText) findViewById(R.id.current_password);
        new_pass_edit_text = (EditText) findViewById(R.id.new_password);
        retype_new_pass_edit_text = (EditText) findViewById(R.id.retype_new_password);
        back_button = (Button) findViewById(R.id.back_button);
        change_button = (Button) findViewById(R.id.change_button);

        //disable the background, make it untouchable
        this.setFinishOnTouchOutside(false);

        //adjust the size of the setting dialog
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

        //listen to change password event
        change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isAllValid = true;

                //get old pass and new pass from user
                String current_pass = old_pass_edit_text.getText().toString();
                String new_pass = new_pass_edit_text.getText().toString();
                String retype_new_pass = retype_new_pass_edit_text.getText().toString();

                //get the password from database
                Cursor cursor = LogInActivity.db.getSpecificAccountInfo(key, "username");

                //print error message if current password field is empty
                if(current_pass.equals(""))
                {
                    old_pass_error_msg.setText("Current password field is empty!");
                    isAllValid = false;
                }
                //print error message if password is not matched with password in database
                else if(!current_pass.equals(cursor.getString(1)))
                {
                    old_pass_error_msg.setText("Incorrect password!");
                    isAllValid = false;
                }
                else
                {
                    old_pass_error_msg.setText("");
                }

                //print error message if new pass word length is less than 6
                if(new_pass.length() < SignUpActivity.MIN_CHARS)
                {
                    new_pass_error_msg.setText("New password must be at least " + SignUpActivity.MIN_CHARS + " characters long!");
                    isAllValid = false;
                }
                else if(new_pass.length() > SignUpActivity.MAX_CHARS)
                {
                    new_pass_error_msg.setText("This new password is too large");
                    isAllValid = false;
                }
                else
                {
                    new_pass_error_msg.setText("");
                }

                //print error message if retype password does not match with new password
                if(!retype_new_pass.equals(new_pass))
                {
                    retype_new_pass_error_msg.setText("Retype password does not match!");
                    isAllValid = false;
                }
                else
                {
                    retype_new_pass_error_msg.setText("");
                }

                if(isAllValid)
                {
                    //update the password if everything is good
                    LogInActivity.db.updateAccount(QuizTakerDatabase.COL_PASSWORD, new_pass, QuizTakerDatabase.COL_USERNAME, key);
                    displayConfirmationMsg();
                }
            }
        });
    }

    /**
     * Display confirmation message that shows new password has been changed
     */
    public void displayConfirmationMsg()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);

        //inflate the confirmation layout
        View view = getLayoutInflater().inflate(R.layout.confirmation_msg, null);

        //create control objects
        Button ok_button = (Button) view.findViewById(R.id.ok_button);
        TextView confirmation_msg = (TextView) view.findViewById(R.id.confirmation_msg);

        confirmation_msg.setText("Your password has been changed successfully.");

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

                //go back to setting dialog
                Intent intent = new Intent(getApplication(), SettingActivity.class);
                intent.putExtra("user_type", user_type);
                startActivity(intent);
                //finish this change name activity
                finish();
            }
        });

        //display the confirmation dialog
        alertDialog.show();
    }
}
