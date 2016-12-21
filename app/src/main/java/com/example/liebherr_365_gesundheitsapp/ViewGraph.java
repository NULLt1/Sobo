package com.example.liebherr_365_gesundheitsapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
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

import Database.DBHelperDataSourceData;
import Database.Data;

public class ViewGraph extends AppCompatActivity {
    DBHelperDataSourceData databaseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_graph);
        databaseData = new DBHelperDataSourceData(this);
        databaseData.open();
        showAllListEntries();
        databaseData.close();
    }

    //function showAllListEntries
    private void showAllListEntries() {
        LineChart chart = (LineChart) findViewById(R.id.chart);
        chart.setScaleEnabled(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setTextSize(12f);
        xAxis.setValueFormatter(xAxisFormatter);

        YAxis yAxisleft = chart.getAxisLeft();
        yAxisleft.setTextSize(12f); // set the text size
        //yAxisleft.setAxisMinimum(40f); // start at 40
        yAxisleft.setGranularity(1f); // only intervals of 1 kg

        YAxis yAxisright = chart.getAxisRight();
        yAxisright.setDrawLabels(false);
        //yAxisright.setAxisMinimum(40f); // start at 40
        yAxisright.setGranularity(1f); // only intervals of 1 kg

        Data[] alldata = databaseData.getAllDataasarray();
        int length = alldata.length;

        List<Entry> entries = new ArrayList<>();

        for (int counter = 0; counter < length; counter++) {
            entries.add(new Entry(alldata[counter].getDays(), alldata[counter].getPhysicalvalues()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Gewicht"); // add entries to dataset
        dataSet.setColor(Color.BLACK);
        dataSet.setLineWidth(2f);
        List<ILineDataSet> dataSets = new ArrayList<>();

        //Style weight line
        dataSet.setDrawValues(false);
        dataSet.setCircleRadius(3f);
        dataSet.setDrawCircleHole(false);
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setColor(Color.BLACK);

        // add Data to dataSets
        dataSets.add(dataSet);

        //style legend
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(15);
        legend.setTextColor(Color.GRAY);
        legend.setForm(Legend.LegendForm.LINE);

        LineData lineData = new LineData(dataSets);

        // hide description
        chart.setDescription(null);

        // disable highlighting
        chart.setHighlightPerTapEnabled(false);

        chart.setData(lineData);
        chart.invalidate(); // refresh
        drawWeightGoal(chart);
        drawZone(chart);

    }

    private void drawWeightGoal(LineChart chart) {

        float weightGoal = SavedSharedPrefrences.getWeightGoal();

        LimitLine ll = new LimitLine(weightGoal, "");
        ll.setLineColor(Color.RED);
        ll.setLineWidth(2f);

        chart.getAxisLeft().setDrawLimitLinesBehindData(false);
        chart.getAxisLeft().addLimitLine(ll);
    }

    private void drawZone(LineChart chart) {

        float lowerLimit = BmiCalculator.getMinRecWeight();
        float upperLimit = BmiCalculator.getMaxRecWeight();
        float increment = ((upperLimit - lowerLimit) / 1000);


        for (int i = 0; i < 1000; i++) {
            LimitLine ll = new LimitLine(lowerLimit, "");
            ll.setLineColor(ContextCompat.getColor(this, R.color.colorLightGreen));
            ll.setLineWidth(10f);

            chart.getAxisLeft().setDrawLimitLinesBehindData(true);
            chart.getAxisLeft().addLimitLine(ll);
            lowerLimit = lowerLimit + increment;
        }
    }
}
