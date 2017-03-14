package com.example.liebherr_365_gesundheitsapp;

import android.os.AsyncTask;
import android.provider.Settings;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Jan on 13.03.2017.
 */

public class XmlParser extends AsyncTask<Void, Void, Void> {
    String content;
    TextView id;
    public XmlParser(TextView id){
        this.id=id;
    }
    @Override
    protected Void doInBackground(Void... params) {
        try {
            Document doc = Jsoup.connect("http://www.hs-furtwangen.de/").get();
        content=doc.text();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        id.setText(content);
        System.out.print(content);

    }
}
