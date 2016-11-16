package com.example.liebherr_365_gesundheitsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ListViewTable extends AppCompatActivity {
    DBHelperDataSource database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_table);
        database = new DBHelperDataSource(this);
        database.open();
        showAllListEntries();
        database.close();
    }

    private void showAllListEntries() {
        List<Weightdata> weightdataList = database.getAllData();

        Log.d("ds",weightdataList.toString());
        ArrayAdapter<Weightdata> adapter = new WeightdataArrayAdapter(this, 0, weightdataList);
        ListView listView = (ListView) findViewById(R.id.ListViewWeightData);
        listView.setAdapter(adapter);
    }
}
