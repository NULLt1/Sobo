package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.app.Activity;
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

import com.example.liebherr_365_gesundheitsapp.R;

import java.util.List;
import java.util.zip.Inflater;

import Database.ModulesQuery;

/**
 * Created by Jan on 18.12.2016.
 */

public class ModulesCursorAdapterSwitch extends CursorAdapter {
    public ModulesCursorAdapterSwitch(Context context, Cursor cursor) {
        super(context, cursor, 0);
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
    public void bindView(View view, Context context, Cursor cursor) {
        
        int position=(Integer) view.getTag();

        Log.d("position", String.valueOf(position));
        // Find fields to populate in inflated template
        TextView textViewModuleName = (TextView) view.findViewById(R.id.textViewModuleName);
        Switch switchModuleStatus = (Switch) view.findViewById(R.id.switchModuleStatus);

        // Extract properties from cursor
        final String modulName = cursor.getString(cursor.getColumnIndexOrThrow(ModulesQuery.getColumnName()));
        String modulFlag = cursor.getString(cursor.getColumnIndexOrThrow(ModulesQuery.getColumnFlag()));
        
        if (modulFlag.equals("true")) {
            switchModuleStatus.setChecked(true);
        }
        else
        {
            switchModuleStatus.setChecked(false);
        }

        switchModuleStatus.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                if (isChecked){
                    Log.d("***CHECK CHANGED***", modulName);
                }
            }
        }) ;

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
