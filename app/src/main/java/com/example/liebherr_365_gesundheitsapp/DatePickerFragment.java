package com.example.liebherr_365_gesundheitsapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    int year, month, day;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        String actualdate = dateFormat.format(new java.util.Date());

        String buttonText = (String) ((TextView) getActivity().findViewById(R.id.plus)).getText();
        Log.d("buttonText", buttonText);

        //convert datestrings to int
        int day = Integer.parseInt(actualdate.substring(0, 2));
        int month = Integer.parseInt(actualdate.substring(3, 5)) - 1;
        int year = Integer.parseInt(actualdate.substring(6));

        Log.d("dayfromdialog", String.valueOf(day));
        Log.d("monthfromdialog", String.valueOf(month));
        Log.d("yearfromdialog", String.valueOf(year));

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    //function onDateSet
    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.day = day;
        this.month = month;
        this.year = year;
        DialogFragment newFragment = new NumberPickerFragment(day,month,year);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
