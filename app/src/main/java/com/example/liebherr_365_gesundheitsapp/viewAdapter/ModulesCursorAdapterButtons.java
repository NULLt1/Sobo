package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;

import com.example.liebherr_365_gesundheitsapp.R;

import Database.ModulesQuery;

public class ModulesCursorAdapterButtons  extends CursorAdapter{
    private Context FinalContext;
    public ModulesCursorAdapterButtons(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.buttons_modules_row, parent, false);
    }
    //dffsdfds
    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        this.FinalContext=context;
        // Find fields to populate in inflated template
        Button button = (Button) view.findViewById(R.id.buttonModulSelection);

        // Extract properties from cursor
        String modulName = cursor.getString(cursor.getColumnIndexOrThrow(ModulesQuery.getColumnName()));
        Log.d("***Modul***", modulName);
        final String modulPath = cursor.getString(cursor.getColumnIndexOrThrow(ModulesQuery.getColumnModul()));
        Log.d("MODULPATH", modulPath);
        try {
          final Class<?>  act = Class.forName("com.example.liebherr_365_gesundheitsapp."+modulPath);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(FinalContext, act);
                    FinalContext.startActivity(intent);
                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        button.setText(modulName);
    }
}
