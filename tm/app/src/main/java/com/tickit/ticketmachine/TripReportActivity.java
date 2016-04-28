package com.tickit.ticketmachine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.tickit.ticketmachine.adapter.FareDetailsAdapter;
import com.tickit.ticketmachine.adapter.TicketReportAdapter;
import com.tickit.ticketmachine.sqlite.DBHelper;
import com.tickit.ticketmachine.sqlite.vo.Ticket;
import com.tickit.ticketmachine.vo.FareDetails;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wjose on 31/03/2016.
 */
public class TripReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        long currentTripNumber= sharedPref.getLong(getString(R.string.current_trip_number), 0);

        Intent intent =getIntent();
        String reportType = intent.getStringExtra("reportType");
        if(reportType!=null && reportType.equals("lastTrip")){
            currentTripNumber--;
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.trip_report)+" - "+currentTripNumber);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DBHelper dbHelper = new DBHelper(getBaseContext());
        ArrayList<Ticket> tickets = dbHelper.getTickets(currentTripNumber);
        if(tickets.size() > 0) {
            TextView startTime = (TextView) findViewById(R.id.tripStartTime);
            startTime.setText(new Date(tickets.get(0).getDate()).toString());
        }
        TicketReportAdapter adapter = new TicketReportAdapter(this, tickets);
        ListView listView = (ListView) findViewById(R.id.ticketList);
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
