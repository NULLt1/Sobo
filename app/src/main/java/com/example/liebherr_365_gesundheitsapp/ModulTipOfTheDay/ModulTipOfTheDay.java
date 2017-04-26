package com.example.liebherr_365_gesundheitsapp.ModulTipOfTheDay;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.liebherr_365_gesundheitsapp.Database.DataParsedData;
import com.example.liebherr_365_gesundheitsapp.Database.DataSourceParsedData;
import com.example.liebherr_365_gesundheitsapp.R;
import com.example.liebherr_365_gesundheitsapp.viewAdapter.TipOfTheDayAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;

public class ModulTipOfTheDay extends AppCompatActivity {
    public static final String LOG_TAG = ModulTipOfTheDay.class.getSimpleName();

    private TipOfTheDayAdapter adapter;
    private ListView listView;
    private ProgressDialog mProgressDialog;
    private DataSourceParsedData dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_of_the_day);

        dataSource = new DataSourceParsedData(this);

        dataSource.open();
        dataSource.deleteDB();
        dataSource.close();

        new XMLParser().execute();
    }

    public void showNewEntry(View view) {
        dataSource.open();
        dataSource.getNewTip();
        adapter.updateData(dataSource.getSubscribedTips());
        dataSource.close();

        adapter.notifyDataSetChanged();
    }

    private class XMLParser extends AsyncTask {
        String url = "http://eid.dm.hs-furtwangen.de/joomla/index.php/gesundheitstipps";

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                saveDataLocally(getDataFromWebsite());
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(ModulTipOfTheDay.this);
            mProgressDialog.setTitle("Speiseplan");
            mProgressDialog.setMessage("Laden...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            showEntries();
            mProgressDialog.dismiss();

        }


        private Document getDataFromWebsite() throws Exception {
            Document doc = Jsoup.parse(new URL(url).openStream(), null, url);

            return doc;
        }

        private void saveDataLocally(Document doc) {
            String moduleName;
            String teaser = null;
            String text = null;

            Elements elementsTables = doc.select("table");

            for (int i = 0; i < elementsTables.size(); i++) {
                Element elementTable = elementsTables.get(i);
                moduleName = elementTable.attr("id");
                Elements elementsTrs = elementTable.select("tr");

                for (int j = 1; j < elementsTrs.size(); j++) {
                    Elements elementsTds = elementsTrs.get(j).select("td");

                    for (int k = 0; k < elementsTds.size(); k++) {


                        Element elementTd = elementsTds.get(k);
                        if (elementTd.text().length() > 4) {
                            if (k == 0) {
                                teaser = elementTd.text();
                            } else {
                                text = elementTd.text();
                            }
                        }

                    }
                    if (!teaser.isEmpty()) {
                        dataSource.open();
                        DataParsedData test = dataSource.createEntry(moduleName, null, teaser, text, false);
                        teaser = "";
                        text = "";
                        dataSource.close();
                    }
                }
            }
        }

        private void showEntries() {
            listView = (ListView) findViewById(R.id.listViewTipOfTheDay);

            dataSource.open();
            adapter = new TipOfTheDayAdapter(ModulTipOfTheDay.this, dataSource.getSubscribedTips());
            dataSource.close();

            listView.setAdapter(adapter);
        }
    }


}
