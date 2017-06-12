package com.example.liebherr_365_gesundheitsapp.ModulMensa;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.liebherr_365_gesundheitsapp.Database.DataMensaMenu;
import com.example.liebherr_365_gesundheitsapp.Database.DataParsedData;
import com.example.liebherr_365_gesundheitsapp.Database.DataSourceMensa;
import com.example.liebherr_365_gesundheitsapp.Database.DataSourceParsedData;
import com.example.liebherr_365_gesundheitsapp.R;
import com.example.liebherr_365_gesundheitsapp.XMLParser.Parser;
import com.example.liebherr_365_gesundheitsapp.viewAdapter.ListViewAdapterMensa;

import java.util.List;


public class FragmentModulMensa extends Fragment {
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_fragment_modul_mensa, container, false);
        ListView listview = (ListView) rootView.findViewById(R.id.listViewFragmentMensa);
        Parser parser = new Parser(getContext());

        DataSourceMensa dataSource = new DataSourceMensa(getContext());
        dataSource.open();
        ListViewAdapterMensa adapter = new ListViewAdapterMensa(getContext(), dataSource.getTodaysDataAsArrayList());
        parser.pullData();
        Log.d("*********", "onCreateView: ");
        List<DataMensaMenu> list = dataSource.getDataForWeek(0);

        dataSource.close();


        listview.setAdapter(adapter);
        return rootView;
    }
}

