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
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * An activity which receive user's input for username and password
 * and check if this account is already register; if not, then display
 * a toast message saying that the account is invalid.  The user can play
 * as guest by clicking on "Play as guest", it will take the user to main
 * menu page; or create an account by clicking on "Sign up", it will
 * take the user to sign up page
 */
public class LogInActivity extends AppCompatActivity
{
    //declare objects for controls
    private EditText username_edit_text;
    private EditText password_edit_text;
    private TextView sign_up_text;
    private Button login_button;

    public static QuizTakerDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //create control objects
        username_edit_text = (EditText) findViewById(R.id.username_edit);
        password_edit_text = (EditText) findViewById(R.id.password_edit);
        sign_up_text = (TextView) findViewById(R.id.sign_up_text);
        login_button = (Button) findViewById(R.id.login_button);

        //hide action bar for this activity
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //create an instance of account database
        db = new QuizTakerDatabase(this);

        cursor = db.getCurrentAccount();
        if(cursor.getCount() > 0)
        {
            String username = cursor.getString(cursor.getColumnIndex(QuizTakerDatabase.COL_USERNAME));
            //a cursor which is pointing to the matched information
            cursor = db.getSpecificAccountInfo(username, "username");
            Intent intent = new Intent(getApplication(), HomeMenuActivity.class);
            intent.putExtra("user_type", cursor.getString(cursor.getColumnIndex(QuizTakerDatabase.COL_USERNAME)));
            intent.putExtra("in_game_name", cursor.getString(cursor.getColumnIndex(QuizTakerDatabase.COL_IN_GAME_NAME)));
            startActivity(intent);
        }

        //this method is used to listen login event
        login_button.setOnClickListener(new View.OnClickListener()
        {
            /**
             * If user clicks on login button then we have to check
             * for valid account.  If account is valid then we go to
             * main menu page, else we send an error toast message
             */
            public void onClick(View v)
            {
                String username = username_edit_text.getText().toString();
                String password = password_edit_text.getText().toString();

                //check if account is valid
                if(isValidAccount(username, password))
                {
                    //store current account to database so user does not have to login every time he/she opens the app
                    db.insertCurrentAccount(cursor.getString(cursor.getColumnIndex(QuizTakerDatabase.COL_USERNAME)));

                    //if account is valid then send an intent to MainMenuActivity
                    Intent intent = new Intent(getApplication(), HomeMenuActivity.class);
                    intent.putExtra("user_type", cursor.getString(0));
                    intent.putExtra("in_game_name", cursor.getString(3));
                    startActivity(intent);
                }
                else
                {
                    //create SpannableStringBuilder object with the message as an argument
                    SpannableStringBuilder message = new SpannableStringBuilder("Invalid Account!");

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

                    //clear the password field
                    password_edit_text.getText().clear();
                }
            }
        });

        //this method is used to listen to sign up event
        sign_up_text.setOnClickListener(new View.OnClickListener()
        {
            /**
             * If user clicks on sign up then we will go to sign up page
             */
            public void onClick(View v)
            {
                //send an intent to SignUpActivity
                Intent intent = new Intent(getApplication(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * This function is used to check if the account is already
     * registered or not
     *
     * @param username account's username
     * @param password account's password
     *
     * @return return true if account is already registered, else
     * return false
     */
    public boolean isValidAccount(String username, String password)
    {
        //a cursor which is pointing to the matched information
        cursor = db.getSpecificAccountInfo(username, "username");

        //check if there is any username in database that matches with username that user provides
        if(cursor.getCount() > 0)
        {
            //check if the password user provides is the same as the password in the database
            //password column is at index 1
            if(cursor.getString(1).equals(password))
            {
                return true;
            }
        }
        return false;
    }
}
