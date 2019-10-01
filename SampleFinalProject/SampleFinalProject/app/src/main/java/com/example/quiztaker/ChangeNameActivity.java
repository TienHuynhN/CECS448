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
 * This activity acts as a dialog which receives the new in-game name from
 * the user and check it with the database if the new name is already taken.
 * If new name is not in the database, then display a confirmation dialog,
 * else ask user to enter other name
 */
public class ChangeNameActivity extends AppCompatActivity
{
    //declare control objects
    private TextView exit;
    private TextView name_error_msg;
    private EditText new_name_text;
    private Button back_button;
    private Button change_button;

    private String user_type;
    private String key;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        //get information from intent
        Intent intent = getIntent();
        user_type = intent.getStringExtra("user_type");
        key = intent.getStringExtra("user_name");

        //create control objects
        exit = (TextView) findViewById(R.id.exit);
        name_error_msg = (TextView) findViewById(R.id.change_name_error_msg);
        new_name_text = (EditText) findViewById(R.id.new_in_game_name);
        back_button = (Button) findViewById(R.id.back_button);
        change_button = (Button) findViewById(R.id.change_button);

        //disable the background, make it untouchable
        this.setFinishOnTouchOutside(false);

        //resize the change name dialog
        this.getWindow().setLayout(1300, 1100);

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

        //listen to change name event
        change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_name = new_name_text.getText().toString();

                Cursor cursor = LogInActivity.db.getSpecificAccountInfo(new_name, "in_game_name");

                //if the new in-game name already exists then inform the
                // user so they can choose another name
                if (new_name.equals("")) {
                    name_error_msg.setText("In-game name field is empty!");
                }
                else if(new_name.length() > SignUpActivity.MAX_CHARS)
                {
                    name_error_msg.setText("In-game name is too large");
                }
                else if (cursor.getCount() > 0) {
                    name_error_msg.setText("This in-game name already exists!");
                }

                //if new name is not in database then update it with old name
                else {
                    LogInActivity.db.updateAccount(QuizTakerDatabase.COL_IN_GAME_NAME, new_name, QuizTakerDatabase.COL_USERNAME, key);
                    name_error_msg.setText("");
                    displayConfirmationMsg();
                }
            }
        });
    }

    /**
     * Display confirmation message that shows new name has been updated
     */
    public void displayConfirmationMsg()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangeNameActivity.this);

        //inflate the confirmation layout
        View view = getLayoutInflater().inflate(R.layout.confirmation_msg, null);

        //create control objects
        Button ok_button = (Button) view.findViewById(R.id.ok_button);
        TextView confirmation_msg = (TextView) view.findViewById(R.id.confirmation_msg);

        confirmation_msg.setText("Your in-game name has been changed successfully.");

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


