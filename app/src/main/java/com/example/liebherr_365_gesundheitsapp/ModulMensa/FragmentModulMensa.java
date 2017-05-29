package com.example.liebherr_365_gesundheitsapp.ModulMensa;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.liebherr_365_gesundheitsapp.R;
import com.example.liebherr_365_gesundheitsapp.XMLParser.Parser;


public class FragmentModulMensa extends Fragment {
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_fragment_modul_mensa, container, false);

        Parser parser = new Parser(getContext());

        parser.pullData();

        return rootView;
    }
}

