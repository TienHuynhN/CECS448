package com.finalproject.kwizz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizNameRecyclerViewAdapter extends RecyclerView.Adapter<QuizNameRecyclerViewAdapter.QuizNameViewHolder>
{
    private final List<String> quiz_name_list;

    class QuizNameViewHolder extends RecyclerView.ViewHolder
    {
        final TextView quiz_name_text;

        QuizNameViewHolder(View view)
        {
            super(view);
            quiz_name_text = (TextView) view.findViewById(R.id.quiz_name_text);
        }
    }

    public QuizNameRecyclerViewAdapter(List<String> quiz_name_list)
    {
        this.quiz_name_list = quiz_name_list;
    }

    public QuizNameRecyclerViewAdapter.QuizNameViewHolder onCreateViewHolder(ViewGroup parent, int view_type)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_name, parent, false);

        return new QuizNameRecyclerViewAdapter.QuizNameViewHolder(view);
    }

    public void onBindViewHolder(final QuizNameRecyclerViewAdapter.QuizNameViewHolder holder, final int position)
    {
        holder.quiz_name_text.setText(quiz_name_list.get(position));

    }

    public int getItemCount()
    {
        return quiz_name_list.size();
    }
}
