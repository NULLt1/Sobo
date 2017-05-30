package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import android.widget.TableRow;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.R;


/**
 * Created by Melissa Hug on 04.05.2017.
 */

public class ListViewAdapterModuleHealth extends BaseAdapter {
    LayoutInflater inflater;
    private Context context;
    String zeitraum = "Oktober - Ostern\n" +
            "Dienstag,\n"+
            "17:30 Uhr - 18:30 Uhr";
    String ort = "Ehingen";
    String kosten = "50€";
    String status = "ausgebucht";
    String titel = "Spinning";

    public ListViewAdapterModuleHealth(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item_modul_health, viewGroup, false);


        TableRow ll =(TableRow) itemView.findViewById(R.id.tr);
        TableRow vl =(TableRow) itemView.findViewById(R.id.tt);

        TextView textViewperiod = new TextView(context);
        TextView textViewlocation = new TextView(context);
        TextView textViewprice = new TextView(context);
        TextView textViewstatus = new TextView(context);
        TextView textViewtitle = new TextView(context);

        TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,0.5f);

        textViewtitle.setText(titel);
        textViewtitle.setLayoutParams(params);
        textViewtitle.setTextColor(Color.parseColor("#6cc0ae"));
        textViewtitle.setGravity(Gravity.CENTER);
        textViewtitle.setTextSize(18);
        vl.addView(textViewtitle);


        textViewperiod.setText(zeitraum);
        textViewperiod.setLayoutParams(params);
        ll.addView(textViewperiod);


        textViewlocation.setText(ort);
        textViewlocation.setLayoutParams(params2);
        ll.addView(textViewlocation);

        textViewprice.setText(kosten);
        textViewprice.setLayoutParams(params2);
        ll.addView(textViewprice);

        textViewstatus.setText(status);
        textViewstatus.setLayoutParams(params2);
        ll.addView(textViewstatus);

        return itemView;
    }
}
