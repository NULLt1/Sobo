package com.example.liebherr_365_gesundheitsapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.sql.Timestamp;
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

    private void showAllListEntries() {

        LineChart chart = (LineChart) findViewById(R.id.chart);


        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        /*
        Typeface mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        xAxis.setTypeface(mTfLight);
        */
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);


        Weightdata[] alldata = database.getAllDataasarray();
        int length = alldata.length;
        Log.d("ARRAY", String.valueOf(length));

        List<Entry> entries = new ArrayList<Entry>();

        for (Weightdata data : alldata) {

            Timestamp timeStamp = new Timestamp(data.getDateasDate().getTime());

            Log.d("TIMESTAMP", String.valueOf(timeStamp.getTime()));
            // turn your data into Entry objects
            entries.add(new Entry(38, 50));
            entries.add(new Entry(39, 55));
            entries.add(new Entry(40, 75));
            entries.add(new Entry(41, 70));

        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset


        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh
    }
}
