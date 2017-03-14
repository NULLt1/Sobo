package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import Database.ModulesQuery;

/**
 * Created by Jan on 11.01.2017.
 */

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private Cursor mCursor;

    public GridViewAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return mCursor.moveToPosition(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mCursor.moveToPosition(position);
        final String modulPath = mCursor.getString(mCursor.getColumnIndexOrThrow(ModulesQuery.getColumnModul()));
        int id = mContext.getResources().getIdentifier("vector_"+modulPath.toLowerCase(),"drawable",mContext.getPackageName());

        GridViewItem imageView = new GridViewItem(mContext);
        imageView.setImageResource(id);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        imageView.setBackgroundColor(Color.parseColor("#CBCBCB"));
        imageView.setPadding(100,100,100,100);
        return imageView;
    }
public void updateView(Cursor cursor){
    mCursor=cursor;
    notifyDataSetChanged();
}
}
