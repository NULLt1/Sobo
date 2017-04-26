package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.Database.DataParsedData;
import com.example.liebherr_365_gesundheitsapp.R;

import java.util.List;

public class TipOfTheDayAdapter extends BaseAdapter {
    private List<DataParsedData> dataList;
    private Context mContext;
    private DataParsedData data;
    private LayoutInflater inflater;

    public TipOfTheDayAdapter(Context context, List<DataParsedData> dataList) {
        this.dataList = dataList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_item_tip_of_the_day, parent, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewModuleIcon);
        TextView textViewTeaser = (TextView) itemView.findViewById(R.id.textViewTipOfTheDayTeaser);
        TextView textViewText = (TextView) itemView.findViewById(R.id.textViewTipOfTheDayText);

        data = dataList.get(position);

        imageView.setImageResource(mContext.getResources().getIdentifier("vector_" + data.getModul().toLowerCase(), "drawable", mContext.getPackageName()));
        textViewTeaser.setText(data.getTeaser());
        textViewText.setText(data.getText());
        return itemView;
    }

    public void updateData(List<DataParsedData> arrayList) {
        this.dataList = arrayList;
    }
}
