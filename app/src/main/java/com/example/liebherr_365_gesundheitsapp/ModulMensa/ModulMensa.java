package com.example.liebherr_365_gesundheitsapp.ModulMensa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liebherr_365_gesundheitsapp.Database.DataMensaMenu;
import com.example.liebherr_365_gesundheitsapp.Database.DataSourceMensa;
import com.example.liebherr_365_gesundheitsapp.R;
import com.example.liebherr_365_gesundheitsapp.XMLParser.Parser;
import com.example.liebherr_365_gesundheitsapp.viewAdapter.ListViewAdapterAdditionalMenu;
import com.example.liebherr_365_gesundheitsapp.viewAdapter.ListViewAdapterMensa;

import java.util.List;


public class ModulMensa extends AppCompatActivity {

    public static String DAY = "day";
    public static String MENU = "menu";
    public static String HEADER = "header";
    public static String PRICE = "price";

    private boolean currentWeek;
    private ImageButton imageButtonLastWeek;
    private ImageButton imageButtonNextWeek;
    private ListView listview;
    private ListViewAdapterMensa adapter;
    private ListViewAdapterAdditionalMenu additionalAdapter;
    TextView textViewKw;
    String stringKw;
    private boolean isSet = false;

    private TextView textViewWeekOfTheYear;
    private Button buttonMore;


    private DataSourceMensa dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentWeek = true;
        dataSource = new DataSourceMensa(this);

        // set up navigation enabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_modul_mensa);

        listview = (ListView) findViewById(R.id.listViewMensa);
        dataSource.open();
        adapter = new ListViewAdapterMensa(ModulMensa.this, dataSource.getDataAsArrayList(Parser.getCurrentWeekOfTheYear()));
        dataSource.close();

        textViewWeekOfTheYear = (TextView) findViewById(R.id.textViewModulMensaKW);
        imageButtonLastWeek = (ImageButton) findViewById(R.id.imageButtonModulMensaLastWeek);

        //hide arrownavigation
        imageButtonLastWeek.setVisibility(View.INVISIBLE);
        imageButtonNextWeek = (ImageButton) findViewById(R.id.imageButtonModulMensaNextWeek);
        //Parser starts if internet conn exists

        buttonMore = (Button) findViewById(R.id.buttonMore);
        listview.setAdapter(adapter);

        View v = getLayoutInflater().inflate(R.layout.listview_item_mensa_footer, null);
        listview.addFooterView(v);
        TextView textViewFooter = (TextView) findViewById(R.id.textViewFooter);
        TextView textViewFooterIncredients = (TextView) findViewById(R.id.textViewFooterIncredients);
        dataSource.open();
        List<DataMensaMenu> list = dataSource.getDataForWeek(100);
        textViewFooter.setText(list.get(0).getHeader());
        textViewFooterIncredients.setText(list.get(0).getMenu());

        stringKw = String.format("KW %1$s", String.valueOf(Parser.getCurrentWeekOfTheYear()));
        textViewKw = (TextView) findViewById(R.id.textViewModulMensaKW);
        textViewKw.setText(stringKw);


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
    }


    public void showNextWeek(View view) {
        currentWeek = false;
        imageButtonLastWeek.setVisibility(View.VISIBLE);
        imageButtonNextWeek.setVisibility(View.INVISIBLE);
        dataSource.open();
        adapter.updateResults(dataSource.getDataAsArrayList(Parser.getnextWeekOfTheYear()));
        dataSource.close();
        stringKw = String.format("KW %1$s", String.valueOf(Parser.getnextWeekOfTheYear()));
        textViewKw = (TextView) findViewById(R.id.textViewModulMensaKW);
        textViewKw.setText(stringKw);

    }

    public void showLastWeek(View view) {
        currentWeek = true;
        imageButtonLastWeek.setVisibility(View.INVISIBLE);
        imageButtonNextWeek.setVisibility(View.VISIBLE);
        dataSource.open();
        adapter.updateResults(dataSource.getDataAsArrayList(Parser.getCurrentWeekOfTheYear()));
        stringKw = String.format("KW %1$s", String.valueOf(Parser.getCurrentWeekOfTheYear()));
        textViewKw = (TextView) findViewById(R.id.textViewModulMensaKW);
        textViewKw.setText(stringKw);
    }

    public void showAdditionalMenu(View view) {
        if (isSet) {
            isSet = false;
            listview.setAdapter(adapter);
            buttonMore.setText(getResources().getString(R.string.button_more));

        } else {
            isSet = true;
            DataSourceMensa dataSource = new DataSourceMensa(this);
            dataSource.open();
            additionalAdapter = new ListViewAdapterAdditionalMenu(this, dataSource.getDataForWeek(0));
            dataSource.close();
            listview.setAdapter(additionalAdapter);
            listview.deferNotifyDataSetChanged();
            buttonMore.setText(getResources().getString(R.string.button_less));
        }
    }

}
