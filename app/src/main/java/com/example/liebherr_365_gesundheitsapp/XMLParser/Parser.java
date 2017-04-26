package com.example.liebherr_365_gesundheitsapp.XMLParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Jan on 26.04.2017.
 */

public class Parser {
    public static String ADDITIONALMENUITEM = "additionalMenuItem";
    public static String DAY = "day";
    public static String MENU = "menu";
    public static String HEADER = "header";
    public static String PRICE = "price";

    private static final String url = "http://eid.dm.hs-furtwangen.de/joomla/index.php/speiseplan";
    private static final String LOG_TAG = Parser.class.getSimpleName();

    private Context mContext;
    private Document doc;

    private String filename = "html_data.html";
    private File file;

    private ArrayList<HashMap<String, ArrayList<String>>> arraylist;
    private ArrayList<HashMap<String, String>> additionalDataArraylist;

    public boolean currentWeek = true;
    public int weekOfTheYear = 0;
    String stringText;
    String stringIncredients;

    public Parser(Context context) {
        mContext = context;
    }

    public void pullData() {
        new XMLParser().execute();
    }


    public ArrayList<HashMap<String, ArrayList<String>>> parseTable() {
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
            doc = getDataFromFile();
            Log.d("dastas", doc.text());
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
                                Log.d("test", td.text());
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

        return arraylist;
    }

    public ArrayList<HashMap<String, ArrayList<String>>> getCurrentMenu() {
        ArrayList<HashMap<String, ArrayList<String>>> allItems = parseTable();
        ArrayList<HashMap<String, ArrayList<String>>> filteredList = new ArrayList<>();

        filteredList.add(allItems.get(3));
        return filteredList;

    }

    private Document getDataFromFile() throws IOException {
        file = new File(mContext.getFilesDir(), filename);
        Log.d("filedirectory", file.getAbsolutePath());
        return Jsoup.parse(file, null, mContext.getFilesDir().toString());
    }

    private class XMLParser extends AsyncTask {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setTitle("Daten");
            mProgressDialog.setMessage("Laden...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();


        }

        @Override
        protected Object doInBackground(Object[] params) {
            if (isOnline()) {
                try {
                    saveDataLocally(doc = Jsoup.parse(new URL(url).openStream(), null, url));
                } catch (Exception e) {
                    Log.d(LOG_TAG, "Fehler: " + e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            mProgressDialog.dismiss();
        }

        private boolean isOnline() {
            //checks internet avaiability
            ConnectivityManager cm =
                    (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }

        private void saveDataLocally(Document doc) throws FileNotFoundException {
            //saves data local, writes last-modified to sp
            PrintWriter writer;
            FileOutputStream os;

            //write parsed document to document
            os = mContext.openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new PrintWriter(os, false);
            writer.print(doc);
            writer.flush();
            writer.close();
        }
    }

}
