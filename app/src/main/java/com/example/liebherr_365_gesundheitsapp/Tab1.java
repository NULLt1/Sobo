package com.example.liebherr_365_gesundheitsapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liebherr_365_gesundheitsapp.ModulMensa.FragmentModulMensa;

public class Tab1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);

        FragmentManager childFragMan = getChildFragmentManager();
        FragmentTransaction chilFragTrans = childFragMan.beginTransaction();
        FragmentModulMensa fragmentModulMensa = new FragmentModulMensa();
        chilFragTrans.add(R.id.frameMensa, fragmentModulMensa);
        chilFragTrans.commit();

        return rootView;
    }
}
