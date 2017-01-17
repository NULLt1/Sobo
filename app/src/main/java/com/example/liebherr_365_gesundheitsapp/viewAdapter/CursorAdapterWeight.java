package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.R;

import Database.DataQuery;


/**
 * Created by mpadmin on 17.01.2017.
 */

public class CursorAdapterWeight extends CursorAdapter {


    public CursorAdapterWeight(Context context, Cursor cursor) {
        super(context, cursor, 0);
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

        textViewdatum.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataQuery.getColumnDate())));
        String weight = cursor.getString(cursor.getColumnIndexOrThrow(DataQuery.getColumnPhysicalValues()));
        weight += " kg";
        textViewweight.setText(weight);
        String difference = "XXX";
        textViewdifference.setText(difference);
    }
}
