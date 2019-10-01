package com.finalproject.kwizz;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity
{
    private EditText username_edit_text;
    private EditText password_edit_text;
    private Button login_button;
    private TextView sign_up_text;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //hide action bar for this activity
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        username_edit_text = (EditText) findViewById(R.id.username_edit_text);
        password_edit_text = (EditText) findViewById(R.id.password_edit_text);
        login_button = (Button) findViewById(R.id.login_button);
        sign_up_text = (TextView) findViewById(R.id.sign_up_text);

        login_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                KwizzDatabase db = new KwizzDatabase(LoginActivity.this);

                String username = username_edit_text.getText().toString();
                String password = password_edit_text.getText().toString();

                if(isValidAccount(username, password))
                {
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid account!", Toast.LENGTH_SHORT).show();

                    password_edit_text.getText().clear();
                }
            }
        });

        sign_up_text.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplication(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isValidAccount(String username, String password)
    {
        KwizzDatabase db = new KwizzDatabase(LoginActivity.this);

        Cursor cursor = db.getSpecificAccount(KwizzDatabase.COL_USERNAME, username);

        if(cursor.getCount() > 0 && cursor.getString(cursor.getColumnIndex(KwizzDatabase.COL_PASSWORD)).equals(password))
        {
            return true;
        }

        return false;
    }
}
