package com.example.liebherr_365_gesundheitsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.liebherr_365_gesundheitsapp.Database.DataSourceModules;
import com.example.liebherr_365_gesundheitsapp.viewAdapter.ModulesCursorAdapterSwitch;

public class Tab3 extends Fragment {
    DataSourceModules dataSourceModules;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);

        fillListView(rootView);

        return rootView;
    }

    public void fillListView(View view) {
        dataSourceModules = new DataSourceModules(getActivity());
        dataSourceModules.open();
        ListView listViewModules = (ListView) view.findViewById(R.id.listViewModules);

        // Setup cursor adapter using cursor from last step
        ModulesCursorAdapterSwitch cursorAdapter = new ModulesCursorAdapterSwitch(getActivity(), dataSourceModules.getAllDataCursor());

        // Attach cursor adapter to the ListView
        listViewModules.setAdapter(cursorAdapter);
        dataSourceModules.close();


    }

}
