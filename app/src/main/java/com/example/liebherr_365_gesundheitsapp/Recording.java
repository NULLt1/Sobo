package com.example.liebherr_365_gesundheitsapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Niklas on 12.01.2017.
 */

public class Recording extends Activity{

   private Button weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recording);


        //press the button, open ModulWeight
        /*weight = (Button) findViewById(R.id.next);

        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent weight = new Intent(Recording.this,ModulWeight.class);
                startActivity(weight);
            }
        });*/

    }

    public void showDialog(View v){
        FragmentManager manager = getFragmentManager();
        RecordingModulWeight recordingModulWeight = new RecordingModulWeight();
        recordingModulWeight.show(manager,"RecordingModulWeight");

    }




}
