package com.example.liebherr_365_gesundheitsapp.ModulWeight;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.example.liebherr_365_gesundheitsapp.Database.DBHelperDataSourceData;
import com.example.liebherr_365_gesundheitsapp.Database.Data;
import com.example.liebherr_365_gesundheitsapp.R;

import static com.example.liebherr_365_gesundheitsapp.ModulWeight.ModulWeight.adapter;

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

                        //call function updatedata
                        dataSourceData.updatedata(wd);

                        // bind textweightdiffernce to TextView
                        TextView textweightdifference = (TextView) getActivity().findViewById(R.id.weightdifference);

                        // bind textweightstart to TextView
                        TextView textweightactual = (TextView) getActivity().findViewById(R.id.firstweight);

                        // set text textweightactual
                        textweightactual.setText(String.valueOf(weight));

                        // call function calculateweightdifference
                        String weightdifferncestring = calculateweightdifference(weight);

                        // set text textweightdifference
                        textweightdifference.setText(weightdifferncestring);

                        // weightlist adapter
                        adapter.changeCursor(dataSourceData.getPreparedCursorForWeightList());

                        Log.d("closesql", "<DATA>Die Datenquelle wird geschlossen.<DATA>");
                        dataSourceData.close();

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

    // function calculateweightdifference
    private String calculateweightdifference(float weight) {
        String weightdifferncestring;
        float weightdiffernce;
        float weightgoal = ModulWeight.getWeightGoal();

        if (weight == weightgoal) {
            weightdifferncestring = "0.0 kg";
        } else if (weight < weightgoal) {
            weightdiffernce = weightgoal - weight;
            //call function roundfloat
            weightdiffernce = roundfloat(weightdiffernce);
            weightdifferncestring = "+ " + String.valueOf(weightdiffernce) + " kg";
        } else {
            weightdiffernce = weight - weightgoal;
            //call function roundfloat
            weightdiffernce = roundfloat(weightdiffernce);
            weightdifferncestring = "- " + String.valueOf(weightdiffernce) + " kg";
        }
        return weightdifferncestring;
    }

    // function roundfloat
    private float roundfloat(float inputfloat) {
        float roundedfloat = 0;
        inputfloat += 0.05;
        inputfloat = (int) (inputfloat * 10);
        roundedfloat = inputfloat / 10;
        return roundedfloat;
    }
}
