package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.Database.DataParsedData;
import com.example.liebherr_365_gesundheitsapp.R;

import java.util.List;

/**
 * Created by Jan on 12.06.2017.
 */

public class ListViewAdapterPushNews extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private List<DataParsedData> list;

    public ListViewAdapterPushNews(Context context, List<DataParsedData> list) {
        mContext = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_push_news, parent, false);

        TextView header = (TextView) itemView.findViewById(R.id.textViewHeaderPushNews);
        TextView text = (TextView) itemView.findViewById(R.id.textViewTextPushNews);

        header.setText(list.get(position).getTeaser());
        text.setText(list.get(position).getText());

        return itemView;
    }
}
