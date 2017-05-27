package com.example.liebherr_365_gesundheitsapp.ModulDrink;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.lzyzsd.circleprogress.DonutProgress;


import com.example.liebherr_365_gesundheitsapp.R;

public class ModulDrink extends AppCompatActivity {
    private DonutProgress donutProgress;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modul_drink);

        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);

        donutProgress.setProgress(10.0f);
        donutProgress.setPrefixText("Gläser");
    }
}
