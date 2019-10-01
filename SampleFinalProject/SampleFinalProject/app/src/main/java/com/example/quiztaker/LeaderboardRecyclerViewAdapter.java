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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * This class is a recycler view adapter
 */
public class LeaderboardRecyclerViewAdapter extends RecyclerView.Adapter<LeaderboardRecyclerViewAdapter.LeaderboardViewHolder>
{
    private final List<Leaderboard> leaderboards;

    //creates a new custom view holder to hold the view to display in the RecyclerView
    class LeaderboardViewHolder extends RecyclerView.ViewHolder
    {
        final TextView rank;
        final TextView name;
        final TextView scores;
        Leaderboard leaderboard;

        LeaderboardViewHolder(View view)
        {
            super(view);
            rank = (TextView) view.findViewById(R.id.rank);
            name = (TextView) view.findViewById(R.id.name);
            scores = (TextView) view.findViewById(R.id.trophy);
        }
    }

    /**
     * Overloaded constructor
     */
    public LeaderboardRecyclerViewAdapter(List<Leaderboard> leaderboard)
    {
        this.leaderboards = leaderboard;
    }

    /**
     * this method is used to inflate a layout for leaderboard list and return a new view holder that contains it
     */
    public LeaderboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //inflate item view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_leaderboard_content, parent, false);

        //return view
        return new LeaderboardViewHolder(view);
    }

    /**
     * This method is used to set the contents of the leaderboard at a given position in recycler view
     * @param holder
     * @param position
     */
    public void onBindViewHolder(final LeaderboardViewHolder holder, int position)
    {
        //getting data for a certain position
        holder.leaderboard = leaderboards.get(position);
        //add rank to the leaderboard view holder
        holder.rank.setText(String.valueOf(position + 1));
        //add name to the leaderboard view holder
        holder.name.setText(leaderboards.get(position).getInGameName());
        //add scores to the leaderboard view holder
        holder.scores.setText(String.valueOf(leaderboards.get(position).getScores()));
    }

    /**
     * Get number of players in the list
     */
    public int getItemCount()
    {
        return leaderboards.size();
    }
}
