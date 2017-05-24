package com.example.liebherr_365_gesundheitsapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.liebherr_365_gesundheitsapp.Database.DataSourceModules;
import com.example.liebherr_365_gesundheitsapp.Database.Queries;
import com.example.liebherr_365_gesundheitsapp.viewAdapter.GridViewAdapter;

public class Tab2 extends Fragment {
    DataSourceModules dataSourceModules;
    Cursor cursor;
    public static GridViewAdapter gridViewAdapter = null;
    Context context = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);
        context = getActivity();
        //get the Data as Cursor
        dataSourceModules = new DataSourceModules(getActivity());
        cursor = dataSourceModules.getactivemodulescursor();
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
                cursor = dataSourceModules.getactivemodulescursor();
                if (!cursor.moveToFirst())
                    cursor.moveToFirst();

                cursor.moveToPosition(position);

                try {
                    final String modulPath = cursor.getString(cursor.getColumnIndexOrThrow(Queries.COLUMN_MODUL));
                    final Class<?> act = Class.forName(context.getPackageName() + "." + modulPath + "." + modulPath);

                    Intent intent = new Intent(getActivity(), act);
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
