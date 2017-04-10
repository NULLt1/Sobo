package com.example.liebherr_365_gesundheitsapp;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.liebherr_365_gesundheitsapp.Database.DBHelperDataSourceData;
import com.example.liebherr_365_gesundheitsapp.Database.DBHelperDataSourceModules;
import com.example.liebherr_365_gesundheitsapp.Database.DataQuery;
import com.example.liebherr_365_gesundheitsapp.ModulWeight.WrongDatumFragment;

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
    // Wintersemster 16/17
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

    private DBHelperDataSourceModules dbm;
    private DBHelperDataSourceData dbd;
    private ViewPager mViewPager;

    // initalize counter for required fragments
    int fragmentcounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // fill database with defaultmodules
        dbm = new DBHelperDataSourceModules(this);
        dbm.open();

        // call function insertdefaultmodules
        dbm.insertdefaultmodules();

        // close dbm connection
        dbm.close();


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

        MenuItem itemCart = menu.findItem(R.id.recording);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        setBadgeCount(this, icon, "8");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //recording icon
        if (id == R.id.recording) {

            // new DBHelperDataSourceModules
            dbm = new DBHelperDataSourceModules(this);
            dbm.open();

            // initalize String[] activemodulesarray
            String[] activemodulesarray;

            // call function getactivemodulesstringarray
            activemodulesarray = dbm.getactivemodulesstringarray();

            // close dbm connection
            dbm.close();



            // handle null array -> no recording required
            if (activemodulesarray == null) {
                openNoRecordingRequiredFragment();
            } else {
                // iterate through activemodulesarray
                for (String aX : activemodulesarray) {
                    // handle Modules
                    switch (aX) {
                        case "ModulWeight":
                            Log.d("Found", "ModulWeight");

                            // new DBHelperDataSourceModules
                            dbd = new DBHelperDataSourceData(this);
                            dbd.open();

                            boolean result = dbd.entryalreadyexisting("ModulWeight");

                            if (!result) {
                                fragmentcounter++;
                            }

                            Log.d("RESULT", String.valueOf(result));

                            dbd.close();

                            break;
                        case "ModulDrink":
                            Log.d("Found", "ModulDrink");
                            break;
                        default:
                            break;
                    }
                }
                if (fragmentcounter == 0) {
                    openNoRecordingRequiredFragment();
                }
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
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

}
