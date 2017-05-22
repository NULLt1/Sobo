package com.example.liebherr_365_gesundheitsapp.ModulWeight;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.Database.DataSourceData;
import com.example.liebherr_365_gesundheitsapp.R;

public class HistorieModulWeight extends AppCompatActivity {
    public static CursorAdapterWeight adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set up navigation enabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_historie);

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
        DataSourceData dataSourceData = new DataSourceData(this);
        dataSourceData.open();

        // weightlist adapter
        adapter = new CursorAdapterWeight(this, dataSourceData.getPreparedCursorForHistorieList());

        // set adapter to weightlist
        weightlist.setAdapter(adapter);

        // close db connection
        dataSourceData.close();
    }


}
