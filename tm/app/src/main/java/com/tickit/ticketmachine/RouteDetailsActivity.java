package com.tickit.ticketmachine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.tickit.ticketmachine.adapter.RouteDetailsAdapter;
import com.tickit.ticketmachine.vo.StopDetails;

import java.util.ArrayList;

public class RouteDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.route_details));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayList<StopDetails> stops = new ArrayList<StopDetails>();
        stops.addAll(TicketsFragment.mStops);
        RouteDetailsAdapter adapter = new RouteDetailsAdapter(this, stops);
        ListView listView = (ListView) findViewById(R.id.routeList);
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
