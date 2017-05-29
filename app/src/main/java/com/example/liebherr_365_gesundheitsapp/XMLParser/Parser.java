package com.example.liebherr_365_gesundheitsapp.XMLParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.example.liebherr_365_gesundheitsapp.Database.DataMensaMenu;
import com.example.liebherr_365_gesundheitsapp.Database.DataSourceMensa;
import com.example.liebherr_365_gesundheitsapp.Database.Queries;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {


    private static final String url = "http://eid.dm.hs-furtwangen.de/joomla/index.php/speiseplan";
    private static final String LOG_TAG = Parser.class.getSimpleName();

    private Context mContext;
    private Document doc;

    private String filename = "html_data.html";
    private File file;

    private DataMensaMenu currentWeek;
    private DataMensaMenu nextWeek;
    private DataMensaMenu additionalMenu;
    private DataMensaMenu incredients;

    private static int weekOfTheYear = 0;

    private DataSourceMensa datasource;

    public Parser(Context context) {
        mContext = context;
    }

    //Downloading the HTML FILES
    public void pullData() {
        new XMLParser().execute();
    }

    public static int getCurrentWeekOfTheYear() {

        Calendar calendar = GregorianCalendar.getInstance(Locale.GERMAN);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        int delta = -calendar.get(GregorianCalendar.DAY_OF_WEEK) + 2; //add 2 if your week start on monday
        calendar.add(Calendar.DAY_OF_MONTH, delta);

        int weekOfTheYear = calendar.get(Calendar.WEEK_OF_YEAR);

        return weekOfTheYear;
    }

    public static int getnextWeekOfTheYear() {
        return getCurrentWeekOfTheYear() + 1;
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
            Log.d(LOG_TAG, "Query: " + Queries.CREATE_TABLE_MENSA);
            if (isOnline()) {
                try {
                    parseMenu(doc = Jsoup.parse(new URL(url).openStream(), null, url));
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

        public void parseMenu(Document doc) {
            Pattern patternPrice = Pattern.compile("\\d*\\W\\d\\d");
            Pattern patternHeader = Pattern.compile("[A-Za-z0-9_äÄöÖüÜß]*?\\s[A-Za-z0-9_äÄöÖüÜß]*");
            Pattern patternDate = Pattern.compile("\\d\\d\\.\\d\\d\\.\\d\\d");
            datasource = new DataSourceMensa(mContext);

            Elements dateElements = doc.getElementsByClass("date");

            //whole line with text
            String[] rawDates = {dateElements.get(0).text(), dateElements.get(1).text()};
            Date[] dates = new Date[rawDates.length];
            DateFormat format = new SimpleDateFormat("dd.MM.yy", Locale.GERMAN);
            DateFormat newFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.GERMAN);


            for (int i = 0; i < rawDates.length; i++) {
                Matcher matcher = patternDate.matcher(rawDates[i]);

                if (matcher.find()) {
                    rawDates[i] = matcher.group();

                    //Formats the date from the Website
                    try {
                        dates[i] = format.parse(matcher.group());
                        String stringDate = newFormat.format(dates[i]);


                    } catch (ParseException e) {
                        Log.d(LOG_TAG, "Fehler beim Formatieren des Datums: " + e.getMessage());
                    }
                }
            }

            Elements elementsTableMenu = doc.getElementsByClass("menu");

            for (int i = 0; i < elementsTableMenu.size(); i++) {

                Calendar c = Calendar.getInstance();
                c.setTime(dates[i]);
                //week of the year from each table
                int weekOfTheYear = c.get(Calendar.WEEK_OF_YEAR);

                //select table i
                Element elementTable = elementsTableMenu.get(i);

                //select all trs sections from the table (each column)
                Elements elementsTrs = elementTable.getElementsByTag("tr");

                int trSize = elementsTrs.size();
                Element elementTr = elementsTrs.first();

                Elements elementsTd = elementTr.getElementsByTag("td");
                int tdSize = elementsTd.size();

                //iterates through each column and line
                for (int j = 0; j < tdSize; j++) {
                    String day = "", date = "", price = "", header = "", menu = "";

                    for (int k = 0; k < trSize; k++) {
                        Element elementTd = elementsTrs.get(k).select("td").get(j);


                        if (k == 0) {
                            //date
                            date = newFormat.format(c.getTime());
                            //add a day to the date
                            c.add(Calendar.DATE, 1);

                            day = elementTd.text();

                        } else if (k % 2 != 0) {
                            //price & menu name

                            Matcher matcherPrice = patternPrice.matcher(elementTd.text());
                            Matcher matcherHeader = patternHeader.matcher(elementTd.text());

                            if (matcherPrice.find()) {
                                price = matcherPrice.group();

                            }
                            if (matcherHeader.find()) {
                                header = matcherHeader.group();

                            } else {
                                price = "";
                                header = "";
                            }
                        } else {
                            StringBuffer stringBuilder = new StringBuffer();

                            if (!elementTd.text().isEmpty()) {
                                Elements elementsPs = elementTd.select("p");
                                Element elementP;

                                for (int l = 0; l < elementsPs.size(); l++) {
                                    if (elementsPs.size() > 1 & l < elementsPs.size() - 1) {
                                        elementP = elementsPs.get(l);

                                        stringBuilder.append(elementP.text());
                                        stringBuilder.append("\n");
                                    } else {
                                        elementP = elementsPs.get(l);
                                        stringBuilder.append(elementP.text() + "\n");
                                    }
                                }
                                menu = stringBuilder.toString();
                            }

                            menu.trim();

                            if (!menu.isEmpty()) {

                                datasource.open();
                                DataMensaMenu data = datasource.createEntry(date, day, weekOfTheYear, header, price, menu);
                                datasource.close();
                                Log.d(LOG_TAG, "Neuer Eintrag: " + data.toString());
                            }
                        }
                    }
                }
            }
        }
    }
}
