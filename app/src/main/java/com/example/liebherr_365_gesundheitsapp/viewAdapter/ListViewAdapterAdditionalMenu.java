package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.ModulMensa.ModulMensa;
import com.example.liebherr_365_gesundheitsapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapterAdditionalMenu extends BaseAdapter {
    private TextView item;
    private TextView price;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> data;
    private HashMap<String, String> resultp = new HashMap<>();

//kommentar
    public ListViewAdapterAdditionalMenu(Context context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
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
        resultp = data.get(position);
        Log.d(resultp.get(ModulMensa.PRICE), "getView: ");

        item = (TextView) itemView.findViewById(R.id.textViewAdditionMenuItem);
        price = (TextView) itemView.findViewById(R.id.textViewAdditionalMenuPrice);

        item.setText(resultp.get(ModulMensa.ADDITIONALMENUITEM));
        price.setText(resultp.get(ModulMensa.PRICE));

        return itemView;
    }
}
