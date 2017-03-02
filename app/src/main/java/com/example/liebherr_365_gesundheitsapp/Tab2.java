package com.example.liebherr_365_gesundheitsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.liebherr_365_gesundheitsapp.viewAdapter.GridViewAdapter;
import com.example.liebherr_365_gesundheitsapp.viewAdapter.ModulesCursorAdapterButtons;

import Database.DBHelperDataSourceModules;
import Database.ModulesQuery;

public class Tab2 extends Fragment {
    DBHelperDataSourceModules dataSourceModules;
    Cursor cursor;
    public static GridViewAdapter gridViewAdapter = null;
    Context context = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);
        context = getActivity();
        //get the Data as Cursor
        dataSourceModules = new DBHelperDataSourceModules(getActivity());
        cursor = dataSourceModules.getSelectedDataCursor();
        fillGridView(rootView);

        return rootView;
    }

    public void fillGridView(View view) {
        GridView gridView = (GridView) view.findViewById(R.id.gridViewButtons);
        gridViewAdapter = new GridViewAdapter(getActivity(), cursor);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                cursor.moveToPosition(position);
                try {
                    final String modulPath = cursor.getString(cursor.getColumnIndexOrThrow(ModulesQuery.getColumnModul()));

                    //TODO: NOT WORKING
                    final Class<?> act = Class.forName(context.getPackageName() + modulPath);

                    Intent intent = new Intent(getActivity(), act);
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("****Test", "on resume");

    }

}
