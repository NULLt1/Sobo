package com.example.liebherr_365_gesundheitsapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by mpadmin on 10.04.2017.
 */

public class NoRecordingRequired extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("Achtung!");
        alertDialogBuilder.setMessage("Es werden zurzeit keine Daten benötigt.");
        alertDialogBuilder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int d) {
                        //action
                        dialog.dismiss();
                    }
                });
        return alertDialogBuilder.create();
    }
}