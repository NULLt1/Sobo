package com.example.liebherr_365_gesundheitsapp;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Niklas on 27.03.2017.
 */

public class RecordingModulWeight  extends DialogFragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.recording_modul_weight, null);
    }

    public void showDialog(View v) {
    }

}

