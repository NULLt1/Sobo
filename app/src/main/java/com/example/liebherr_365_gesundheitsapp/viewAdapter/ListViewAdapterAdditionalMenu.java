package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.Database.DataMensaMenu;
import com.example.liebherr_365_gesundheitsapp.R;

import java.util.List;

public class ListViewAdapterAdditionalMenu extends BaseAdapter {
    private TextView item;
    private TextView price;
    private Context context;
    private LayoutInflater inflater;

    private List<DataMensaMenu> data;
    private DataMensaMenu itemData;

    //kommentar
    public ListViewAdapterAdditionalMenu(Context context, List<DataMensaMenu> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
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
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_item_mensa_additional_menu, parent, false);
        itemData = data.get(position);


        item = (TextView) itemView.findViewById(R.id.textViewAdditionMenuItem);
        price = (TextView) itemView.findViewById(R.id.textViewAdditionalMenuPrice);

        item.setText(itemData.getMenu());
        price.setText(itemData.getPrice());

        return itemView;
    }
}
