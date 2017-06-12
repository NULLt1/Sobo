package com.example.liebherr_365_gesundheitsapp.XMLParser;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.example.liebherr_365_gesundheitsapp.Database.DataMensaMenu;
import com.example.liebherr_365_gesundheitsapp.Database.DataParsedData;
import com.example.liebherr_365_gesundheitsapp.Database.DataSourceHealthCare;
import com.example.liebherr_365_gesundheitsapp.Database.DataSourceMensa;
import com.example.liebherr_365_gesundheitsapp.Database.DataSourceParsedData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
    private static final String url_push = "http://eid.dm.hs-furtwangen.de/joomla/index.php/pushnews";
    private static final String URL_HEALTH_CARE = "http://eid.dm.hs-furtwangen.de/joomla/index.php/gesundheitsangebote";

    private static final String LOG_TAG = Parser.class.getSimpleName();
    private Context mContext;
    private DataSourceMensa dataSourceMensa;
    private DataSourceHealthCare dataSourceHealthCare;
    private DataSourceParsedData dataSourceParsedData;

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

        DateFormat format = new SimpleDateFormat("dd.MM.yy", Locale.GERMAN);
        DateFormat newFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.GERMAN);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            if (isOnline()) {
                try {

                    parseMenu(Jsoup.parse(new URL(url).openStream(), null, url));
                    parseNews(Jsoup.parse(new URL(url_push).openStream(), null, url));

                    parseHealthCare(Jsoup.parse(new URL(URL_HEALTH_CARE).openStream(), null, URL_HEALTH_CARE));
                } catch (Exception e) {
                    Log.d(LOG_TAG, "Fehler: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
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
            dataSourceMensa = new DataSourceMensa(mContext);
            Elements dateElements = doc.getElementsByClass("date");

            //whole line with text
            String[] rawDates = {dateElements.get(0).text(), dateElements.get(1).text()};
            Date[] dates = new Date[rawDates.length];


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

            //parse additional menu IMPORTANT!!! weekOfTHEYEAR must be 0
            Element elementTableAdditionalMenu = doc.getElementById("additionalMenu");
            Elements elementsAdditionalMenuTrs = elementTableAdditionalMenu.getElementsByTag("tr");
            Elements elementsAdditionalMenuTds = elementsAdditionalMenuTrs.first().getElementsByTag("td");

            for (int i = 0; i < elementsAdditionalMenuTrs.size(); i++) {
                String price = "";
                String menu = "";
                for (int j = 0; j < elementsAdditionalMenuTds.size(); j++) {
                    Element elementTd = elementsAdditionalMenuTrs.get(i).select("td").get(j);
                    if (j == 0) {
                        menu = elementTd.text();
                    } else if (j == 1) {
                        price = elementTd.text();
                        price = price.replace("€", "");
                        Log.d(LOG_TAG, "preis: " + elementTd.text());
                    }

                }
                dataSourceMensa.open();
                DataMensaMenu das = dataSourceMensa.createEntry("", "", 0, menu, price, menu);
                dataSourceMensa.close();
            }

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


                            if (elementTd.text().length() > 5) {
                                Elements elementsPs = elementTd.select("p");
                                Element elementP;

                                if (elementsPs.size() == 0) {

                                    stringBuilder.append(elementTd.text());

                                }

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

                                dataSourceMensa.open();
                                DataMensaMenu data = dataSourceMensa.createEntry(date, day, weekOfTheYear, header, price, menu);
                                dataSourceMensa.close();
                                menu = "";
                            }
                        }
                    }
                }
            }
            Element elementTable = doc.getElementById("incredients");
            Elements elementsPs = elementTable.getElementsByTag("p");

            String header = "";
            StringBuffer stringBuilder = new StringBuffer();
            for (int i = 0; i < elementsPs.size(); i++) {
                Element elementP = elementsPs.get(i);

                if (i == 0) {
                    header = elementP.text();
                } else {
                    stringBuilder.append(elementP.text());
                }
            }
            dataSourceMensa.open();
            dataSourceMensa.createEntry("", "", 100, header, "", stringBuilder.toString());
            dataSourceMensa.close();
        }

        public void parseNews(Document doc) throws ParseException {
            dataSourceParsedData = new DataSourceParsedData(mContext);


            Element elementTable = doc.getElementById("push1");

            Elements elementsTrs = elementTable.getElementsByTag("tr");
            Elements elementsTds = elementsTrs.first().getElementsByTag("td");

            for (int i = 1; i < elementsTrs.size(); i++) {
                String date = "";
                String teaser = "";
                String text = "";
                for (int j = 0; j < elementsTds.size(); j++) {
                    Element elementTd = elementsTrs.get(i).getElementsByTag("td").get(j);

                    //replace whitespace in table cells
                    String cleaned = elementTd.text().replace("\u00a0", "");
                    if (!cleaned.isEmpty() | cleaned.length() > 5) {
                        if (j == 0) {
                            Date myDate = format.parse(cleaned);
                            date = newFormat.format(myDate);

                        } else if (j == 1) {
                            teaser = cleaned;

                        } else {
                            text = cleaned;
                        }
                    }

                }
                if (!date.isEmpty()) {
                    dataSourceParsedData.open();
                    DataParsedData data = dataSourceParsedData.createEntry("News", date, teaser, text, true);
                    // Log.d(LOG_TAG, "Neuer Eintrag: " + data.toString());
                    dataSourceParsedData.getAllData();
                    dataSourceParsedData.close();
                }
            }
        }

        public void parseHealthCare(Document doc) {
            dataSourceHealthCare = new DataSourceHealthCare(mContext);
            dataSourceHealthCare.open();
            //dataSourceHealthCare.deleteDB();
            dataSourceHealthCare.close();

            Element elementTable = doc.getElementById("table1");
            Elements elementsTrs = elementTable.getElementsByTag("tr");
            Elements elementsTds = elementsTrs.first().getElementsByTag("td");
            String name = "";

            for (int i = 1; i < elementsTrs.size(); i++) {
                StringBuffer stringBuilder = new StringBuffer();
                String date = "";

                String venue = "";
                String status = "";
                String price = "";
                for (int j = 0; j < elementsTds.size(); j++) {
                    Element elementTd = elementsTrs.get(i).getElementsByTag("td").get(j);
                    String cleaned = elementTd.text().replace("\u00a0", "").trim();
                    if (j == 0 && !cleaned.isEmpty()) {
                        name = cleaned;

                    }
                    switch (j) {
                        case 0:
                            break;
                        case 1:
                            venue = cleaned;
                            break;
                        case 2:
                            stringBuilder.append(cleaned);
                            break;
                        case 3:
                            stringBuilder.append("\n" + cleaned);
                            break;
                        case 4:
                            stringBuilder.append("\n" + cleaned);
                            break;
                        case 5:
                            price = cleaned;
                            break;
                        case 6:
                            status = cleaned;
                            break;
                        default:
                            Log.d(LOG_TAG, "Ein Fehler ist bei folgender Zelle aufgetreten: " + cleaned);
                            break;
                    }
                }

                dataSourceHealthCare.open();
                dataSourceHealthCare.createEntry(stringBuilder.toString(), name, venue, price, status);
                dataSourceHealthCare.close();
            }
        }
    }
}
