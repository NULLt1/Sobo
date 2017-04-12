package com.example.liebherr_365_gesundheitsapp.ModulMensa;



import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;;

import com.example.liebherr_365_gesundheitsapp.Parser.XMLParserModulMensa;
import com.example.liebherr_365_gesundheitsapp.R;

import java.io.IOException;

public class ModulMensa extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modul_mensa);
        XMLParserModulMensa parser= new XMLParserModulMensa(this);
        parser.execute();
        try {
            parser.getDataAsArrayList(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(savedInstanceState==null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction= fm.beginTransaction();
            transaction.replace(R.id.frame_layout_modul_mensa,new FragmentListModulMensaDiet()).commit();
        }
    }


}
