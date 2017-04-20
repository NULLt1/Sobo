package com.example.liebherr_365_gesundheitsapp.ModulMensa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.R;
import com.example.liebherr_365_gesundheitsapp.viewAdapter.ListViewAdapterAdditionalMenu;
import com.example.liebherr_365_gesundheitsapp.viewAdapter.ListViewAdapterMensa;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class ModulMensa extends AppCompatActivity {
    public static String ADDITIONALMENUITEM = "additionalMenuItem";
    public static String DAY = "day";
    public static String MENU = "menu";
    public static String HEADER = "header";
    public static String PRICE = "price";
    private boolean currentWeek = true;
    private int weekOfTheYear = 0;
    private String filename = "html_data.html";
    private Context context;
    private File file;
    private TextView textViewKW;
    private ImageButton imageButtonLastWeek;
    private ImageButton imageButtonNextWeek;
    private ListView listview;
    private ListViewAdapterMensa adapter;
    private ListViewAdapterAdditionalMenu additionalAdapter;
    private ProgressDialog mProgressDialog;
    private ArrayList<HashMap<String, ArrayList<String>>> arraylist;
    ArrayList<HashMap<String, String>> additionalDataArraylist;
    private boolean isSet = false;
    private String url = "http://eid.dm.hs-furtwangen.de/joomla/index.php/speiseplan";
    private Document doc;
    private TextView buttonMore;
    View v;
    TextView textViewFooter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        file = new File(context.getFilesDir(), filename);

        // set up navigation enabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_modul_mensa);
        imageButtonLastWeek = (ImageButton) findViewById(R.id.imageButtonModulMensaLastWeek);

        //hide arrownavigation
        imageButtonLastWeek.setVisibility(View.INVISIBLE);
        imageButtonNextWeek = (ImageButton) findViewById(R.id.imageButtonModulMensaNextWeek);
        //Parser starts if internet conn exists
        new XmlParser().execute();
        buttonMore = (Button) findViewById(R.id.buttonMore);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && isSet) {
            listview.setAdapter(adapter);
            buttonMore.setText(getResources().getString(R.string.button_more));
            isSet = false;
            return true;
        }

        return super.onOptionsItemSelected(item);
        /**
         switch (item.getItemId()) {
         case android.R.id.home:
         NavUtils.navigateUpFromSameTask(this);
         return true;
         default:
         return super.onOptionsItemSelected(item);
         }**/
    }


    public void showNextWeek(View view) {
        currentWeek = false;
        imageButtonLastWeek.setVisibility(View.VISIBLE);
        imageButtonNextWeek.setVisibility(View.INVISIBLE);
        adapter.updateResults(new XmlParser().parseTable());
    }

    public void showLastWeek(View view) {
        currentWeek = true;
        imageButtonLastWeek.setVisibility(View.INVISIBLE);
        imageButtonNextWeek.setVisibility(View.VISIBLE);
        adapter.updateResults((new XmlParser()).parseTable());
    }

    public void showAdditionalMenu(View view) {
        if (isSet) {
            isSet = false;
            listview.setAdapter(adapter);
            buttonMore.setText(getResources().getString(R.string.button_more));

        } else {
            isSet = true;
            additionalAdapter = new ListViewAdapterAdditionalMenu(context, additionalDataArraylist);
            listview.setAdapter(additionalAdapter);
            listview.deferNotifyDataSetChanged();
            buttonMore.setText(getResources().getString(R.string.button_less));
        }
    }

    private class XmlParser extends AsyncTask<Void, Void, Void> {
        String stringText;
        String stringIncredients;


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
            //loads data if internet connection exists, writes data in a file


            if (isOnline()) {
                try {
                    URL uri = new URL(url);
                    URLConnection connection = uri.openConnection();
                    doc = Jsoup.parse(new URL(url).openStream(), null, url);
                    //saves data if it is modified

                    saveDataLocal(doc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                doc = getDataFromFile();
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

            v = getLayoutInflater().inflate(R.layout.listview_item_mensa_footer, null);
            listview.addFooterView(v);
            textViewFooter = (TextView) findViewById(R.id.textViewFooter);
            TextView textViewFooterIncredients = (TextView) findViewById(R.id.textViewFooterIncredients);
            textViewFooter.setText(stringText);
            textViewFooterIncredients.setText(stringIncredients);

            listview.setAdapter(adapter);

            mProgressDialog.dismiss();
        }

        private ArrayList<HashMap<String, ArrayList<String>>> parseTable() {
            arraylist = new ArrayList<>();
            String dateID;
            String tableID;


            //imageButton= findViewById()
            if (currentWeek) {

                dateID = "span#date1";
                tableID = "table#table1";

            } else {
                dateID = "span#date3";
                tableID = "table#table2";
            }
            try {
                //formates Date and sets the week of the year
                Element dateString = doc.select(dateID).first();
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
                DateFormat newFormat = new SimpleDateFormat("dd.MM.yy", Locale.GERMAN);
                Date date = format.parse(dateString.text());
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                weekOfTheYear = c.get(Calendar.WEEK_OF_YEAR);

                //Parses the Incredients
                try {
                    Element incredients = doc.getElementById("incredients");
                    Elements ps = incredients.select("p");
                    stringText = ps.first().text();
                    stringIncredients = ps.next().text();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Parses additional Menu

                Element elementTable1 = doc.getElementById("additionalMenu");
                Elements elementsTrs = elementTable1.getElementsByTag("tr");
                Elements elementsTds1 = elementsTrs.first().getElementsByTag("td");


                Log.d(elementsTds1.text(), "TEST!!!! ");
                additionalDataArraylist = new ArrayList<>();
                for (int i = 0; i < elementsTrs.size(); i++) {
                    HashMap<String, String> mapnew = new HashMap<>();
                    for (int j = 0; j < 2; j++) {
                        Element td = elementsTrs.get(i).select("td").get(j);
                        if (j == 0)
                            mapnew.put(ADDITIONALMENUITEM, td.text());
                        else
                            mapnew.put(PRICE, td.text());
                    }
                    Log.d(mapnew.get(PRICE), "parseTable: ");
                    Log.d(mapnew.get(ADDITIONALMENUITEM), "parseTable: ");
                    additionalDataArraylist.add(mapnew);
                }

                //Parses the Menu
                Element table = doc.select(tableID).first();
                Elements trs = table.getElementsByTag("tr");
                int trSize = trs.size();
                Element tr = table.select("tr").first();
                Elements tds = tr.getElementsByTag("td");
                int tdSize = tds.size();


                //loops all lines for each column
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
                        } else if (j % 2 != 0) {
                            String headerPrice = td.text();
                            String[] parts = headerPrice.split("€");
                            if (parts.length > 1) {
                                headerlist.add(parts[0]);
                                pricelist.add(parts[1] + " €");
                            }
                        } else {
                            if (!td.text().isEmpty()) {
                                Elements elementsps = td.select("p");

                                Element elementp;
                                StringBuffer stringBuilder = new StringBuffer();
                                for (int k = 0; k < elementsps.size(); k++) {
                                    if (elementsps.size() > 1 & k < elementsps.size() - 1) {
                                        elementp = elementsps.get(k);
                                        stringBuilder.append(elementp.text());
                                        stringBuilder.append("\n");
                                    } else {
                                        elementp = elementsps.get(k);
                                        stringBuilder.append(elementp.text() + "\n");
                                    }
                                }
                                if (elementsps.size() == 0) {
                                    stringBuilder.append(td.text() + "\n");
                                }
                                menulist.add(stringBuilder.toString());
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
            textViewKW = (TextView) findViewById(R.id.textViewModulMensaKW);
            Resources res = getResources();
            String stringWeekOfTheYear = String.format(res.getString(R.string.week_of_the_year), String.valueOf(weekOfTheYear));
            textViewKW.setText(stringWeekOfTheYear);
            return arraylist;
        }

        private void saveDataLocal(Document doc) throws FileNotFoundException {
            //saves data local, writes last-modified to sp
            PrintWriter writer;
            FileOutputStream os;
            //write parsed document to document
            os = openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new PrintWriter(os, false);
            writer.print(doc);
            writer.flush();
            writer.close();
        }

        private Document getDataFromFile() throws IOException {
            return Jsoup.parse(file, null, context.getFilesDir().toString());
        }

        private boolean isOnline() {
            //checks internet avaiability
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }

    }
}
