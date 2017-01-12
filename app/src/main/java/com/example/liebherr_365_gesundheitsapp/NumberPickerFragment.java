package com.example.liebherr_365_gesundheitsapp;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import Database.DBHelperDataSourceData;

/**
 * Created by mpadmin on 12.01.2017.
 */

public class NumberPickerFragment extends DialogFragment {
    Context context;
    private DBHelperDataSourceData dataSourceData;
    int day;
    int month;
    int year;

    public NumberPickerFragment(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        Log.d("~~~~~~~~~~~~","~~~~~~~~~~");
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        // get context
        context = getActivity().getApplicationContext();
        // make dialog object
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // get the layout inflater
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate our custom layout for the dialog to a View
        View view = li.inflate(R.layout.numberpicker, null);

        //Intialize integer and aftkomma as numberpicker to use functions
        NumberPicker integer = (NumberPicker) view.findViewById(R.id.integer);
        NumberPicker afterkomma = (NumberPicker) view.findViewById(R.id.afterkomma);

        //Set interger Value 40-100
        integer.setMinValue(40);
        integer.setMaxValue(150);

        //get latestweight and set picker
        dataSourceData = new DBHelperDataSourceData(context);
        dataSourceData.open();
        int lastentry = dataSourceData.getLatestEntry();
        if (lastentry != 0) {
            integer.setValue(lastentry);
        }
        dataSourceData.close();
        //Set afterkomma Value 0-9
        afterkomma.setMinValue(0);
        afterkomma.setMaxValue(9);

        //wrap@ getMinValue() || getMaxValue()
        integer.setWrapSelectorWheel(false);

        // inform the dialog it has a custom View
        builder.setView(view);
        return view;
    }
}
