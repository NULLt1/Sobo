package com.example.liebherr_365_gesundheitsapp.ModulWeight;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anton46.stepsview.StepsView;
import com.example.liebherr_365_gesundheitsapp.R;

public class FirstEntryTextFragment extends DialogFragment {
    private Context context;
    private final String[] labels = {"     1", "     2", "     3", "     4"};

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
        View view = li.inflate(R.layout.firstentrytextfragment, null);


        StepsView mStepsView = (StepsView) view.findViewById(R.id.stepsView);

        mStepsView.setLabels(labels)
                .setBarColorIndicator(Color.parseColor("#636161"))
                .setProgressColorIndicator(Color.parseColor("#636161"))
                .setLabelColorIndicator(Color.parseColor("#636161"))
                .setCompletedPosition(0)
                .drawView();

        // inform the dialog it has a custom View
        builder.setView(view);

        return view;
    }
}
