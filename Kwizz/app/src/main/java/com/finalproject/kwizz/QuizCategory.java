package com.finalproject.kwizz;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class QuizCategory extends AppCompatActivity
{
    private RecyclerView quiz_category_recycler_view;

    private List<QuizCategoryContent> quiz_category_list;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_category);

        quiz_category_recycler_view = (RecyclerView) findViewById(R.id.quiz_category_recycler_view);

        quiz_category_list = new ArrayList<>();

        KwizzDatabase db = new KwizzDatabase(QuizCategory.this);

        Cursor cursor = db.getQuizCategory();

        if(cursor.getCount() > 0)
        {
            do
            {
                quiz_category_list.add(new QuizCategoryContent(cursor.getString(cursor.getColumnIndex(KwizzDatabase.COL_CATEGORY_NAME)), cursor.getInt(cursor.getColumnIndex(KwizzDatabase.COL_ICON))));
            }while(cursor.moveToNext());
        }

        quiz_category_recycler_view.setAdapter(new QuizCategoryRecyclerViewAdapter(quiz_category_list));
    }
}
