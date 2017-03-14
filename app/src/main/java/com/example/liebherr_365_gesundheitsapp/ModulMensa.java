package com.example.liebherr_365_gesundheitsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;





public class ModulMensa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modul_mensa);
        TextView textView= (TextView) findViewById(R.id.textView5);
        new XmlParser(textView).execute();

    }
}
