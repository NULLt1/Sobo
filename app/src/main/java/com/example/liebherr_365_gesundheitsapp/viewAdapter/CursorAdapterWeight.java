package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.Database.Queries;
import com.example.liebherr_365_gesundheitsapp.R;

public class CursorAdapterWeight extends CursorAdapter {
    private Cursor mCursor;

    public CursorAdapterWeight(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.mCursor=cursor;
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

        textViewdatum.setText(cursor.getString(cursor.getColumnIndexOrThrow(Queries.COLUMN_DATE)));
        String weight = cursor.getString(cursor.getColumnIndexOrThrow(Queries.COLUMN_PHYSICAL_VALUES));
        weight += " kg";
        textViewweight.setText(weight);
        String difference = calcDifference(cursor.getPosition());
        textViewdifference.setText(difference);
    }

    private String calcDifference(int position){
        try {
        Log.d("Cursor position",String.valueOf(position));
        Log.d("MCursor count",String.valueOf(mCursor.getCount()));
        mCursor.moveToPosition(position);
       String currentWeight = mCursor.getString(getCursor().getColumnIndexOrThrow(Queries.COLUMN_PHYSICAL_VALUES));

       float currentWeightFloat = Float.parseFloat(currentWeight);

        mCursor.moveToPosition(position+1);
        String lastWeight= mCursor.getString(getCursor().getColumnIndexOrThrow(Queries.COLUMN_PHYSICAL_VALUES));
        float lastWeightFloat = Float.parseFloat(lastWeight);
        float weightdifference = currentWeightFloat-lastWeightFloat;

        return String.format("%.1f",weightdifference)+" kg";
        }
        catch(Exception e){
            return "";
        }

    }
}
