package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.ModulMensa.ModulMensa;
import com.example.liebherr_365_gesundheitsapp.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jan on 14.03.2017.
 */

public class ListViewAdapterMensa extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, ArrayList<String>>> data;
    ArrayList<String> arrayListDay = new ArrayList<>();
    ArrayList<String> arrayListHeader = new ArrayList<>();
    ArrayList<String> arrayListPrice = new ArrayList<>();
    ArrayList<String> arrayListMenu = new ArrayList<>();
    HashMap<String, ArrayList<String>> resultp = new HashMap<>();


    public ListViewAdapterMensa(Context context, ArrayList<HashMap<String, ArrayList<String>>> arraylist) {
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
        TextView day;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_item_mensa, parent, false);
        resultp = data.get(position);

        LinearLayout verticalLayout = (LinearLayout) itemView.findViewById(R.id.modulMensaLinearLayoutVertical);

        day = (TextView) itemView.findViewById(R.id.textViewMensaDay);
        arrayListDay = resultp.get(ModulMensa.DAY);
        day.setText(arrayListDay.get(0));

        arrayListHeader=resultp.get(ModulMensa.HEADER);
        arrayListPrice = resultp.get(ModulMensa.PRICE);
        arrayListMenu = resultp.get(ModulMensa.MENU);

        for (int i = 0; i < arrayListPrice.size(); i++) {
            RelativeLayout relativeLayout = new RelativeLayout(context);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            verticalLayout.addView(relativeLayout, lp);

            TextView textViewprice = new TextView(context);
            TextView textViewheader = new TextView(context);
            TextView textViewMenu = new TextView(context);

            textViewheader.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            textViewheader.setTypeface(Typeface.DEFAULT_BOLD);
            RelativeLayout.LayoutParams paramsmenu = (RelativeLayout.LayoutParams) textViewheader.getLayoutParams();
            paramsmenu.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            textViewheader.setLayoutParams(paramsmenu);
            textViewheader.setText(arrayListHeader.get(i));


            textViewprice.setLayoutParams(new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textViewprice.setTypeface(Typeface.DEFAULT_BOLD);
            RelativeLayout.LayoutParams paramsprice = (RelativeLayout.LayoutParams) textViewprice.getLayoutParams();
            paramsprice.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            textViewprice.setLayoutParams(paramsprice);
            textViewprice.setText(arrayListPrice.get(i));



            textViewMenu.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            textViewMenu.setText(arrayListMenu.get(i));

            relativeLayout.addView(textViewheader);
            relativeLayout.addView(textViewprice);
            verticalLayout.addView(textViewMenu);

        }
        return itemView;
    }
    public void updateResults(ArrayList<HashMap<String, ArrayList<String>>> data){
        this.data=data;
        notifyDataSetChanged();
        Log.d("es wurde ausgeführt", "updateResults: ");
    }
}
