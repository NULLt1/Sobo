package com.example.liebherr_365_gesundheitsapp.ModulWeight;


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
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.Database.Data;
import com.example.liebherr_365_gesundheitsapp.Database.DataSourceData;
import com.example.liebherr_365_gesundheitsapp.R;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class NumberPickerSingleDataRecord extends DialogFragment {
    private Context context;
    private DataSourceData dataSourceData;
    private int integervalue;
    private int afterkommavalue = 0;
    private String bundledatum;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        // get value from bundle
        Bundle bundle = this.getArguments();
        bundledatum = bundle.getString("date");

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

        // setOnClickListener on Button save
        Button button = (Button) view.findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // type declaration
                String type = "kg";

                // modul declaration
                String modulweight = "ModulWeight";

                // call function integertofloat
                float weight = integertofloat(integervalue, afterkommavalue);

                // new weightdateobject with values
                Data wd = new Data(modulweight, bundledatum, weight, type);

                // new DBHelperDataSource
                dataSourceData = new DataSourceData(context);
                dataSourceData.open();

                if (dataSourceData.getLatestEntryDatum("ModulWeight").equals(bundledatum)) {
                    ModulWeight.setFirstWeight(weight);
                }

                //call function updatedata
                dataSourceData.updatedata(wd);

                ModulWeight.adapter.changeCursor(dataSourceData.getPreparedCursorForWeightList());
                try {
                    HistorieModulWeight.adapter.changeCursor(dataSourceData.getPreparedCursorForHistorieList("ModulWeight"));
                } catch (Exception e) {
                    Log.d("ERROR", String.valueOf(e));
                }

                Log.d("closesql", "<DATA>Die Datenquelle wird geschlossen.<DATA>");
                dataSourceData.close();

                //close NumberPickerFragment
                getDialog().dismiss();

            }
        });
        return view;
    }

    // function integer values -> float integervalue,afterkommavalue
    private float integertofloat(int integervalue, int afterkommavalue) {
        float result = 0;
        result += (float) integervalue;
        result += ((float) afterkommavalue / 10);
        return result;
    }

    // function setPickerValues
    private void setPickerValues(NumberPicker integer, NumberPicker afterkomma) {
        // new DBHelperDataSource
        dataSourceData = new DataSourceData(context);
        dataSourceData.open();

        // get value of selected item
        float value = dataSourceData.getValueWithDatum("ModulWeight", bundledatum);

        if (value != 0) {
            // if value existing -> set pickers
            int lastinteger = 0;
            int lastfloat = 0;

            //set value integer
            lastinteger = ((int) value);
            integer.setValue(lastinteger);
            integervalue = lastinteger;

            //set value afterkomma
            value = value * 10;
            lastfloat = (int) (value - lastinteger * 10);
            afterkomma.setValue(lastfloat);
            afterkommavalue = lastfloat;
        } else {
            // if value not existing -> set default values
            integer.setValue(80);
            integervalue = 80;
        }
        Log.d("closesql", "<DATA>Die Datenquelle wird geschlossen.<DATA>");
        dataSourceData.close();
    }
}