package com.example.liebherr_365_gesundheitsapp.ModulMensa;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.R;
import com.example.liebherr_365_gesundheitsapp.viewAdapter.ListViewAdapterMensa;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class ModulMensa extends AppCompatActivity {
    public static String DAY = "day";
    public static String MENU = "menu";
    public static String HEADER = "header";
    public static String PRICE = "price";
    private boolean currentWeek = true;
    private int weekOfTheYear = 0;
    TextView textViewKW;
    ImageButton imageButtonLastWeek;
    ImageButton imageButtonNextWeek;
    ListView listview;
    ListViewAdapterMensa adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, ArrayList<String>>> arraylist;
    String url = "http://eid.dm.hs-furtwangen.de/joomla/index.php/speiseplan";
    Document doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up navigation enabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_modul_mensa);
        imageButtonLastWeek = (ImageButton) findViewById(R.id.imageButtonModulMensaLastWeek);
        imageButtonLastWeek.setVisibility(View.GONE);
        imageButtonNextWeek = (ImageButton) findViewById(R.id.imageButtonModulMensaNextWeek);
        //Parser starts if internet conn exists
        if (isOnline() == true) {
            new XmlParser().execute();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void showNextWeek(View view) {
        currentWeek = false;
        imageButtonLastWeek.setVisibility(View.VISIBLE);
        imageButtonNextWeek.setVisibility(View.GONE);
        adapter.updateResults(new XmlParser().parseTable());
    }

    public void showLastWeek(View view) {
        currentWeek = true;
        imageButtonLastWeek.setVisibility(View.GONE);
        imageButtonNextWeek.setVisibility(View.VISIBLE);
        adapter.updateResults((new XmlParser()).parseTable());
    }

    private class XmlParser extends AsyncTask<Void, Void, Void> {

        String content;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(ModulMensa.this);
            mProgressDialog.setTitle("Speiseplan");
            mProgressDialog.setMessage("Laden...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                doc = Jsoup.parse(new URL(url).openStream(), null, url);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listview = (ListView) findViewById(R.id.listViewMensa);
            adapter = new ListViewAdapterMensa(ModulMensa.this, parseTable());
            listview.setAdapter(adapter);

            mProgressDialog.dismiss();
        }

        public ArrayList<HashMap<String, ArrayList<String>>> parseTable() {
            arraylist = new ArrayList<>();
            String dateID;
            int tableID;
            //imageButton= findViewById()
            if (currentWeek == true) {

                dateID = "span#date1";
                tableID = 1;

            } else
                dateID = "span#date3";
            tableID = 4;
            try {
                Element dateString = doc.select(dateID).first();
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
                DateFormat newFormat = new SimpleDateFormat("dd.MM.yy", Locale.GERMAN);
                Date date = format.parse(dateString.text());
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                weekOfTheYear = c.get(Calendar.WEEK_OF_YEAR);


                Element table = doc.select("table").get(tableID);
                Log.d(table.text(), "doInBackground: ");
                //table = table.nextElementSibling();
                Elements trs = table.getElementsByTag("tr");
                int trSize = trs.size();
                Element tr = table.select("tr").first();
                Elements tds = tr.getElementsByTag("td");
                int tdSize = tds.size();


                //loop Columns
                for (int i = 0; i < tdSize; i++) {
                    ArrayList<String> daylist = new ArrayList<>();
                    ArrayList<String> menulist = new ArrayList<>();
                    ArrayList<String> pricelist = new ArrayList<>();
                    ArrayList<String> headerlist = new ArrayList<>();
                    HashMap<String, ArrayList<String>> map = new HashMap<>();
                    //loop lines
                    for (int j = 0; j < trSize; j++) {
                        Element td = trs.get(j).select("td").get(i);
                        if (j == 0) {
                            String currentDayString = newFormat.format(c.getTime());
                            daylist.add(td.text() + "\n" + currentDayString);
                            c.add(Calendar.DATE, 1);
                        } else if (j % 2 != 0 && j != 0) {
                            String headerPrice = td.text();
                            String[] parts = headerPrice.split("€");
                            if (parts.length > 1) {
                                headerlist.add(parts[0]);
                                pricelist.add(parts[1] + " €");
                            }
                        } else {
                            if (!td.text().isEmpty()) {
                                menulist.add(td.text());
                            }
                        }

                    }
                    //add Arraylist to Hashmap
                    map.put(DAY, daylist);
                    map.put(HEADER, headerlist);
                    map.put(PRICE, pricelist);
                    map.put(MENU, menulist);
                    arraylist.add(map);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(String.valueOf(tableID), "tableid");
            Log.d(String.valueOf(currentWeek), "parseTable: ");
            Log.d(arraylist.get(0).get(ModulMensa.DAY).get(0), "parseTable: ");
            textViewKW = (TextView) findViewById(R.id.textViewModulMensaKW);
            textViewKW.setText("KW " + String.valueOf(weekOfTheYear));
            return arraylist;
        }
    }
}
