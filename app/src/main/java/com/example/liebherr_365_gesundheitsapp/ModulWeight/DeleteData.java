package com.example.liebherr_365_gesundheitsapp.ModulWeight;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.Database.DBHelperDataSourceData;
import com.example.liebherr_365_gesundheitsapp.R;

import static com.example.liebherr_365_gesundheitsapp.ModulWeight.ModulWeight.adapter;


public class DeleteData extends DialogFragment {
    Context context;
    private DBHelperDataSourceData dataSourceData;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        // get context
        context = getActivity().getApplicationContext();

        // make dialog object
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // get the layout inflater
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflate our custom layout for the dialog to a View
        final View view = li.inflate(R.layout.deletedata, null);

        // inform the dialog it has a custom View
        builder.setView(view);


        // setOnClickListener on Button yes
        Button buttonyes = (Button) view.findViewById(R.id.yes);
        buttonyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // bind diagrammbutton to Button
                Button diagrammbutton = (Button) getActivity().findViewById(R.id.viewgraph);

                // bind deletebutton to Button
                Button historiebutton = (Button) getActivity().findViewById(R.id.historie);

                // bind textweightdiffernce to TextView
                TextView textweightdifference = (TextView) getActivity().findViewById(R.id.weightdifference);

                // bind textweightstart to TextView
                TextView textweightstart = (TextView) getActivity().findViewById(R.id.firstweight);

                // bind weightlist to Listview
                ListView weightlist = (ListView) getActivity().findViewById(R.id.listview);

                // new DBHelperDataSource
                dataSourceData = new DBHelperDataSourceData(context);
                dataSourceData.open();

                //call function deletedb
                String ModulWeight = "ModulWeight";
                dataSourceData.deletedb(ModulWeight);

                // defaultstring
                String defaultstring = "-.-";

                // set text textweightstart
                textweightstart.setText(String.valueOf(defaultstring));

                // set text textweightdifference
                textweightdifference.setText(defaultstring);

                // set deletebutton disabled and change opacity
                diagrammbutton.setEnabled(false);
                diagrammbutton.getBackground().setAlpha(45);
                diagrammbutton.setTextColor(getResources().getColor(R.color.colorLightGrey));

                // set deletebutton disabled and change opacity
                historiebutton.setEnabled(false);
                historiebutton.getBackground().setAlpha(45);
                historiebutton.setTextColor(getResources().getColor(R.color.colorLightGrey));

                // weightlist adapter
                adapter.changeCursor(dataSourceData.getPreparedCursorForWeightList());

                Log.d("closesql", "<DATA>Die Datenquelle wird geschlossen.<DATA>");
                dataSourceData.close();

                //close NumberPickerModulWeight
                getDialog().dismiss();
            }
        });

        // setOnClickListener on Button no
        Button buttonno = (Button) view.findViewById(R.id.no);
        buttonno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("No", "No");

                //close NumberPickerModulWeight
                getDialog().dismiss();
            }
        });
        return view;
    }
}

    /*
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


                        //TODO: Refresh List

                        dialog.dismiss();
                    }
                });

        return alertDialogBuilder.create();
    }
   */

