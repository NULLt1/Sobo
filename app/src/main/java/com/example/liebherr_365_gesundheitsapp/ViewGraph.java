package com.example.liebherr_365_gesundheitsapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
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

        YAxis yAxisleft = chart.getAxisLeft();
        yAxisleft.setTextSize(12f); // set the text size
        yAxisleft.setAxisMinimum(40f); // start at 40S
        yAxisleft.setGranularity(1f); // only intervals of 1 kg

        YAxis yAxisright = chart.getAxisRight();
        yAxisright.setDrawLabels(false);
        yAxisright.setAxisMinimum(40f); // start at zero
        yAxisright.setGranularity(1f); // only intervals of 1 kg

        Weightdata[] alldata = database.getAllDataasarray();
        int length = alldata.length;

        List<Entry> entries = new ArrayList<>();

        for (int counter = 0; counter < length; counter++) {
            entries.add(new Entry(alldata[counter].getDays(), alldata[counter].getWeight()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(Color.BLACK);
        dataSet.setLineWidth(2f);
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        LineData lineData = new LineData(dataSets);
        chart.setData(lineData);
        chart.invalidate(); // refresh

        drawZone(chart);
        showWeightGoal(chart);
    }

    private void showWeightGoal(LineChart chart) {
        float weightGoal=SavedSharedPrefrences.getWeightGoal();


        LimitLine llWeightGoal = new LimitLine(weightGoal,"Zielgewicht");
        llWeightGoal.setLineColor(Color.RED);
        llWeightGoal.setTextColor(Color.BLACK);
        llWeightGoal.setLineWidth(2f);
        chart.getAxisLeft().setDrawLimitLinesBehindData(true);
        chart.getAxisLeft().addLimitLine(llWeightGoal);

    }

    private void drawZone(LineChart chart) {

        float lowerLimit = BmiCalculator.getMinRecWeight();
        float upperLimit = BmiCalculator.getMaxRecWeight();
        float increment=((upperLimit-lowerLimit)/100);


        for(int i = 0; i<100;i++) {
            LimitLine ll = new LimitLine(lowerLimit, "");
            ll.setLineColor(ContextCompat.getColor(this,R.color.colorLightGreen));
            ll.setLineWidth(10f);

            chart.getAxisLeft().setDrawLimitLinesBehindData(true);
            chart.getAxisLeft().addLimitLine(ll);
            lowerLimit= lowerLimit+increment;
        }
    }
}
