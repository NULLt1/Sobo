package com.example.liebherr_365_gesundheitsapp.Database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class MergeData {
    private Context mContext;
    private DataSourceMensa dataSourceMensa;
    private DataSourceParsedData dataSourceParsedData;

    public MergeData(Context context) {
        mContext = context;
        dataSourceMensa = new DataSourceMensa(mContext);
        dataSourceParsedData = new DataSourceParsedData(mContext);
    }

    public List<List<DataMergedData>> mergeData() {
        List<DataMensaMenu> mensaList;
        List<DataParsedData> dataList;
        List<DataMergedData> newList = new ArrayList<>();
        List<List<DataMergedData>> mergedList = new ArrayList<>();

        dataSourceMensa.open();
        mensaList = dataSourceMensa.getTodaysDataAsArrayList();
        dataSourceMensa.close();

        dataSourceParsedData.open();
        dataList = dataSourceParsedData.getAllNews();
        dataSourceParsedData.close();

        for (DataMensaMenu item : mensaList) {
            DataMergedData data = new DataMergedData("Mensa", item.getDate(), item.getHeader(), item.getMenu(), item.getPrice());
            newList.add(data);
        }

        mergedList.add(newList);

        for (DataParsedData item : dataList) {
            DataMergedData data = new DataMergedData("News", item.getDate(), item.getTeaser(), item.getText(), "");
            List<DataMergedData> newsList = new ArrayList<>();
            newsList.add(data);
            mergedList.add(newsList);
        }

        return mergedList;
    }
}
