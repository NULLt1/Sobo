package com.example.liebherr_365_gesundheitsapp;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.liebherr_365_gesundheitsapp.Database.DataSourceData;
import com.example.liebherr_365_gesundheitsapp.Database.DataSourceModules;


//s
public class MainMenu extends AppCompatActivity {

    //public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    //       .-..-..---. .-..-.
    //       : :; :: .--': :: :
    //       :    :: `;  : :: :
    //       : :: :: :   : :; :
    //       :_;:_;:_;   `.__.'
    //
    // This project was developed during our project studies.
    // WS 16/17 & SS 17
    // Bussmann    Jan
    // Hug         Melissa
    // Otec        Marvin
    // Speer       Christopher
    // Wangler     Niklas

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private DataSourceModules dbm;
    private DataSourceData dbd;
    private ViewPager mViewPager;

    // initalize counter for required fragments
    private static int fragmentcounter = 0;

    // initalize String[] activemodulesarray
    private String[] activemodulesarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // fill database with defaultmodules
        dbm = new DataSourceModules(this);
        dbm.open();

        // call function insertdefaultmodules
        dbm.insertdefaultmodules();

        // call function getactivemodulesstringarray
        activemodulesarray = dbm.getactivemodulesstringarray();

        // close dbm connection
        dbm.close();

        // call function prepareRecording
        prepareRecording();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onResume() {
        super.onResume();

        // new DataSourceModules
        dbm = new DataSourceModules(this);
        dbm.open();

        // call function getactivemodulesstringarray
        activemodulesarray = dbm.getactivemodulesstringarray();

        // close dbm connection
        dbm.close();

        // call function prepareRecording
        prepareRecording();
    }

    // function resetFragmentCounter
    public static void resetFragmentCounter() {
        fragmentcounter = 0;
    }

    // function prepareRecording
    private void prepareRecording() {
        // initalize counter for required fragments
        int fragmentcounterlocal = 0;

        // iterate through activemodulesarray
        for (String aX : activemodulesarray) {
            // handle Modules
            switch (aX) {
                case "ModulWeight":
                    Log.d("Found", "ModulWeight");

                    // new DataSourceModules
                    dbd = new DataSourceData(this);
                    dbd.open();

                    // call function entryalreadyexisting
                    boolean result = dbd.entryAlreadyExisting("ModulWeight");

                    // if result == false count up fragmentcounterlocal
                    if (!result) {
                        fragmentcounterlocal++;
                    }

                    // close dbm connection
                    dbd.close();

                    break;
                case "ModulDrink":
                    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    //~~~~~~~~~~~~~~PLACEHOLDER~~~~~~~~~~~~~~~~~
                    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    Log.d("Found", "ModulDrink");
                    break;
                default:
                    break;
            }
        }
        // set fragmentcounter with value of fragmentcounterlocal
        fragmentcounter = fragmentcounterlocal;
    }

    //  function setBadgeCount
    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {
        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);

        // setBadgeCount with fragmencounter
        MenuItem itemCart = menu.findItem(R.id.recording);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        setBadgeCount(this, icon, String.valueOf(fragmentcounter));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //settings icon
        if (id == R.id.action_settings) {
            return true;
        }

        //recording icon
        if (id == R.id.recording) {
            if (activemodulesarray == null) {
                // handle null array -> no recording required
                openNoRecordingRequiredFragment();
            } else if (fragmentcounter == 0) {
                // handle fragmentcounter == 0
                openNoRecordingRequiredFragment();
            } else {
                // create new NumberPickerModulWeight
                DialogFragment RecordingWeightNumberPicker = new RecordingWeightNumberPicker();

                // open NumberPickerModulWeight
                RecordingWeightNumberPicker.show(getFragmentManager(), "numberPicker");
            }
        }

        return super.onOptionsItemSelected(item);
    }

    // function openNoRecordingRequiredFragment

    public void openNoRecordingRequiredFragment() {
        // create new WrongDatumFragment
        DialogFragment NoRecordingRequired = new NoRecordingRequired();

        // open WrongDatumFragment
        NoRecordingRequired.show(getFragmentManager(), "NoRecordingRequired");
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Tab1 tab1 = new Tab1();
                    return tab1;
                case 1:
                    Tab2 tab2 = new Tab2();
                    return tab2;
                case 2:
                    Tab3 tab3 = new Tab3();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Übersicht";
                case 1:
                    return "Meine Apps";
                case 2:
                    return "Bibliothek";
            }
            return null;
        }
    }

}
