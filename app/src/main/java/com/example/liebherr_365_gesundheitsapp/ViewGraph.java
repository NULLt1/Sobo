package com.example.liebherr_365_gesundheitsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpadmin on 26.11.2016.
 */

public class ViewGraph extends AppCompatActivity {
    DBHelperDataSource database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_graph);
        database = new DBHelperDataSource(this);
        database.open();
        showAllListEntries();
        database.close();
    }

    //function showAllListEntries
    private void showAllListEntries() {
        LineChart chart = (LineChart) findViewById(R.id.chart);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        Weightdata[] alldata = database.getAllDataasarray();
        int length = alldata.length;

        List<Entry> entries = new ArrayList<>();

        for (int counter = 0; counter < length; counter++) {
            entries.add(new Entry(alldata[counter].getDays(), alldata[counter].getWeight()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        dataSets.add(showRecommendedWeight(BmiCalculator.getMinRecWeight(),"empfohlenes Minimalgewicht"));
        dataSets.add(showRecommendedWeight(BmiCalculator.getMaxRecWeight(),"empfohlenes Maximalgewicht"));
        dataSets.add(showWeightGoal());

        LineData lineData = new LineData(dataSets);
        chart.setData(lineData);
        chart.invalidate(); // refresh
        Log.d("*** Weight Goal", String.valueOf(SavedSharedPrefrences.getWeightGoal()));
        Log.d("**** Max Date ****", database.getMaxDate());
        Log.d("**** Min Date ****", database.getMinDate());
    }

    private LineDataSet showRecommendedWeight(float weight,String label) {
        Weightdata[] recWeight = database.getRecommendedValues(weight);
        List<Entry> entries = new ArrayList<>();
        int length = recWeight.length;
        for (int counter = 0; counter < length; counter++) {
            entries.add(new Entry(recWeight[counter].getDays(), recWeight[counter].getWeight()));
        }
        LineDataSet dataSet = new LineDataSet(entries, label); // add entries to dataset



        return dataSet;
    }
    private LineDataSet showWeightGoal(){
        Weightdata[] weightGoal = database.getRecommendedValues(SavedSharedPrefrences.getWeightGoal());
        List<Entry> entries = new ArrayList<>();
        int length= weightGoal.length;
        for (int counter = 0; counter < length; counter++) {
            entries.add(new Entry(weightGoal[counter].getDays(), weightGoal[counter].getWeight()));
        }
        LineDataSet dataSet = new LineDataSet(entries, "Gewichtsziel"); // add entries to dataset
        return dataSet;
    }
}
