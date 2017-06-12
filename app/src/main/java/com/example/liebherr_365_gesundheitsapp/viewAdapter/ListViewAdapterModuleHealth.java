package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.Database.DataHealthCare;
import com.example.liebherr_365_gesundheitsapp.R;

import java.util.List;


/**
 * Created by Melissa Hug on 04.05.2017.
 */

public class ListViewAdapterModuleHealth extends BaseAdapter {
    LayoutInflater inflater;
    private Context context;
    private List<List<DataHealthCare>> data;
    String zeitraum = "Oktober - Ostern\n" +
            "Dienstag,\n" +
            "17:30 Uhr - 18:30 Uhr";
    String ort = "Ehingen";
    String kosten = "50€";
    String status = "ausgebucht";
    String titel = "Spinning";

    public ListViewAdapterModuleHealth(Context context, List<List<DataHealthCare>> list) {
        this.context = context;
        data = list;
    }

    @Override
    public int getCount() {
        return data.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        List<DataHealthCare> list = data.get(position);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item_modul_health, viewGroup, false);
        TextView textViewtitle = new TextView(context);

        TableLayout tableLayout = (TableLayout) itemView.findViewById(R.id.tableLayoutHealthCare);
        TableRow ll = (TableRow) itemView.findViewById(R.id.tr);
        TableRow vl = (TableRow) itemView.findViewById(R.id.tt);

        TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f);
        TableRow.LayoutParams params3 = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        textViewtitle.setText(list.get(0).getCourse());
        textViewtitle.setLayoutParams(params);
        textViewtitle.setTextColor(Color.parseColor("#6cc0ae"));
        textViewtitle.setGravity(Gravity.CENTER);
        textViewtitle.setTextSize(18);
        vl.addView(textViewtitle);

        for (int i = 0; i < list.size(); i++) {
            TableRow tableRow = new TableRow(context);

            tableRow.setLayoutParams(params3);
            tableLayout.addView(tableRow);
            TextView textViewperiod = new TextView(context);
            TextView textViewlocation = new TextView(context);
            TextView textViewprice = new TextView(context);
            TextView textViewstatus = new TextView(context);

            textViewperiod.setText(list.get(i).getDate());
            tableRow.addView(textViewperiod);


            textViewlocation.setText(list.get(i).getVenue());
            textViewlocation.setLayoutParams(params2);
            tableRow.addView(textViewlocation);

            textViewprice.setText(list.get(i).getPrice());
            textViewprice.setLayoutParams(params2);
            tableRow.addView(textViewprice);

            textViewstatus.setText(list.get(i).getStatus());
            textViewstatus.setLayoutParams(params2);
            tableRow.addView(textViewstatus);
        }
        return itemView;
    }
}
