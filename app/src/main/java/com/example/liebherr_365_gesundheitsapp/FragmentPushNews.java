package com.example.liebherr_365_gesundheitsapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.liebherr_365_gesundheitsapp.Database.DataParsedData;
import com.example.liebherr_365_gesundheitsapp.Database.DataSourceParsedData;
import com.example.liebherr_365_gesundheitsapp.viewAdapter.ListViewAdapterPushNews;

import java.util.List;


/**
 * Created by Jan on 12.06.2017.
 */

public class FragmentPushNews extends Fragment {
    private View rootView;
    private ListViewAdapterPushNews adapter;
    private List<DataParsedData> list;
    private DataSourceParsedData dataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        dataSource = new DataSourceParsedData(getContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_push_news, container, false);
        ListView listview = (ListView) rootView.findViewById(R.id.listViewPushNews);

        dataSource.open();
        list = dataSource.getAllNews();
        adapter = new ListViewAdapterPushNews(getContext(), list);
        dataSource.close();

        listview.setAdapter(adapter);
        return rootView;
    }
}
