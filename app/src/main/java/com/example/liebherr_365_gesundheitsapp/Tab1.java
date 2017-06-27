package com.example.liebherr_365_gesundheitsapp;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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

        final ListView listView = (ListView) rootView.findViewById(R.id.listViewTab1);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipelayout);

        mergeData = new MergeData(getContext());

        List<List<DataMergedData>> myList = mergeData.mergeData();
        final ListViewAdapterOverView adapter = new ListViewAdapterOverView(getContext(), myList);
        listView.setAdapter(adapter);

        TextView header = (TextView) rootView.findViewById(R.id.textViewOverViewHeader);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        header.setText("Aktuelle Infos für den " + dateFormat.format(date));

        //enable refresh layout only when list is at top of the screen
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (listView == null || listView.getChildCount() == 0) ? 0 : listView.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });

        //refresh data on swipe
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
