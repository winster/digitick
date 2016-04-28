package com.tickit.ticketmachine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.tickit.ticketmachine.adapter.FareDetailsAdapter;
import com.tickit.ticketmachine.vo.FareDetails;

import java.util.ArrayList;

public class FareDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.fare_details));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayList<FareDetails> fares = new ArrayList<FareDetails>();
        fares.addAll(TicketsFragment.mFares.values());
        FareDetailsAdapter adapter = new FareDetailsAdapter(this, fares);
        ListView listView = (ListView) findViewById(R.id.fareList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
