package com.example.liebherr_365_gesundheitsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

        //Intialize integer and aftkomma as numberpicker to use functions
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
    }

    //function saveweight onklick @+id/saveButton
    public void saveweight(View view) {
        NumberPicker integer = (NumberPicker) findViewById(R.id.integer);
        NumberPicker afterkomma = (NumberPicker) findViewById(R.id.afterkomma);

        // get values of Numberpicker
        int integervalue = integer.getValue();
        int afterkommavalue = afterkomma.getValue();

        // call function integertofloat
        float weight = integertofloat(integervalue, afterkommavalue);

        //display weight on console
        Log.d("weight", String.valueOf(weight));

        /********************************/
        /*** next Step Save variables ***/
        /********************************/
    }

    //function integer values -> float integervalue,afterkommavalue
    public float integertofloat(int integervalue, int afterkommavalue) {
        float result = 0;
        result += (float) integervalue;
        result += ((float) afterkommavalue / 10);
        return result;
    }
}
