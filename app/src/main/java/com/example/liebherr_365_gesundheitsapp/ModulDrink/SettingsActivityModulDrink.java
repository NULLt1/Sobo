package com.example.liebherr_365_gesundheitsapp.ModulDrink;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.util.Log;
import android.view.MenuItem;

import com.example.liebherr_365_gesundheitsapp.AppCompatPreferenceActivity;
import com.example.liebherr_365_gesundheitsapp.ModulWeight.DeleteDataDrink;
import com.example.liebherr_365_gesundheitsapp.R;

public class SettingsActivityModulDrink extends AppCompatPreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.menu_weight);

        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();

        // cups
        EditTextPreference editTextPref = (EditTextPreference) findPreference("cups");
        editTextPref.setSummary(sp.getString("Tassen", null));

        Preference button = findPreference(getString(R.string.deletedata));
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.d("Clicked", "Clicked");
                DialogFragment deletedata = new DeleteDataDrink();
                deletedata.show(getFragmentManager(), "DeleteData");
                return true;
            }
        });

        /*
        // new DBHelperDataSource
        DataSourceData dataSourceData = new DataSourceData(this);
        dataSourceData.open();

        // getFirstWeight
        float firstweight = dataSourceData.getLatestEntry("ModulWeight");

        // handle empty db -> hide deletedata
        if (firstweight == 0) {
            PreferenceScreen screen = getPreferenceScreen();
            Preference pref = getPreferenceManager().findPreference(getString(R.string.deletedata));
            screen.removePreference(pref);
        }

        // close db connection
        dataSourceData.close();
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)`enter code here`
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void OnCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String value = "";

        /*
        Preference pref = findPreference(key);
        if (pref instanceof EditTextPreference) {
            EditTextPreference etp = (EditTextPreference) pref;
            switch (etp.getKey()) {
                case "height":
                    value = " cm";
                    break;
                case "age":
                    value = " Jahre";
                    // call function setAge
                    break;
                default:
                    value = "";
            }
            pref.setSummary(etp.getText() + value);
        }
        */
    }
}

