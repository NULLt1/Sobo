package com.example.liebherr_365_gesundheitsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Niklas on 12.01.2017.
 */

public class Recording extends Activity{

    Button weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recording);

        weight = (Button) findViewById(R.id.a);

        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent weight = new Intent(Recording.this,ModulWeight.class);
                startActivity(weight);
            }
        });

    }


}
