package com.example.liebherr_365_gesundheitsapp.ModulHealth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.liebherr_365_gesundheitsapp.Database.DataHealthCare;
import com.example.liebherr_365_gesundheitsapp.Database.DataSourceHealthCare;
import com.example.liebherr_365_gesundheitsapp.R;
import com.example.liebherr_365_gesundheitsapp.viewAdapter.ListViewAdapterModuleHealth;

import java.util.List;

public class ModulHealth extends AppCompatActivity {
    private DataSourceHealthCare dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new DataSourceHealthCare(this);
        setContentView(R.layout.activity_modul_health);
        dataSource.open();
        List<List<DataHealthCare>> list = dataSource.getGroupedData();
        dataSource.close();
        ListView lv = (ListView) findViewById(R.id.listViewHealth);
        View v = getLayoutInflater().inflate(R.layout.modul_health_footer, null);
        lv.addFooterView(v);
        ListViewAdapterModuleHealth adapter = new ListViewAdapterModuleHealth(this, list);
        lv.setAdapter(adapter);
    }
}


