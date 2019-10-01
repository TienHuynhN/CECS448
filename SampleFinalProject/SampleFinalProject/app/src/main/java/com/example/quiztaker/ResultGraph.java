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

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to display the graph
 */
public class ResultGraph extends AppCompatActivity
{
    //declare control object
    private TextView exit;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_graph);

        //create control object
        exit = (TextView) findViewById(R.id.exit);

        //get the column chart
        Cartesian bar_graph = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        //store rounds and scores for each round
        for(int round: QuizActivity.rounds_questions.keySet())
        {
            data.add(new ValueDataEntry("" + round, QuizActivity.rounds_questions.get(round)));
        }

        System.out.println(QuizActivity.rounds_questions);

        //set the x and y title
        bar_graph.xAxis(0).title("Round");
        bar_graph.yAxis(0).title("Number of correct answers");
        bar_graph.data(data);

        AnyChartView anyCharView = (AnyChartView) findViewById(R.id.result_graph);
        anyCharView.setChart(bar_graph);

        //dismiss the dialog if user clicks on "x" button
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
