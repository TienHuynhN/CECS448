package com.finalproject.kwizz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    private Button play_game_button;
    private Button join_group_button;
    private Button setting_button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //hide action bar for this activity
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        play_game_button = (Button) findViewById(R.id.play_game_button);
        join_group_button = (Button) findViewById(R.id.join_group_button);
        setting_button = (Button) findViewById(R.id.setting_button);

        play_game_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplication(), QuizCategory.class);
                startActivity(intent);
            }
        });

        join_group_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                buildJoinGroupDialog();
            }
        });

        setting_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplication(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void buildJoinGroupDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        View view = getLayoutInflater().inflate(R.layout.join_group, null);

        EditText group_id_edit_text = (EditText) view.findViewById(R.id.group_name_edit_text);
        Button join_button = (Button) view.findViewById(R.id.join_button);

        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        join_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplication(), GroupActivity.class);
                startActivity(intent);

                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
