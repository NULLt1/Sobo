package com.example.liebherr_365_gesundheitsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.example.liebherr_365_gesundheitsapp.viewAdapter.ModulesCursorAdapterSwitch;

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

        fillListView(rootView);
        int position = (Integer) getView().getTag();
         Switch mySwitch = (Switch) getView().findViewWithTag(position);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    //TODO: Switch Funktion einbauen
                } else {

                }

            }});
        return rootView;
    }

    public void fillListView(View view) {
        dataSourceModules = new DBHelperDataSourceModules(getActivity());
        dataSourceModules.open();
        ListView listViewModules = (ListView) view.findViewById(R.id.listViewModules);
// Setup cursor adapter using cursor from last step
        ModulesCursorAdapterSwitch cursorAdapter = new ModulesCursorAdapterSwitch(getActivity(), dataSourceModules.getAllDataCursor());
// Attach cursor adapter to the ListView
        listViewModules.setAdapter(cursorAdapter);
        dataSourceModules.close();
    }


}
