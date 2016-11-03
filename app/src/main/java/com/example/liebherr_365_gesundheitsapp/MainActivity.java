package com.example.liebherr_365_gesundheitsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    //       .-..-..---. .-..-.
    //       : :; :: .--': :: :
    //       :    :: `;  : :: :
    //       : :: :: :   : :; :
    //       :_;:_;:_;   `.__.'
    //
    // This project was developed during our project studies.
    // Wintersemster 16/17
    // Bussmann    Jan
    // Hug         Melissa
    // Otec        Marvin
    // Speer       Christopher
    // Wangler     Niklas


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intialize integer and aftkomma as numberpicker to use funktions
        NumberPicker integer = (NumberPicker) findViewById(R.id.integer);
        NumberPicker afterkomma = (NumberPicker) findViewById(R.id.afterkomma);

        //Set interger Value 40-100
        integer.setMinValue(40);
        integer.setMaxValue(100);

        //Set afterkomma Value 0-9
        afterkomma.setMinValue(0);
        afterkomma.setMaxValue(9);

        //wrap@ getMinValue() || getMaxValue()
        integer.setWrapSelectorWheel(false);
        afterkomma.setWrapSelectorWheel(false);


    }

    /********************************/
    /*** next Step Save variables ***/
    /********************************/
}
