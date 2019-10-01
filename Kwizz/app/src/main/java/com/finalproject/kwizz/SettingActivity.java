package com.finalproject.kwizz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity
{
    private Button change_name_button;
    private Button change_password_button;
    private Button create_group_quiz_button;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        change_name_button = (Button) findViewById(R.id.change_name_button);
        change_password_button = (Button) findViewById(R.id.change_password_button);
        create_group_quiz_button = (Button) findViewById(R.id.create_group_quiz_button);

        change_name_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                buildNameChangeDialog();
            }
        });

        change_password_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                buildPasswordChangeDialog();
            }
        });

        create_group_quiz_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                buildGroupCreationDialog();
            }
        });
    }

    public void buildNameChangeDialog()
    {
        View view = getView(R.layout.name_change);

        final AlertDialog alertDialog = buildDialog(view);

        EditText new_name_edit_text = (EditText) view.findViewById(R.id.new_name_edit_text);
        Button change_button = (Button) view.findViewById(R.id.change_button);

        change_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void buildPasswordChangeDialog()
    {
        View view = getView(R.layout.password_change);

        final AlertDialog alertDialog = buildDialog(view);

        Button change_button = (Button) view.findViewById(R.id.change_button);

        change_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void buildGroupCreationDialog()
    {
        View view = getView(R.layout.create_group);

        final AlertDialog alertDialog = buildDialog(view);

        Button create_button = (Button) view.findViewById(R.id.create_button);

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public AlertDialog buildDialog(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);

        builder.setView(view);

        return builder.create();
    }

    public View getView(int layout_id)
    {
        return getLayoutInflater().inflate(layout_id, null);
    }
}
