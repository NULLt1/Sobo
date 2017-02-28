package com.example.liebherr_365_gesundheitsapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.viewAdapter.CursorAdapterWeight;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import Database.*;

public class ModulWeight extends AppCompatActivity {
    // new DBHelperDataSourceData
    private DBHelperDataSourceData dataSourceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SavedSharedPrefrences.setSharedPreferences(this);

        setContentView(R.layout.modul_weight);

        ListView weightlist = (ListView) findViewById(R.id.listview);

        dataSourceData = new DBHelperDataSourceData(this);
        dataSourceData.open();
        //dataSourceData.deletedb();
        CursorAdapterWeight adapter = new CursorAdapterWeight(this, dataSourceData.getPreparedCursorForWeightList());
        weightlist.setAdapter(adapter);
        dataSourceData.close();

        super.onCreate(savedInstanceState);

        // set up navigation enabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BmiCalculator.setRecBmi();

        // set text weightgoal
        float weightgoal = SavedSharedPrefrences.getWeightGoal();
        String weightgoalstring = String.valueOf(weightgoal);
        TextView text = (TextView) findViewById(R.id.weightgoal);
        text.setText(weightgoalstring);


        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //Set afterkomma Value 0-9
        afterkomma.setMinValue(0);
        afterkomma.setMaxValue(9);

        //wrap@ getMinValue() || getMaxValue()
        integer.setWrapSelectorWheel(false);



        //Set Text Weightdifference
        Button buttonWeightDifference = (Button) findViewById(R.id.buttonWeightDifference);
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


        //TODO FIX
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        //setButtonWeightDifferenceText(buttonWeightDifference);
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


        /*////////////////////////////////////////////////////////////////////////////////
        // start notification oncreate
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager) ModulWeight.this.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(ModulWeight.this, Notification.class);
        alarmIntent = PendingIntent.getService(ModulWeight.this, 0, intent, 0);
        ////////////////////////////////////////////////////////////////////////////////*/


        // call notification all minute -> works
        //alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 60 * 1000, alarmIntent);

        // call notification on defined time and repeat all 20 minutes -> works
        /*
        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 57);

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 20, alarmIntent);
        */


         /*////////////////////////////////////////////////////////////////////////////////
        //TODO: NACH 3 Tagen ohne Aufruf Notification ausgeben + auslagern
        // call notification at defined time
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(System.currentTimeMillis());
        calender.set(Calendar.HOUR_OF_DAY, 15);
        calender.set(Calendar.MINUTE, 16);

        alarmMgr.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), alarmIntent);
        ////////////////////////////////////////////////////////////////////////////////*/
    }


    public void deleteweightdb(View view) {
        dataSourceData = new DBHelperDataSourceData(this);
        dataSourceData.open();
        dataSourceData.deletedb();
        dataSourceData.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //function saveweight onklick @+id/viewgraph
    public void viewgraph(View view) {
        //Creatiing new intent, which navigates to ViewGraph on call
        Intent intent = new Intent(ModulWeight.this, ViewGraph.class);
        startActivity(intent);
    }

    //function newweight onklick @+id/saveButton
    public void newweight(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");

        DatePickerFragment fragment = new DatePickerFragment();
        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        //get date from buttondate
        Button button = (Button) findViewById(R.id.buttondate);
        String buttonText = (String) button.getText();


        //convert datestrings to int
        int dayinteger = Integer.parseInt(buttonText.substring(0, 2));
        int monthinteger = Integer.parseInt(buttonText.substring(3, 5)) - 1;
        int yearinteger = Integer.parseInt(buttonText.substring(6));

        // exclude years smaller then 2016
        if (yearinteger < 2016) {
            alertdialogwrongdatum();
        } else {
            yearinteger = yearinteger - 1900;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formateddate = sdf.format(new Date(yearinteger, monthinteger, dayinteger));

            Log.d("formateddate", formateddate);

            //Numberpicker
            NumberPicker integer = (NumberPicker) findViewById(R.id.integer);
            NumberPicker afterkomma = (NumberPicker) findViewById(R.id.afterkomma);

            // get values of Numberpicker
            int integervalue = integer.getValue();
            int afterkommavalue = afterkomma.getValue();

            // call function integertofloat
            float weight = integertofloat(integervalue, afterkommavalue);
            Log.d("bmi", Float.toString(BmiCalculator.calculateBmi(this, weight)));

            //type declaration
            String type = "kg";

            // new weightdateobject with values
            String modulweight = String.valueOf(R.string.modulweight);
            Data wd = new Data(modulweight, formateddate, weight, type);

            // new DBHelperDataSource
            dataSourceData = new DBHelperDataSourceData(this);
            dataSourceData.open();

            // call function datealreadysaved and react on result
            boolean datealreadyexisting = dataSourceData.datealreadysaved(wd);
            Log.d("result", String.valueOf(datealreadyexisting));


            if (datealreadyexisting) {
                //call alertdialog
                alertdialogalreadysaved(wd);

            } else {
                dataSourceData.insertdata(wd);

                Log.d("closesql", "<DATA>Die Datenquelle wird geschlossen.<DATA>");
                dataSourceData.close();

                //Creatiing new intent, which navigates to ViewGraph on call
                Intent intent = new Intent(ModulWeight.this, ViewGraph.class);
                startActivity(intent);
            }
        }
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    }

    //function showDatePickerDialog
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    //alertdialogalreadysaved
    public void alertdialogalreadysaved(final Data wd) {
        final Context context = this;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Achtung!");
        alertDialogBuilder.setMessage("Zu diesem Datum existiert schon ein Gewicht");
        alertDialogBuilder.setPositiveButton("Ändern",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //action
                        Log.d("Ausgbabe", "Ändern");
                        //call function updatedata
                        dataSourceData.updatedata(wd);
                        Log.d("closesql", "<DATA>Die Datenquelle wird geschlossen.<DATA>");
                        dataSourceData.close();

                        //Creatiing new intent, which navigates to ViewGraph on call
                        Intent intent = new Intent(ModulWeight.this, ViewGraph.class);
                        startActivity(intent);

                        dialog.dismiss();
                    }
                });

        alertDialogBuilder.setNegativeButton("Abbruch",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int d) {
                        //action
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //alertdialogwrongdatum
    public void alertdialogwrongdatum() {
        final Context context = this;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Achtung!");
        alertDialogBuilder.setMessage("Dieses Datum ist nicht zulässig");
        alertDialogBuilder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int d) {
                        //action
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //function integer values -> float integervalue,afterkommavalue
    public float integertofloat(int integervalue, int afterkommavalue) {
        float result = 0;
        result += (float) integervalue;
        result += ((float) afterkommavalue / 10);
        return result;
    }

    private void setButtonWeightDifferenceText(Button button) {
        String text = "";
        dataSourceData.open();
        float weightDifference = BmiCalculator.calculateWeightDifference(dataSourceData);
        dataSourceData.close();
        if (weightDifference > 0) {
            text = weightDifference + "kg Über dem Wunschgewicht";
        } else if (weightDifference < 0) {
            weightDifference = weightDifference * -1;
            text = weightDifference + "kg unter dem Wunschgewicht";
        } else {
            text = "Wunschgewicht erreicht!";
        }
        button.setText(text);
    }
}
