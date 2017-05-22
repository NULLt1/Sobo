package com.example.liebherr_365_gesundheitsapp.ModulWeight;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.R;

import com.example.liebherr_365_gesundheitsapp.Database.*;

public class ModulWeight extends AppCompatActivity {
    // new DataSourceData
    private DataSourceData dataSourceData;
    public static CursorAdapterWeight adapter;
    @SuppressLint("StaticFieldLeak")
    private static TextView textweightstart;
    @SuppressLint("StaticFieldLeak")
    private static TextView textweightdiffernce;
    @SuppressLint("StaticFieldLeak")
    private static Button diagrammbutton = null;
    @SuppressLint("StaticFieldLeak")
    private static Button historiebutton = null;
    private static TextView textweightgoal;
    private static float firstweight;
    private static float weightgoal;

    @Override
    public void onResume() {
        super.onResume();

        // new DBHelperDataSource
        dataSourceData = new DataSourceData(this);
        dataSourceData.open();

        // weightlist adapter
        adapter.changeCursor(dataSourceData.getPreparedCursorForWeightList());

        // handle empty db
        if (dataSourceData.getLatestEntry("ModulWeight") == 0) {
            changeButtons();
        }

        // close db connection
        dataSourceData.close();

        // call function setWeightGoalText
        setWeightGoalText();

        // call function setActualWeight();
        setActualWeight();

        // call function setWeightDifference
        setWeightDifference();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // call function firstentry if flag = true
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean flag = prefs.getBoolean("flag", true);
        if (flag) {
            firstentry();
        }

        SavedSharedPrefrencesModulWeight.setSharedPreferences(this);

        setContentView(R.layout.activity_modul_weight);

        // bind textweightstart to TextView
        textweightstart = (TextView) findViewById(R.id.firstweight);

        // bind textweightdiffernce to TextView
        textweightdiffernce = (TextView) findViewById(R.id.weightdifference);

        // bind textweightgoal to TextView
        textweightgoal = (TextView) findViewById(R.id.weightgoal);

        // bind diagrammbutton to Button
        diagrammbutton = (Button) findViewById(R.id.viewgraph);

        // bind deletebutton to Button
        historiebutton = (Button) findViewById(R.id.historie);

        // bind weightlist to Listview
        ListView weightlist = (ListView) findViewById(R.id.listview);

        // onItemClickListener
        weightlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // selected item
                String selecteddate = ((TextView) view.findViewById(R.id.datum)).getText().toString();
                //deletedata(getWindow().getDecorView().getRootView());

                // create bundle and fill with values
                Bundle bundle = new Bundle();
                bundle.putString("date", selecteddate);

                // create new singledatarecord
                DialogFragment singledatarecord = new SingleDataRecord();

                // setArguments to SingleDataRecord
                singledatarecord.setArguments(bundle);

                // open singledatarecord
                singledatarecord.show(getFragmentManager(), "DeleteData");
            }
        });

        // new DBHelperDataSource
        dataSourceData = new DataSourceData(this);
        dataSourceData.open();

        // getFirstWeight
        firstweight = dataSourceData.getLatestEntry("ModulWeight");

        // handle empty db
        if (firstweight == 0) {
            disableButtons();
        } else {
            activateButtons();
        }

        // weightlist adapter
        adapter = new CursorAdapterWeight(this, dataSourceData.getPreparedCursorForWeightList());

        // set adapter to weightlist
        weightlist.setAdapter(adapter);

        // close db connection
        dataSourceData.close();

        super.onCreate(savedInstanceState);

        // set up navigation enabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BmiCalculator.setRecBmi();

        BmiCalculator.calculateBmi(this);

        // set text weightgoal
        setWeightGoalText();

        // set text weighstart
        setActualWeightText();

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

    // function firstentry
    private void firstentry() {
        DialogFragment textfragment = new FirstEntryTextFragment();
        textfragment.show(getFragmentManager(), "datePicker");
    }

    // function setFirstWeight
    public static void setFirstWeight(float firstweightparameter) {
        firstweight = firstweightparameter;
        setActualWeightText();
    }

    //function disableButtons
    public static void changeButtons() {
        changeString();
        disableButtons();
    }

    private static void changeString() {
        //defaultstring
        String defaultstring = "-.-";

        // set text textweightstart
        textweightstart.setText(String.valueOf(defaultstring));

        // set text textweightdifference
        textweightdiffernce.setText(defaultstring);
    }

    private static void disableButtons() {
        // set deletebutton disabled and change opacity
        diagrammbutton.setEnabled(false);
        diagrammbutton.getBackground().setAlpha(45);
        diagrammbutton.setTextColor(Color.parseColor("#BDBDBD"));

        // set deletebutton disabled and change opacity
        historiebutton.setEnabled(false);
        historiebutton.getBackground().setAlpha(45);
        historiebutton.setTextColor(Color.parseColor("#BDBDBD"));
    }

    private static void activateButtons() {
        // set deletebutton disabled and change opacity
        diagrammbutton.setEnabled(true);
        diagrammbutton.getBackground().setAlpha(255);
        diagrammbutton.setTextColor(Color.parseColor("#000000"));

        // set deletebutton disabled and change opacity
        historiebutton.setEnabled(true);
        historiebutton.getBackground().setAlpha(255);
        historiebutton.setTextColor(Color.parseColor("#000000"));
    }

    //function getWeightGoal
    public static float getWeightGoal() {
        return weightgoal;
    }

    //functiom setActualWeight
    private void setActualWeight() {
        // new DBHelperDataSource
        dataSourceData = new DataSourceData(this);
        dataSourceData.open();

        // getFirstWeight
        firstweight = dataSourceData.getLatestEntry("ModulWeight");

        // close db connection
        dataSourceData.close();
    }

    //function proveFirstWeight
    public static boolean proveFirstWeight() {
        boolean flag = false;
        if (firstweight != 0) {
            flag = true;
        }
        return flag;
    }

    //function setWeightStartText
    public static void setActualWeightText() {
        // set text weightstart
        if (proveFirstWeight()) {
            // setText firstweight
            String weightstartstring = String.valueOf(firstweight);
            textweightstart.setText(weightstartstring);

            // call function setWeightDiffernce
            setWeightDifference();
        }
    }

    //function setWeightDifference
    public static void setWeightDifference() {
        if (proveFirstWeight()) {
            String weightdifferncestring;
            float weightdiffernce;
            Log.d("~~~~~~~~~~~", "~~~~~~~~~~~~~");
            Log.d("firstweight", String.valueOf(firstweight));
            Log.d("weightgoal", String.valueOf(weightgoal));

            if (firstweight == weightgoal) {
                weightdifferncestring = "0.0 kg";
            } else if (firstweight < weightgoal) {
                weightdiffernce = weightgoal - firstweight;
                weightdiffernce = roundfloat(weightdiffernce);
                weightdifferncestring = "+ " + String.valueOf(weightdiffernce) + " kg";
            } else {
                weightdiffernce = firstweight - weightgoal;
                weightdiffernce = roundfloat(weightdiffernce);
                weightdifferncestring = "- " + String.valueOf(weightdiffernce) + " kg";
            }
            textweightdiffernce.setText(weightdifferncestring);
        }
    }

    // function roundfloat
    private static float roundfloat(float inputfloat) {
        float roundedfloat = 0;
        inputfloat += 0.05;
        inputfloat = (int) (inputfloat * 10);
        roundedfloat = inputfloat / 10;
        return roundedfloat;
    }

    //function setWeightGoalText
    public static void setWeightGoalText() {
        weightgoal = BmiCalculator.getAverageRecWeight();
        String weightgoalstring = String.valueOf(weightgoal);
        textweightgoal.setText(weightgoalstring);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modulweight, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivityModulWeight.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //function viewgraph onklick @+id/historie
    public void historie(View view) {
        //Creatiing new intent, which navigates to ViewGraph on call
        Intent intent = new Intent(ModulWeight.this, HistorieModulWeight.class);
        startActivity(intent);
    }

    //function viewgraph onklick @+id/viewgraph
    public void viewgraph(View view) {
        //Creatiing new intent, which navigates to ViewGraph on call
        Intent intent = new Intent(ModulWeight.this, ViewGraphModulWeight.class);
        startActivity(intent);
    }

    //function newweight onklick @+id/saveButton
    public void newweight(View view) {
        DialogFragment datepicker = new DatePickerModulWeight();
        datepicker.show(getFragmentManager(), "datePicker");
    }
}
