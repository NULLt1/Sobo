package com.example.liebherr_365_gesundheitsapp;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import Database.DBHelperDataSourceData;
import Database.Data;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by mpadmin on 12.01.2017.
 */

public class NumberPickerFragment extends DialogFragment {
    Context context;
    private DBHelperDataSourceData dataSourceData;
    int day;
    int month;
    int year;
    int integervalue;
    int afterkommavalue;

    public NumberPickerFragment() {

    }

    //TODO: getArguments() !?!??!
    public NumberPickerFragment(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        Log.d("~~~~~~~~~~~~", "~~~~~~~~~~");
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

        // setOnValueChangedListener on NumberPucker integer
        integer.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                integervalue = newValue;
            }
        });

        // setOnValueChangedListener on NumberPucker integer
        afterkomma.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                afterkommavalue = newValue;
            }
        });

        //Set interger Value 40-100
        integer.setMinValue(40);
        integer.setMaxValue(150);

        //get latestweight and set picker
        dataSourceData = new DBHelperDataSourceData(context);
        dataSourceData.open();
        int lastentry = dataSourceData.getLatestEntry();
        if (lastentry != 0) {
            integer.setValue(lastentry);
            integervalue = lastentry;
        } else {
            integervalue = integer.getValue();
        }
        dataSourceData.close();
        //Set afterkomma Value 0-9
        afterkomma.setMinValue(0);
        afterkomma.setMaxValue(9);

        //wrap@ getMinValue() || getMaxValue()
        integer.setWrapSelectorWheel(false);

        // inform the dialog it has a custom View
        builder.setView(view);

        // setOnClickListener on Button save
        Button button = (Button) view.findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // exclude years smaller then 2016
                if (year < 2016) {
                    //TODO REALISIEREN
                } else {
                    //TODO ABFRAGE DATEALREADYEXISTS
                    year = year - 1900;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String formateddate = sdf.format(new Date(year, month, day));

                    // call function integertofloat
                    float weight = integertofloat(integervalue, afterkommavalue);

                    //type declaration
                    String type = "kg";

                    // new weightdateobject with values
                    String modulweight = "ModulWeight";
                    Data wd = new Data(modulweight, formateddate, weight, type);

                    // new DBHelperDataSource
                    dataSourceData = new DBHelperDataSourceData(context);
                    dataSourceData.open();

                    // call function datealreadysaved and react on result
                    //boolean datealreadyexisting = dataSourceData.datealreadysaved(wd);
                    //Log.d("result", String.valueOf(datealreadyexisting));

                    Log.d("SAVED", String.valueOf(modulweight));
                    Log.d("SAVED", String.valueOf(formateddate));
                    Log.d("SAVED", String.valueOf(weight));
                    Log.d("SAVED", String.valueOf(type));

                    dataSourceData.insertdata(wd);

                    Log.d("closesql", "<DATA>Die Datenquelle wird geschlossen.<DATA>");
                    dataSourceData.close();

                    //close NumberPickerFragment
                    getDialog().dismiss();
                }
            }
        });
        return view;
    }

    //function integer values -> float integervalue,afterkommavalue
    public float integertofloat(int integervalue, int afterkommavalue) {
        float result = 0;
        result += (float) integervalue;
        result += ((float) afterkommavalue / 10);
        return result;
    }
}