package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.Database.DataMergedData;
import com.example.liebherr_365_gesundheitsapp.R;

import java.util.List;

/**
 * Created by Jan on 13.06.2017.
 */

public class ListViewAdapterOverView extends BaseAdapter {
    private List<List<DataMergedData>> list;
    private List<DataMergedData> data;
    private Context mContext;
    private LayoutInflater inflater;

    public ListViewAdapterOverView(Context context, List<List<DataMergedData>> list) {
        mContext = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
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
        View itemView = inflater.inflate(R.layout.item_tab_1, parent, false);

        data = list.get(position);


        ImageView icon = (ImageView) itemView.findViewById(R.id.imageViewOverViewIcon);
        if(data.get(0).getModul().equals("Kantine")) {
            icon.setImageResource(R.drawable.vector_modulmensa);
        }
        LinearLayout container = (LinearLayout) itemView.findViewById(R.id.linearLayoutOverViewContainer);


        //Parameter for layout
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        LinearLayout.LayoutParams lpTextView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lpPrice = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

        for (DataMergedData item : data) {

            LinearLayout innerLayout = new LinearLayout(mContext);
            LinearLayout outerLayout = new LinearLayout(mContext);
            innerLayout.setLayoutParams(lp);
            outerLayout.setLayoutParams(lp);

            outerLayout.setOrientation(LinearLayout.HORIZONTAL);
            innerLayout.setOrientation(LinearLayout.VERTICAL);

            container.addView(outerLayout);
            outerLayout.addView(innerLayout);

            TextView header = new TextView(mContext);
            TextView text = new TextView(mContext);
            TextView price = new TextView(mContext);

            header.setLayoutParams(lpTextView);
            text.setLayoutParams(lpTextView);
            price.setLayoutParams(lpPrice);

            innerLayout.addView(header);
            innerLayout.addView(text);
            outerLayout.addView(price);

            header.setText(item.getHeader());
            text.setText(item.getText());
            price.setText(item.getPrice());

            header.setTypeface(Typeface.DEFAULT_BOLD);
            price.setTypeface(Typeface.DEFAULT_BOLD);

        }

        return itemView;
    }
}
