package com.example.liebherr_365_gesundheitsapp.ModulDrink;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;


import com.example.liebherr_365_gesundheitsapp.R;

public class ModulDrink extends AppCompatActivity {
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

        // disableMinusButton if glassCounter == 0
        if (glassCounter == 0) {
            disableMinusButton();
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
    }

    // function setGlassesText
    private void setGlassesText() {
        // bind textglasses to TextView
        TextView textGlasses = (TextView) findViewById(R.id.glasses);

        // set current glassCounter as text
        textGlasses.setText(String.valueOf(glassCounter));
    }

    // function enableMinusButton
    private void enableMinusButton() {
        // bind minusButton to Button
        Button minusButton = (Button) findViewById(R.id.minus);

        // enable Button
        minusButton.setEnabled(true);

        // change alpha
        minusButton.getBackground().setAlpha(255);
    }

    // function disableMinusButton
    private void disableMinusButton() {
        // bind minusButton to Button
        Button minusButton = (Button) findViewById(R.id.minus);

        // disable Button
        minusButton.setEnabled(false);

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
}
