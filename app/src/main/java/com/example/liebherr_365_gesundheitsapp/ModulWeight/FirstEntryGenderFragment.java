package com.example.liebherr_365_gesundheitsapp.ModulWeight;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import com.anton46.stepsview.StepsView;
import com.example.liebherr_365_gesundheitsapp.R;

public class FirstEntryGenderFragment extends DialogFragment {
    private Context context;
    private int gender = 1;
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
        View view = li.inflate(R.layout.firstentrygenderfragment, null);

        // get StepsView
        StepsView mStepsView = (StepsView) view.findViewById(R.id.stepsView);

        //style StepsView
        mStepsView.setLabels(labels)
                .setBarColorIndicator(Color.parseColor("#b2acac"))
                .setProgressColorIndicator(Color.parseColor("#64c5b1"))
                .setLabelColorIndicator(Color.parseColor("#64c5b1"))
                .setCompletedPosition(3)
                .drawView();

        RadioGroup rg = (RadioGroup) view.findViewById(R.id.gender);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.man:
                        gender = 1;
                        break;
                    case R.id.woman:
                        gender = 0;
                        break;
                }
            }
        });

        // setOnClickListener on Button später
        Button späterbutton = (Button) view.findViewById(R.id.weiter);
        späterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavedSharedPrefrencesModulWeight.setGender(gender);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("gender", String.valueOf(gender));
                editor.apply();

                // update Bmi
                BmiCalculator.setRecBmi();
                BmiCalculator.calculateBmi(context);
                ModulWeight.setWeightGoalText();

                // deactivate firstentry
                SavedSharedPrefrencesModulWeight.setFirstentry(false);

                //close NumberPickerFragment
                getDialog().dismiss();
            }
        });

        // inform the dialog it has a custom View
        builder.setView(view);

        return view;
    }
}