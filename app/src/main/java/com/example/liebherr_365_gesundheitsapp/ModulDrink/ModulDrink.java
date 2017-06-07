package com.example.liebherr_365_gesundheitsapp.ModulDrink;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.Database.Data;
import com.example.liebherr_365_gesundheitsapp.Database.DataSourceData;
import com.example.liebherr_365_gesundheitsapp.ModulWeight.HistorieModulWeight;
import com.example.liebherr_365_gesundheitsapp.ModulWeight.ModulWeight;
import com.github.lzyzsd.circleprogress.DonutProgress;


import com.example.liebherr_365_gesundheitsapp.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ModulDrink extends AppCompatActivity {
    // new DataSourceData
    private DataSourceData dataSourceData;
    private int glassCounter = 0;
    private float donutProgressCounter;
    private int maxGlasses = 8;

    @Override
    public void onResume() {
        super.onResume();

        if (glassCounter == 0) {
            // disableMinusButton if glassCounter == 0
            disableMinusButton();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setContentView
        setContentView(R.layout.activity_modul_drink);

        if (entryAlreadyExisting()) {
            // entryExisting
            entryExists();
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
        donutProgress.setPrefixText("Gläser");
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
    }

    // function enableMinusButton
    private void enableMinusButton() {
        // bind minusButton to Button
        Button minusButton = (Button) findViewById(R.id.minus);

        // enable Button
        minusButton.setEnabled(true);

        // set color
        minusButton.setBackgroundResource(R.color.colorPrimaryDark);
        minusButton.setTextColor(Color.parseColor("#FFFFFF"));

        // change alpha
        minusButton.getBackground().setAlpha(255);
    }

    // function disableMinusButton
    private void disableMinusButton() {
        // bind minusButton to Button
        Button minusButton = (Button) findViewById(R.id.minus);

        // disable Button
        minusButton.setEnabled(false);

        // set color
        minusButton.setBackgroundResource(R.color.colorLightGrey);
        minusButton.setTextColor(Color.parseColor("#403f3f"));

        // change alpha
        minusButton.getBackground().setAlpha(45);
    }

    // function countUpDonutProgressCounter
    private void countUpDonutProgressCounter() {
        donutProgressCounter += 12.5f;
    }

    // function countDownDonutProgressCounter
    private void countDownDonutProgressCounter() {
        donutProgressCounter -= 12.5f;
    }

    // function setDonutProgress
    private void setDonutProgress() {
        // bind donutProgress to DonutProgress
        DonutProgress donutProgress = (DonutProgress) findViewById(R.id.donut_progress);

        // setDonutProgress
        donutProgress.setProgress(donutProgressCounter);

        if (donutProgressCounter == 100) {
            // set finished color green
            donutProgress.setFinishedStrokeColor(Color.parseColor("#5CC053"));
        } else {
            // set finished color blue
            donutProgress.setFinishedStrokeColor(Color.parseColor("#2c60ad"));
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
        glassCounter = (int) dataSourceData.getLatestEntry("ModulDrink");

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
        String type = "Gläser";

        // modul declaration
        String modulweight = "ModulDrink";

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
        result = dataSourceData.entryAlreadyExisting("ModulDrink");

        // close db connection
        dataSourceData.close();

        return result;
    }

    //function viewgraph onklick @+id/historie
    public void historie(View view) {
        //Creatiing new intent, which navigates to HistorieModulDrink on call
        Intent intent = new Intent(ModulDrink.this, HistorieModulDrink.class);
        startActivity(intent);
    }

}
