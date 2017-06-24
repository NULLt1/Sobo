package com.example.liebherr_365_gesundheitsapp.ModulCoffee;


import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.liebherr_365_gesundheitsapp.Database.DataSourceData;
import com.example.liebherr_365_gesundheitsapp.R;

public class DeleteDataCoffee extends DialogFragment {
    Context context;
    private DataSourceData dataSourceData;

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
                // call function resetGlassCounter
                ModulCoffee.resetGlassCounter();

                // new DBHelperDataSource
                dataSourceData = new DataSourceData(context);
                dataSourceData.open();

                //call function deletedb
                String ModulWeight = "ModulCoffee";
                dataSourceData.deletedb(ModulWeight);

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


