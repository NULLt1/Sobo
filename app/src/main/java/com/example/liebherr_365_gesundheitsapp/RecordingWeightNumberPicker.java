package com.example.liebherr_365_gesundheitsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.liebherr_365_gesundheitsapp.Database.DataSourceData;
import com.example.liebherr_365_gesundheitsapp.Database.Data;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class RecordingWeightNumberPicker extends DialogFragment {
    Context context;
    private DataSourceData dataSourceData;
    int integervalue;
    int afterkommavalue = 0;

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
        View view = li.inflate(R.layout.recordingweightnumberpicker, null);

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
        integer.setBackgroundColor(Color.GRAY);
        integer.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        //Set afterkomma Value 0-9
        afterkomma.setMinValue(0);
        afterkomma.setMaxValue(9);
        afterkomma.setBackgroundColor(Color.GRAY);
        afterkomma.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        // call function setPickerValues
        setPickerValues(integer, afterkomma);

        //wrap@ getMinValue() || getMaxValue()
        integer.setWrapSelectorWheel(false);

        // inform the dialog it has a custom View
        builder.setView(view);

        // setOnClickListener on Button später
        Button späterbutton = (Button) view.findViewById(R.id.später);
        späterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close NumberPickerFragment
                getDialog().dismiss();
            }
        });

        // setOnClickListener on Button speichern
        Button speichernbutton = (Button) view.findViewById(R.id.speichern);
        speichernbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
                String formateddate = dateFormat.format(new java.util.Date());

                // call function integertofloat
                float weight = integertofloat(integervalue, afterkommavalue);

                // type declaration
                String type = "kg";

                // modul declaration
                String modulweight = "ModulWeight";

                // new weightdateobject with values
                Data wd = new Data(modulweight, formateddate, weight, type);

                // new DBHelperDataSource
                dataSourceData = new DataSourceData(context);
                dataSourceData.open();

                //insert data into database
                dataSourceData.insertdata(wd);

                Log.d("closesql", "<DATA>Die Datenquelle wird geschlossen.<DATA>");
                dataSourceData.close();

                // call function resetFragmentCounter
                MainMenu.resetFragmentCounter();

                // call function refreshMenu
                refreshMenu(getActivity());

                //close NumberPickerFragment
                getDialog().dismiss();
            }
        });
        return view;
    }


    public static void refreshMenu(Activity activity) {
        activity.invalidateOptionsMenu();
    }

    // function setPickerValues
    public void setPickerValues(NumberPicker integer, NumberPicker afterkomma) {
        // new DBHelperDataSource
        dataSourceData = new DataSourceData(context);
        dataSourceData.open();

        // call function getLatestEntry
        float lastentry = dataSourceData.getLatestEntry(getString(R.string.modulweight));

        if (lastentry != 0) {
            // if lastentry existing -> set pickers
            int lastinteger = 0;
            int lastfloat = 0;

            //set value integer
            lastinteger = ((int) lastentry);
            integer.setValue(lastinteger);
            integervalue = lastinteger;

            //set value afterkomma
            lastentry = lastentry * 10;
            lastfloat = (int) (lastentry - lastinteger * 10);
            afterkomma.setValue(lastfloat);
            afterkommavalue = lastfloat;
        } else {
            // if lastentry not existing -> set default values
            integer.setValue(80);
            integervalue = 80;
        }
        Log.d("closesql", "<DATA>Die Datenquelle wird geschlossen.<DATA>");
        dataSourceData.close();
    }

    //function integer values -> float integervalue,afterkommavalue
    public float integertofloat(int integervalue, int afterkommavalue) {
        float result = 0;
        result += (float) integervalue;
        result += ((float) afterkommavalue / 10);
        return result;
    }
}
