package com.example.liebherr_365_gesundheitsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import java.sql.Date;
import java.text.SimpleDateFormat;

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
    Button btnNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // NOTIFICATION
        /*
        //Button wird festgelegt (saveButton)
        btnNotification = (Button) findViewById(R.id.saveButton);
        btnNotification.setOnClickListener(new View.OnClickListener() {

            //service wir gestarten mit dem klick auf den Button
            @Override
            public void onClick(View view) {
                Intent startNotificationsServiceIntent = new Intent(MainActivity.this, Notification.class);
                startService(startNotificationsServiceIntent);
            }
        });
        */


        //Intialize integer and aftkomma as numberpicker to use functions
        NumberPicker integer = (NumberPicker) findViewById(R.id.integer);
        NumberPicker afterkomma = (NumberPicker) findViewById(R.id.afterkomma);

        //Set interger Value 40-100
        integer.setMinValue(40);
        integer.setMaxValue(100);

        //Set afterkomma Value 0-9
        afterkomma.setMinValue(0);
        afterkomma.setMaxValue(9);

        //wrap@ getMinValue() || getMaxValue()
        integer.setWrapSelectorWheel(false);
    }


    //function saveweight onklick @+id/saveButton

    public void saveweight(View view) {

        //Datepicker
        DatePicker date = (DatePicker) findViewById(R.id.dp);
        int dayinteger = date.getDayOfMonth();
        int monthinteger = date.getMonth();
        int yearinteger = date.getYear() - 1900;

        // bmi placeholder
        float bmi = 24;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formateddate = sdf.format(new Date(yearinteger, monthinteger, dayinteger));


        //Numberpicker
        NumberPicker integer = (NumberPicker) findViewById(R.id.integer);
        NumberPicker afterkomma = (NumberPicker) findViewById(R.id.afterkomma);

        // get values of Numberpicker
        int integervalue = integer.getValue();
        int afterkommavalue = afterkomma.getValue();

        // call function integertofloat
        float weight = integertofloat(integervalue, afterkommavalue);

        // new weightdateobject with values
        weightdata wd = new weightdata(weight, formateddate, bmi);

        // new DBHelperDataSource
        dataSource = new DBHelperDataSource(this);

        Log.d("opensql", "Die Datenquelle wird geöffnet.");
        dataSource.open();

        try {
            //call function insertdata
            dataSource.insertdata(wd);
        } catch (Exception e) {
            alertdialog();
            e.printStackTrace();
        }

        Log.d("closesql", "Die Datenquelle wird geschlossen.");
        dataSource.close();

        // call function getData
        String sqlvalue = dataSource.getData();
        Log.d("sqlvalue", sqlvalue);

        /*
        //display weight and month on console
        Log.d("weight", String.valueOf(weight));

        // call function monthinegertostring
        String month = monthinegertostring(monthinteger);

        //console out with debug
        Log.d("day", String.valueOf(dayinteger));
        Log.d("day", String.valueOf(month));
        Log.d("day", String.valueOf(yearinteger));
        */
    }

    public void alertdialog() {
        final Context context = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Achtung!");
        builder.setMessage("Zu diesem Datum existiert schon ein Gewicht");
        builder.setPositiveButton("Ändern",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //action
                        dialog.dismiss();
                    }
                });

        builder.setNegativeButton("Abbruch",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int d) {
                        //action
                        dialog.dismiss();
                    }
                });
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
