package com.example.liebherr_365_gesundheitsapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jan on 16.11.2016.
 */

public class WeightdataArrayAdapter extends ArrayAdapter<Weightdata> {

    private Context context;
    private List<Weightdata> weightdataList;

    public WeightdataArrayAdapter(Context context, int resource, List<Weightdata> objects) {
        super(context, resource, objects);

        this.context = context;
        this.weightdataList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //anzuzeigende Reihe
        Weightdata weightdata = weightdataList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_view_row, null);

        TextView textViewWeight = (TextView) view.findViewById(R.id.textViewWeight);
        TextView textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        TextView textViewBmi = (TextView) view.findViewById(R.id.textViewBmi);


        String dateString = weightdata.getDate();

        SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMANY);

        try {
            Date date = dbDateFormat.parse(dateString);
            dateString = shortDateFormat.format(date);
            textViewDate.setText(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        textViewDate.setText(dateString);
        textViewWeight.setText(String.format("%.01f", weightdata.getWeight()));
        textViewBmi.setText(String.format("%.01f", weightdata.getBmi()));

        return view;
    }


}
