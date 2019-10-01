package com.finalproject.kwizz;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity
{
    private EditText username_edit_text;
    private EditText password_edit_text;
    private EditText confirm_password_edit_text;
    private EditText email_edit_text;
    private EditText name_edit_text;
    private Button sign_up_button;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username_edit_text = (EditText) findViewById(R.id.username_edit_text);
        password_edit_text = (EditText) findViewById(R.id.password_edit_text);
        confirm_password_edit_text = (EditText) findViewById(R.id.confirm_password_edit_text);
        email_edit_text = (EditText) findViewById(R.id.email_edit_text);
        name_edit_text = (EditText) findViewById(R.id.name_edit_text);
        sign_up_button = (Button) findViewById(R.id.sign_up_button);

        sign_up_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                KwizzDatabase db = new KwizzDatabase(SignupActivity.this);

                boolean isAllValid = true;

                String username = username_edit_text.getText().toString();
                String password = password_edit_text.getText().toString();
                String confirm_password = confirm_password_edit_text.getText().toString();
                String email = email_edit_text.getText().toString();
                String name = name_edit_text.getText().toString();

                TextView username_error_msg_text = (TextView) findViewById(R.id.username_error_msg_text);
                TextView password_error_msg_text = (TextView) findViewById(R.id.password_error_msg_text);
                TextView confirm_password_error_msg_text = (TextView) findViewById(R.id.confirm_password_error_msg_text);
                TextView email_error_msg_text = (TextView) findViewById(R.id.email_error_msg_text);
                TextView name_error_msg = (TextView) findViewById(R.id.name_error_msg);

                if(username.isEmpty() || username.length() <= 3)
                {
                    username_error_msg_text.setText("Username must be more than 3 characters.");
                    isAllValid = false;
                }
                else if(db.getSpecificAccount(KwizzDatabase.COL_USERNAME, username).getCount() > 0)
                {
                    username_error_msg_text.setText("This username already exists.");
                    isAllValid = false;
                }
                else
                {
                    username_error_msg_text.setText("");
                }

                if(password.isEmpty() || password.length() <= 3)
                {
                    password_error_msg_text.setText("Password must be more than 3 characters.");
                    isAllValid = false;
                }
                else
                {
                    password_error_msg_text.setText("");
                }

                if(confirm_password.isEmpty() || !confirm_password.equals(password))
                {
                    confirm_password_error_msg_text.setText("Does not match password field.");
                    isAllValid = false;
                }
                else
                {
                    confirm_password_error_msg_text.setText("");
                }

                if(!email.isEmpty() && !isValidEmail(email))
                {
                    email_error_msg_text.setText("Invalid email.");
                    isAllValid = false;
                }
                else
                {
                    email_error_msg_text.setText("");
                }

                if(name.isEmpty() || name.length() <= 3)
                {
                    name_error_msg.setText("Name must be more than 3 characters.");
                    isAllValid = false;
                }
                else if(db.getSpecificAccount(KwizzDatabase.COL_NAME, name).getCount() > 0)
                {
                    name_error_msg.setText("This name already exists.");
                    isAllValid = false;
                }
                else
                {
                    name_error_msg.setText("");
                }

                if(isAllValid)
                {
                    db.insertAccount(username, password, email, name);

                    finish();
                }
            }
        });
    }

    public boolean isValidEmail(String email)
    {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        return email.matches(regex);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
