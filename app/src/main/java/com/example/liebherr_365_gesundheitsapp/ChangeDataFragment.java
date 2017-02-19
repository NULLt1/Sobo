package com.example.liebherr_365_gesundheitsapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import Database.DBHelperDataSourceData;

/**
 * Created by mpadmin on 19.02.2017.
 */

public class ChangeDataFragment extends DialogFragment {
    private DBHelperDataSourceData dataSourceData;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("Achtung!");
        alertDialogBuilder.setMessage("Zu diesem Datum existiert schon ein Gewicht");
        alertDialogBuilder.setPositiveButton("Ändern",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //TODO: GEWICHT ÜBERSCHREIBEN
                        //action
                        Log.d("Ausgbabe", "Ändern");
                        //call function updatedata
                        //dataSourceData.updatedata(wd);
                        Log.d("closesql", "<DATA>Die Datenquelle wird geschlossen.<DATA>");
                        //dataSourceData.close();

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
