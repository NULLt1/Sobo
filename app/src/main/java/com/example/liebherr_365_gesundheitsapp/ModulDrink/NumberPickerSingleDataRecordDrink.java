package com.example.liebherr_365_gesundheitsapp.ModulDrink;

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

import com.example.liebherr_365_gesundheitsapp.Database.Data;
import com.example.liebherr_365_gesundheitsapp.Database.DataSourceData;
import com.example.liebherr_365_gesundheitsapp.R;

public class NumberPickerSingleDataRecordDrink extends DialogFragment {
    private Context context;
    private DataSourceData dataSourceData;
    private int glasses;
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
        View view = li.inflate(R.layout.numberpickerdrink, null);

        //Intialize glasspicker as numberpicker to use functions
        final NumberPicker glassespicker = (NumberPicker) view.findViewById(R.id.glasses);

        // setOnValueChangedListener on NumberPicker glasspicker
        glassespicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                glasses = newValue;
            }
        });

        //Set interger Value 1-30
        glassespicker.setMinValue(1);
        glassespicker.setMaxValue(30);
        glassespicker.setBackgroundColor(Color.GRAY);
        glassespicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        // call function setPickerValues
        setPickerValues(glassespicker);

        //wrap@ getMinValue() || getMaxValue()
        glassespicker.setWrapSelectorWheel(false);

        // inform the dialog it has a custom View
        builder.setView(view);

        // setOnClickListener on Button save
        Button button = (Button) view.findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // type declaration
                String type = "Gläser";

                // modul declaration
                String modulweight = "ModulDrink";

                // new dataobject with values
                Data wd = new Data(modulweight, bundledatum, glasses, type);

                // new DBHelperDataSource
                dataSourceData = new DataSourceData(context);
                dataSourceData.open();

                //call function updatedata
                dataSourceData.updatedata(wd);

                try {
                    HistorieModulDrink.adapter.changeCursor(dataSourceData.getPreparedCursorForHistorieList("ModulDrink"));
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

    // function setPickerValues
    private void setPickerValues(NumberPicker glasses) {
        // new DBHelperDataSource
        dataSourceData = new DataSourceData(context);
        dataSourceData.open();

        // get value of selected item
        int value = (int) dataSourceData.getValueWithDatum("ModulDrink", bundledatum);

        if (value != 0) {
            glasses.setValue(value);
        } else {
            glasses.setValue(5);
        }


        Log.d("closesql", "<DATA>Die Datenquelle wird geschlossen.<DATA>");
        dataSourceData.close();
    }
}
