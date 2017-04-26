package com.example.liebherr_365_gesundheitsapp.ModulWeight;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.R;
import com.example.liebherr_365_gesundheitsapp.viewAdapter.CursorAdapterWeight;

import com.example.liebherr_365_gesundheitsapp.Database.*;

public class ModulWeight extends AppCompatActivity {
    // new DataSourceData
    private DataSourceData dataSourceData;
    public static CursorAdapterWeight adapter;
    private TextView textweightstart;
    private TextView textweightdiffernce;
    private TextView textweightgoal;
    private static float firstweight;
    private static float weightgoal;

    @Override
    public void onResume() {
        Log.d("Resumed", "Resumed");
        super.onResume();
        // call function setWeightGoalText
        setWeightGoalText();

        // call function setFirstWeight();
        setFirstWeight();

        // call function setWeightDifference
        setWeightDifference();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SavedSharedPrefrencesModulWeight.setSharedPreferences(this);

        setContentView(R.layout.modul_weight);

        // bind textweightstart to TextView
        textweightstart = (TextView) findViewById(R.id.firstweight);

        // bind textweightdiffernce to TextView
        textweightdiffernce = (TextView) findViewById(R.id.weightdifference);

        // bind textweightgoal to TextView
        textweightgoal = (TextView) findViewById(R.id.weightgoal);

        // bind diagrammbutton to Button
        Button diagrammbutton = (Button) findViewById(R.id.viewgraph);

        // bind deletebutton to Button
        Button deletebutton = (Button) findViewById(R.id.deleteButton);

        // bind weightlist to Listview
        ListView weightlist = (ListView) findViewById(R.id.listview);

        // new DBHelperDataSource
        dataSourceData = new DataSourceData(this);
        dataSourceData.open();

        // getFirstWeight
        firstweight = dataSourceData.getFirstWeight("ModulWeight");

        // handle empty db
        if (firstweight == 0) {
            // set deletebutton disabled and change opacity
            diagrammbutton.setEnabled(false);
            diagrammbutton.getBackground().setAlpha(45);
            diagrammbutton.setTextColor(getResources().getColor(R.color.colorLightGrey));

            // set deletebutton disabled and change opacity
            deletebutton.setEnabled(false);
            deletebutton.getBackground().setAlpha(45);
            deletebutton.setTextColor(getResources().getColor(R.color.colorLightGrey));
        } else {
            // set deletebutton enabled and change opacity, color
            diagrammbutton.setEnabled(true);
            diagrammbutton.getBackground().setAlpha(255);
            diagrammbutton.setTextColor(getResources().getColor(R.color.colorPrimary));

            // set deletebutton enabled and change opacity, color
            deletebutton.setEnabled(true);
            deletebutton.getBackground().setAlpha(255);
            deletebutton.setTextColor(getResources().getColor(R.color.colorPrimary));
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
        weightgoal = SavedSharedPrefrencesModulWeight.getWeightGoal();
        setWeightGoalText();

        // set text weighstart
        setWeightStartText();

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

    //function getWeightGoal
    public static float getWeightGoal() {
        return weightgoal;
    }

    //functiom setFirstWeight
    private void setFirstWeight() {
        // new DBHelperDataSource
        dataSourceData = new DataSourceData(this);
        dataSourceData.open();

        // getFirstWeight
        firstweight = dataSourceData.getFirstWeight("ModulWeight");

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
    public void setWeightStartText() {
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
    public void setWeightDifference() {
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
    public float roundfloat(float inputfloat) {
        float roundedfloat = 0;
        inputfloat += 0.05;
        inputfloat = (int) (inputfloat * 10);
        roundedfloat = inputfloat / 10;
        return roundedfloat;
    }

    //function setWeightGoalText
    public static void setWeightGoal(float weightgoalfloat) {
        weightgoal = weightgoalfloat;
    }

    //function setWeightGoalText
    public void setWeightGoalText() {
        String weightgoalstring = String.valueOf(weightgoal);
        textweightgoal.setText(weightgoalstring);
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

    //function deletedata onclick @+if/deleteButton
    public void deletedata(View view) {
        DialogFragment deletedata = new DeleteData();
        deletedata.show(getFragmentManager(), "DeleteData");
    }
}
