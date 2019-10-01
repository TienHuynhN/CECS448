package com.finalproject.kwizz;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity
{
    private RecyclerView quiz_name_recycler_view;

    private List<String> quiz_name_list;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_room);

        //hide action bar for this activity
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        quiz_name_recycler_view = (RecyclerView)findViewById(R.id.quiz_name_recycler_view);

        quiz_name_list = new ArrayList<>();

        quiz_name_list.add("quiz name 1");
        quiz_name_list.add("quiz name 2");
        quiz_name_list.add("quiz name 3");
        quiz_name_list.add("quiz name 4");
        quiz_name_list.add("quiz name 5");
        quiz_name_list.add("quiz name 6");
        quiz_name_list.add("quiz name 7");
        quiz_name_list.add("quiz name 8");
        quiz_name_list.add("quiz name 9");
        quiz_name_list.add("quiz name 10");
        quiz_name_list.add("quiz name 11");
        quiz_name_list.add("quiz name 12");
        quiz_name_list.add("quiz name 13");
        quiz_name_list.add("quiz name 14");
        quiz_name_list.add("quiz name 15");
        quiz_name_list.add("quiz name 16");
        quiz_name_list.add("quiz name 17");
        quiz_name_list.add("quiz name 18");

        quiz_name_recycler_view.setAdapter(new QuizNameRecyclerViewAdapter(quiz_name_list));
    }
}
