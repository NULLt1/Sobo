package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.example.liebherr_365_gesundheitsapp.MainMenu;
import com.example.liebherr_365_gesundheitsapp.R;
import com.example.liebherr_365_gesundheitsapp.Tab2;

import Database.DBHelperDataSourceModules;
import Database.ModulesQuery;

public class ModulesCursorAdapterSwitch extends CursorAdapter {
    private DBHelperDataSourceModules db;
    private Context mContext;


    public ModulesCursorAdapterSwitch(Context context, Cursor cursor) {
        super(context, cursor, 0);
        mContext=context;
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.selection_modules_row, parent, false);

    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {

        int position = (Integer) view.getTag();

        Log.d("position", String.valueOf(position));
        // Find fields to populate in inflated template
        TextView textViewModuleName = (TextView) view.findViewById(R.id.textViewModuleName);
        Switch switchModuleStatus = (Switch) view.findViewById(R.id.switchModuleStatus);

        // Extract properties from cursor
        final String modulName = cursor.getString(cursor.getColumnIndexOrThrow(ModulesQuery.getColumnName()));
        String modulFlag = cursor.getString(cursor.getColumnIndexOrThrow(ModulesQuery.getColumnFlag()));

        if (modulFlag.equals("true")) {
            switchModuleStatus.setChecked(true);
        } else {
            switchModuleStatus.setChecked(false);
        }

        switchModuleStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                // >>>> turn modul on <<<<
                if (isChecked) {
                    db = new DBHelperDataSourceModules(context);
                    db.open();
                    //call function changemodulstatus
                    db.changemodulstatus(modulName, true);
                    db.close();
                }
                // >>>> turn modul off <<<<
                else {
                    db = new DBHelperDataSourceModules(context);
                    db.open();
                    //call function changemodulstatus
                    db.changemodulstatus(modulName, false);
                    db.close();
                }
                db = new DBHelperDataSourceModules(context);
                    Cursor cursor = db.getSelectedDataCursor();
                    Tab2.gridViewAdapter.updateView(cursor);


            }
        });

        // Populate fields with extracted properties
        textViewModuleName.setText(modulName);
        //switchModuleStatus.setTag(cursor.getPosition());
    }

    @Override
    public View getView(int position, View convertview, ViewGroup arg2) {
        if (convertview == null) {
            LayoutInflater inflater = LayoutInflater.from(arg2.getContext());
            convertview = inflater.inflate(R.layout.selection_modules_row,
                    null);
        }
        convertview.setTag(position);
        return super.getView(position, convertview, arg2);
    }

}
