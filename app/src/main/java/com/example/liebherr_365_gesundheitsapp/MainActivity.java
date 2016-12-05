package com.example.liebherr_365_gesundheitsapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    //       .-..-..---. .-..-.
    //       : :; :: .--': :: :
    //       :    :: `;  : :: :
    //       : :: :: :   : :; :
    //       :_;:_;:_;   `.__.'
    //
    // This project was developed during our project studies.
    // Wintersemster 16/17
    // Bussmann    Jan
    // Hug         Melissa
    // Otec        Marvin
    // Speer       Christopher
    // Wangler     Niklas


    private DBHelperDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set date Button with current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        String actualdate = dateFormat.format(new java.util.Date());
        Button button = (Button) findViewById(R.id.buttondate);
        button.setText(actualdate);

        //Intialize integer and aftkomma as numberpicker to use functions
        NumberPicker integer = (NumberPicker) findViewById(R.id.integer);
        NumberPicker afterkomma = (NumberPicker) findViewById(R.id.afterkomma);

        //Set interger Value 40-100
        integer.setMinValue(40);
        integer.setMaxValue(150);

        //Set afterkomma Value 0-9
        afterkomma.setMinValue(0);
        afterkomma.setMaxValue(9);

        //wrap@ getMinValue() || getMaxValue()
        integer.setWrapSelectorWheel(false);

        BmiCalculator.setRecBmi(this);

        // start notification oncreate
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(MainActivity.this, Notification.class);
        alarmIntent = PendingIntent.getService(MainActivity.this, 0, intent, 0);

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

        // call notification on defined time
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(System.currentTimeMillis());
        calender.set(Calendar.HOUR_OF_DAY, 15);
        calender.set(Calendar.MINUTE, 21);

        alarmMgr.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), alarmIntent);

    }

    public void deleteweightdb(View view) {
        dataSource = new DBHelperDataSource(this);
        dataSource.deletedb();

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
            default:
                return
                        super.onOptionsItemSelected(item);
        }
    }


    //function saveweight onklick @+id/saveButton
    public void saveweight(View view) {

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

            // new weightdateobject with values
            Weightdata wd = new Weightdata(weight, formateddate, BmiCalculator.calculateBmi(this, weight));

            // new DBHelperDataSource
            dataSource = new DBHelperDataSource(this);

            Log.d("opensql", "Die Datenquelle wird geöffnet.");
            dataSource.open();

            // call function datealreadysaved and react on result
            boolean datealreadyexisting = dataSource.datealreadysaved(wd);
            Log.d("result", String.valueOf(datealreadyexisting));
            if (datealreadyexisting) {
                //call alertdialog
                alertdialogalreadysaved(wd);

            } else {
                dataSource.insertdata(wd);

                Log.d("closesql", "Die Datenquelle wird geschlossen.");
                dataSource.close();

                /*
                //Creatiing new intent, which navigates to Listviewtable on call
                Intent intent = new Intent(MainActivity.this, ListViewTable.class);
                startActivity(intent);
                */

                //Creatiing new intent, which navigates to ViewGraph on call
                Intent intent = new Intent(MainActivity.this, ViewGraph.class);
                startActivity(intent);

            }
        }
        //call function notification
        //Todo: Hier wird die Notification aufgerufen
        //notification();  ~~~~~~~~~auskommentiert~~~~~~~~~~*/
    }

    //function showDatePickerDialog
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }


    //alertdialogalreadysaved
    public void alertdialogalreadysaved(final Weightdata wd) {
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
                        dataSource.updatedata(wd);
                        Log.d("closesql", "Die Datenquelle wird geschlossen.");
                        dataSource.close();

                        /*
                        //Creatiing new intent, which navigates to Listviewtable on call
                        Intent intent = new Intent(MainActivity.this, ListViewTable.class);
                        startActivity(intent);
                        */

                        //Creatiing new intent, which navigates to ViewGraph on call
                        Intent intent = new Intent(MainActivity.this, ViewGraph.class);
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

    public void notification(int time) {
        //Todo:realisieren des Aufrufs nach ablauf von time
        //wait(time);
    }

    //function integer values -> float integervalue,afterkommavalue
    public float integertofloat(int integervalue, int afterkommavalue) {
        float result = 0;
        result += (float) integervalue;
        result += ((float) afterkommavalue / 10);
        return result;
    }

    //function monthinteger to string
    public String monthinegertostring(int dayinteger) {
        switch (dayinteger) {
            case 1:
                return "Januar";
            case 2:
                return "Februar";
            case 3:
                return "März";
            case 4:
                return "April";
            case 5:
                return "Mai";
            case 6:
                return "Juni";
            case 7:
                return "Juli";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "Oktober";
            case 11:
                return "November";
            case 12:
                return "Dezember";
        }
        return "a";
    }
}
