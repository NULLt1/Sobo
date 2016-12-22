package com.example.liebherr_365_gesundheitsapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.liebherr_365_gesundheitsapp.viewAdapter.ModulesCursorAdapter;

import Database.DBHelperDataSourceModules;

/**
 * Created by Jan on 13.12.2016.
 */

public class Tab3 extends Fragment {
    DBHelperDataSourceModules dataSourceModules;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);

fillListView();


        return rootView;
    }
public void fillListView(){
    ListView listViewModules = (ListView) getView().findViewById(R.id.listViewModules);
// Setup cursor adapter using cursor from last step
    ModulesCursorAdapter cursorAdapter = new ModulesCursorAdapter(getActivity(),dataSourceModules.getAllDataCursor() );
// Attach cursor adapter to the ListView
    listViewModules.setAdapter(cursorAdapter);
}



}
