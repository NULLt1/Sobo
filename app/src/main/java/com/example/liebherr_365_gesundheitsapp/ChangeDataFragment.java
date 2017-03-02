package com.example.liebherr_365_gesundheitsapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;

import Database.DBHelperDataSourceData;
import Database.Data;

public class ChangeDataFragment extends DialogFragment {
    Context context;
    private DBHelperDataSourceData dataSourceData;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get context
        context = getActivity().getApplicationContext();

        // initialize bundle
        final Bundle bundle = this.getArguments();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("Achtung!");
        alertDialogBuilder.setMessage("Zu diesem Datum existiert schon ein Gewicht");
        alertDialogBuilder.setPositiveButton("Ändern",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get values from bundle
                        String modulweight = bundle.getString("modul");
                        int day = bundle.getInt("day", 0);
                        int month = bundle.getInt("month", 0);
                        int year = bundle.getInt("year", 0);
                        float weight = bundle.getFloat("weight", 0);
                        String type = bundle.getString("type");

                        // formate date
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String formateddate = sdf.format(new Date(year, month, day));

                        // new weightdateobject with values
                        Data wd = new Data(modulweight, formateddate, weight, type);

                        // new DBHelperDataSource
                        dataSourceData = new DBHelperDataSourceData(context);
                        dataSourceData.open();

                        Log.d("Ausgbabe", "Ändern");
                        //call function updatedata
                        dataSourceData.updatedata(wd);

                        Log.d("closesql", "<DATA>Die Datenquelle wird geschlossen.<DATA>");
                        dataSourceData.close();
                        //TODO: REFRESH LIST

                        dialog.dismiss();
                    }
                });

        alertDialogBuilder.setNegativeButton("Abbruch",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int d) {
                        //action
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        return alertDialogBuilder.create();
    }
}
