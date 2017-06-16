package com.example.liebherr_365_gesundheitsapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.Database.DataMergedData;
import com.example.liebherr_365_gesundheitsapp.Database.MergeData;
import com.example.liebherr_365_gesundheitsapp.XMLParser.Parser;
import com.example.liebherr_365_gesundheitsapp.viewAdapter.ListViewAdapterOverView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Tab1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);

        Parser parser = new Parser(getContext());
        parser.pullData();

        ListView listView = (ListView) rootView.findViewById(R.id.listViewTab1);

        MergeData mergeData = new MergeData(getContext());
        List<List<DataMergedData>> myList = mergeData.mergeData();
        ListViewAdapterOverView adapter = new ListViewAdapterOverView(getContext(), myList);
        listView.setAdapter(adapter);

        TextView header = (TextView) rootView.findViewById(R.id.textViewOverViewHeader);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        header.setText("Aktuelle Infos für den " + dateFormat.format(date));
        return rootView;
    }
}
