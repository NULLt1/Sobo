package com.example.liebherr_365_gesundheitsapp;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
    Parser parser;
    MergeData mergeData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);

        parser = new Parser(getContext());
        parser.pullData();

        ListView listView = (ListView) rootView.findViewById(R.id.listViewTab1);

        mergeData = new MergeData(getContext());

        List<List<DataMergedData>> myList = mergeData.mergeData();
        final ListViewAdapterOverView adapter = new ListViewAdapterOverView(getContext(), myList);
        listView.setAdapter(adapter);

        TextView header = (TextView) rootView.findViewById(R.id.textViewOverViewHeader);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        header.setText("Aktuelle Infos für den " + dateFormat.format(date));


        //refresh data on swipe
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipelayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                parser.pullData();
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateResults(mergeData.mergeData());
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);


            }
        });

        return rootView;
    }
}
