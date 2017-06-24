package com.example.liebherr_365_gesundheitsapp.ModulCoffee;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.Database.Data;
import com.example.liebherr_365_gesundheitsapp.Database.DataSourceData;

import com.example.liebherr_365_gesundheitsapp.ModulWeight.SettingsActivityModulWeight;
import com.example.liebherr_365_gesundheitsapp.R;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ModulCoffee extends AppCompatActivity {
    // new DataSourceData
    private DataSourceData dataSourceData;
    private int glassCounter = 0;
    private float donutProgressCounter;
    static int maxGlasses = 4;

    public static int getMaxGlasses() {
        return maxGlasses;
    }

    public static void setMaxGlasses(int maxGlasses) {
        ModulCoffee.maxGlasses = maxGlasses;
    }
    
    @Override
    public void onResume() {
        super.onResume();

        if (glassCounter == 0) {
            // disableButtons if glassCounter == 0
            disableMinusButton();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setContentView
        setContentView(R.layout.activity_modul_coffee);

        if (entryAlreadyExisting()) {
            // entryExisting
            entryExists();
        }


        if (dataExisting()) {
            enableHistorieButton();
        } else {
            disableHistorieButton();
        }

        // disableMinusButton if glassCounter == 0
        if (glassCounter == 0) {
            disableMinusButton();
        } else {
            enableMinusButton();
        }


        // bind donutProgress to DonutProgress
        DonutProgress donutProgress = (DonutProgress) findViewById(R.id.donut_progress);

        // set donutProgess text
        donutProgress.setPrefixText("Tassen");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modulcoffee, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivityModulCoffee.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //function minusTrigger onklick @+id/minus
    public void minusTrigger(View view) {
        glassCounter--;
        if (glassCounter == 0) {
            // disableMinusButton if glassCounter == 0
            disableMinusButton();
            deleteData();
        } else {
            updateData();
        }
        if (glassCounter < maxGlasses) {
            countDownDonutProgressCounter();
            setDonutProgress();
        }

        // call function setGlassesText
        setGlassesText();

        if (dataExisting()) {
            enableHistorieButton();
        } else {
            disableHistorieButton();
        }
    }

    //function plusTrigger onklick @+id/plus
    public void plusTrigger(View view) {
        glassCounter++;
        if (glassCounter <= maxGlasses) {
            countUpDonutProgressCounter();
            setDonutProgress();
        }

        // call function enableMinusButton
        enableMinusButton();

        // call function setGlassesText
        setGlassesText();

        if (!entryAlreadyExisting()) {
            insertData();
        } else {
            updateData();
        }

        if (dataExisting()) {
            enableHistorieButton();
        } else {
            disableHistorieButton();
        }
    }


    // function enableButtons
    private void enableHistorieButton() {
        // bind historieButton to Button
        Button historieButton = (Button) findViewById(R.id.historie_coffee);

        // enable Buttons
        historieButton.setEnabled(true);

        // set color
        historieButton.setTextColor(Color.parseColor("#7f7d7f"));

        // change alpha
        //historieButton.getBackground().setAlpha(45);
    }

    // function disableButtons
    private void disableHistorieButton() {
        // bind historieButton to Button
        Button historieButton = (Button) findViewById(R.id.historie_coffee);

        // disable Button
        historieButton.setEnabled(false);

        // set color
        historieButton.setTextColor(Color.parseColor("#f7f7f7"));

        // change alpha
        //historieButton.getBackground().setAlpha(45);
    }

    // function enableButtons
    private void enableMinusButton() {
        // bind minusButton to Button
        Button minusButton = (Button) findViewById(R.id.minus_coffee);

        // enable Buttons
        minusButton.setEnabled(true);

        // change alpha
        minusButton.getBackground().setAlpha(255);

        // set color
        minusButton.setTextColor(Color.parseColor("#7f7d7f"));
    }

    // function disableButtons
    private void disableMinusButton() {
        // bind minusButton to Button
        Button minusButton = (Button) findViewById(R.id.minus_coffee);

        // disable Button
        minusButton.setEnabled(false);

        // change alpha
        //minusButton.getBackground().setAlpha(45);

        // set color
        minusButton.setTextColor(Color.parseColor("#f7f7f7"));
    }

    // function countUpDonutProgressCounter
    private void countUpDonutProgressCounter() {
        donutProgressCounter += 25f;
    }

    // function countDownDonutProgressCounter
    private void countDownDonutProgressCounter() {
        donutProgressCounter -= 25f;
    }

    // function setDonutProgress
    private void setDonutProgress() {
        // bind donutProgress to DonutProgress
        DonutProgress donutProgress = (DonutProgress) findViewById(R.id.donut_progress);

        // bind TextView to reached
        TextView reached = (TextView) findViewById(R.id.reached);

        // setDonutProgress
        donutProgress.setProgress(donutProgressCounter);

        if (donutProgressCounter == 100) {
            // set finished color green
            donutProgress.setFinishedStrokeColor(Color.parseColor("#c05853"));
            reached.setText(R.string.limit_reached);
        } else {
            // set finished color blue
            donutProgress.setFinishedStrokeColor(Color.parseColor("#845a32"));
            reached.setText("");
        }
    }

    // function setGlassesText
    private void setGlassesText() {
        // bind textglasses to TextView
        TextView textGlasses = (TextView) findViewById(R.id.glasses);

        // set current glassCounter as text
        textGlasses.setText(String.valueOf(glassCounter));
    }

    // function setGlassCounter
    private void setGlassCounter() {
        dataSourceData = new DataSourceData(this);
        dataSourceData.open();

        //call function entryalreadyexisting
        glassCounter = (int) dataSourceData.getLatestEntry("ModulCoffee");

        // close db connection
        dataSourceData.close();
    }

    // function insertData()
    private void insertData() {
        // createNewDataObject with current date
        Data data = createNewDataObject();

        dataSourceData = new DataSourceData(this);
        dataSourceData.open();

        //call function insertdata
        dataSourceData.insertdata(data);

        // close db connection
        dataSourceData.close();
    }

    //function updateData()
    private void updateData() {
        // createNewDataObject with current date
        Data data = createNewDataObject();

        dataSourceData = new DataSourceData(this);
        dataSourceData.open();

        //call function updatedata
        dataSourceData.updatedata(data);

        // close db connection
        dataSourceData.close();
    }

    //function deleteData()
    private void deleteData() {
        // createNewDataObject with current date
        Data data = createNewDataObject();

        dataSourceData = new DataSourceData(this);
        dataSourceData.open();

        //call function deletesingledata
        dataSourceData.deletesingledata(data);

        // close db connection
        dataSourceData.close();
    }

    // createNewDataObject
    private Data createNewDataObject() {
        // get actualdate
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
        String actualdate = dateFormat.format(new java.util.Date());

        // type declaration
        String type = "Tassen";

        // modul declaration
        String modulweight = "ModulCoffee";

        // new weightdateobject with values
        return new Data(modulweight, actualdate, glassCounter, type);
    }

    // function entryExists
    private void entryExists() {
        // setGlassCounter
        setGlassCounter();

        // setGlassesText
        setGlassesText();

        // countUpDonutProgressCounter foreach glass
        for (int i = 1; i <= glassCounter; i++) {
            if (donutProgressCounter < 100) {
                countUpDonutProgressCounter();
            }
        }

        // setDonutProgress
        setDonutProgress();
    }

    // function entryAlreadyExisting
    private boolean entryAlreadyExisting() {
        // declare result
        boolean result = false;

        // new DBHelperDataSource
        dataSourceData = new DataSourceData(this);
        dataSourceData.open();

        //call function entryalreadyexisting
        result = dataSourceData.entryAlreadyExisting("ModulCoffee");

        // close db connection
        dataSourceData.close();

        return result;
    }

    //function dataExisting
    private boolean dataExisting() {
        // declare result
        boolean result = false;

        // new DBHelperDataSource
        dataSourceData = new DataSourceData(this);
        dataSourceData.open();

        // getFirstWeight
        int glasses = (int) dataSourceData.getLatestEntry("ModulCoffee");

        // handle empty db
        if (glasses != 0) {
            result = true;
        }

        // close db connection
        dataSourceData.close();

        return result;
    }

    //function viewgraph onklick @+id/historie
    public void historie(View view) {
        //Creatiing new intent, which navigates to HistorieModulDrink on call
        Intent intent = new Intent(ModulCoffee.this, HistorieModulCoffee.class);
        startActivity(intent);
    }

}
