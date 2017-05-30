package com.example.liebherr_365_gesundheitsapp.ModulHealth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.liebherr_365_gesundheitsapp.R;
import com.example.liebherr_365_gesundheitsapp.viewAdapter.ListViewAdapterModuleHealth;

public class ModulHealth extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modul_health);

        ListView lv = (ListView) findViewById(R.id.listViewHealth);
        ListViewAdapterModuleHealth adapter = new ListViewAdapterModuleHealth(this);
        lv.setAdapter(adapter);
    }
}


