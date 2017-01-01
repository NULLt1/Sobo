package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.R;

import Database.ModulesQuery;

/**
 * Created by Jan Bussmann on 27.12.2016.
 */

public class ModulesCursorAdapterButtons  extends CursorAdapter{
    public ModulesCursorAdapterButtons(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.selection_modules_row, parent, false);
    }
    //dffsdfds
    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find fields to populate in inflated template
        Button button = (Button) view.findViewById(R.id.buttonModulSelection);

        // Extract properties from cursor
        String modulName = cursor.getString(cursor.getColumnIndexOrThrow(ModulesQuery.getColumnName()));
        String modulFlag = cursor.getString(cursor.getColumnIndexOrThrow(ModulesQuery.getColumnFlag()));
        if (modulFlag.equals("true")) {
            button.setText(modulName);
        }

    }
}
