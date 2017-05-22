package com.example.liebherr_365_gesundheitsapp.ModulWeight;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DatePickerModulWeight extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private int currentday;
    private int currentmonth;
    private int currentyear;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current date as the default date in the picker
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        String actualdate = dateFormat.format(new java.util.Date());

        //convert datestrings to int
        currentday = Integer.parseInt(actualdate.substring(0, 2));
        currentmonth = Integer.parseInt(actualdate.substring(3, 5)) - 1;
        currentyear = Integer.parseInt(actualdate.substring(6));

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, currentyear, currentmonth, currentday);
    }

    //function onDateSet
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Log.d("day", String.valueOf(day));
        Log.d("day", String.valueOf(month));
        Log.d("day", String.valueOf(year));
        Log.d("day", String.valueOf(currentday));
        Log.d("day", String.valueOf(currentmonth));
        Log.d("day", String.valueOf(currentyear));


        if (year < 2016 || (year > currentyear || (year == currentyear && month > currentmonth || (year == currentyear && month == currentmonth && day > currentday)))) { // exclude years smaller then 2016
            // create new WrongDatumFragment
            DialogFragment WrongDatumFragment = new WrongDatumFragment();

            // open WrongDatumFragment
            WrongDatumFragment.show(getFragmentManager(), "wrongDatum");
            getDialog().dismiss();
        } else {
            // create new NumberPickerModulWeight
            DialogFragment NumberPickerModulWeight = new NumberPickerModulWeight();

            // create bundle and fill with values
            Bundle bundle = new Bundle();
            bundle.putInt("day", day);
            bundle.putInt("month", month);
            bundle.putInt("year", year);

            // setArguments to NumberPickerModulWeight
            NumberPickerModulWeight.setArguments(bundle);

            // open NumberPickerModulWeight
            NumberPickerModulWeight.show(getFragmentManager(), "numberPicker");
        }
    }
}
