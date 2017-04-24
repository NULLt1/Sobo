package com.example.liebherr_365_gesundheitsapp.ModulWeight;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.R;

import com.example.liebherr_365_gesundheitsapp.Database.DataQuery;


/**
 * Created by mpadmin on 17.01.2017.
 */

public class CursorAdapterWeight extends CursorAdapter {
    private Cursor mCursor;

    public CursorAdapterWeight(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.mCursor = cursor;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.weight_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewdatum = (TextView) view.findViewById(R.id.datum);
        TextView textViewweight = (TextView) view.findViewById(R.id.weight);
        TextView textViewdifference = (TextView) view.findViewById(R.id.difference);

        //set text datum
        String datum = formateDatum(cursor.getPosition());
        textViewdatum.setText(datum);

        //set text weight
        String weight = cursor.getString(cursor.getColumnIndexOrThrow(DataQuery.getColumnPhysicalValues()));
        weight += " kg";
        textViewweight.setText(weight);

        //set text difference
        String difference = calcDifference(cursor.getPosition());
        textViewdifference.setText(difference);
    }

    // funtion formateDatum
    private String formateDatum(int position) {
        mCursor.moveToPosition(position);
        String currentWeight = mCursor.getString(getCursor().getColumnIndexOrThrow(DataQuery.getColumnDate()));
        String year = currentWeight.substring(0, 4);
        String month = currentWeight.substring(5, 7);
        String day = currentWeight.substring(8, 10);
        currentWeight = day + "." + month + "." + year;
        return currentWeight;
    }

    //function calcDifference
    private String calcDifference(int position) {
        try {
            Log.d("Cursor position", String.valueOf(position));
            Log.d("MCursor count", String.valueOf(mCursor.getCount()));
            mCursor.moveToPosition(position);
            String currentWeight = mCursor.getString(getCursor().getColumnIndexOrThrow(DataQuery.getColumnPhysicalValues()));

            float currentWeightFloat = Float.parseFloat(currentWeight);

            mCursor.moveToPosition(position + 1);
            String lastWeight = mCursor.getString(getCursor().getColumnIndexOrThrow(DataQuery.getColumnPhysicalValues()));
            float lastWeightFloat = Float.parseFloat(lastWeight);
            float weightdifference = currentWeightFloat - lastWeightFloat;

            weightdifference = roundfloat(weightdifference);
            return String.format("%s kg", weightdifference);
        } catch (Exception e) {
            return "";
        }

    }

    // function roundfloat
    private float roundfloat(float inputfloat) {
        float roundedfloat = 0;
        inputfloat += 0.05;
        inputfloat = (int) (inputfloat * 10);
        roundedfloat = inputfloat / 10;
        return roundedfloat;
    }
}
