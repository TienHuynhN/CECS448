package com.finalproject.kwizz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizCategoryRecyclerViewAdapter extends RecyclerView.Adapter<QuizCategoryRecyclerViewAdapter.QuizCategoryViewHolder>
{
    private final List<QuizCategoryContent> quiz_category_list;

    class QuizCategoryViewHolder extends RecyclerView.ViewHolder
    {
        final View view;
        final ImageView category_image;
        final TextView category_name;
        QuizCategoryContent quiz_category_content;

        QuizCategoryViewHolder(View view)
        {
            super(view);
            this.view = view;
            category_image = (ImageView) view.findViewById(R.id.category_image);
            category_name = (TextView) view.findViewById(R.id.category_name);
        }
    }

    public QuizCategoryRecyclerViewAdapter(List<QuizCategoryContent> quiz_category_list)
    {
        this.quiz_category_list = quiz_category_list;
    }

    public QuizCategoryViewHolder onCreateViewHolder(ViewGroup parent, int view_type)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_category_content, parent, false);

        return new QuizCategoryViewHolder(view);
    }

    public void onBindViewHolder(final QuizCategoryViewHolder holder, final int position)
    {
        holder.quiz_category_content = quiz_category_list.get(position);
        holder.category_image.setImageResource(quiz_category_list.get(position).getCategoryImage());
        holder.category_name.setText(quiz_category_list.get(position).getCategoryName());

        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Context context = view.getContext();

                Intent intent = new Intent(context, QuizActivity.class);
                intent.putExtra(QuizCategoryContent.quiz_key, quiz_category_list.get(position).getCategoryName());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    public int getItemCount()
    {
        return quiz_category_list.size();
    }
}
