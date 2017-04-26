package com.example.liebherr_365_gesundheitsapp.viewAdapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.liebherr_365_gesundheitsapp.Database.Queries;

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private Cursor mCursor;

    public GridViewAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    //sd
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
        final String modulPath = mCursor.getString(mCursor.getColumnIndexOrThrow(Queries.COLUMN_MODUL));
        int id = mContext.getResources().getIdentifier("vector_" + modulPath.toLowerCase(), "drawable", mContext.getPackageName());

        GridViewItem imageView = new GridViewItem(mContext);
        imageView.setImageResource(id);
        imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        imageView.setBackgroundColor(Color.parseColor("#CBCBCB"));
        imageView.setPadding(50, 50, 50, 50);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    void updateView(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }
}
