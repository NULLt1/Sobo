package com.example.liebherr_365_gesundheitsapp.ModulWeight;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.Database.DBHelperDataSourceData;
import com.example.liebherr_365_gesundheitsapp.R;

public class DeleteData extends DialogFragment {
    Context context;
    private DBHelperDataSourceData dataSourceData;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get context
        context = getActivity().getApplicationContext();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("Achtung!");
        alertDialogBuilder.setMessage("Möchten sie alle Daten löschen?");

        alertDialogBuilder.setNegativeButton("Nein",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int d) {
                        //action
                        dialog.dismiss();
                    }
                });

        alertDialogBuilder.setPositiveButton("Ja",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // new DBHelperDataSource
                        dataSourceData = new DBHelperDataSourceData(context);
                        dataSourceData.open();

                        //call function deletedb
                        String ModulWeight = "ModulWeight";
                        dataSourceData.deletedb(ModulWeight);

                        Log.d("closesql", "<DATA>Die Datenquelle wird geschlossen.<DATA>");
                        dataSourceData.close();

                        // bind textweightdiffernce to TextView
                        TextView textweightdifference = (TextView) getActivity().findViewById(R.id.weightdifference);

                        // bind textweightstart to TextView
                        TextView textweightstart = (TextView) getActivity().findViewById(R.id.firstweight);

                        String defaultstring = "-.-";

                        // set text textweightstart
                        textweightstart.setText(String.valueOf(defaultstring));

                        // set text textweightdifference
                        textweightdifference.setText(defaultstring);

                        dialog.dismiss();
                    }
                });

        return alertDialogBuilder.create();
    }
}
