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

import com.tickit.ticketmachine.adapter.ScheduleReportAdapter;
import com.tickit.ticketmachine.adapter.TicketReportAdapter;
import com.tickit.ticketmachine.sqlite.DBHelper;
import com.tickit.ticketmachine.sqlite.vo.Ticket;
import com.tickit.ticketmachine.sqlite.vo.Trip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by wjose on 31/03/2016.
 */
public class ScheduleReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.schedule_report));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        long fromTime = date.getTimeInMillis();
        date.add(Calendar.DAY_OF_MONTH, 1);
        long toTime = date.getTimeInMillis();

        Intent intent =getIntent();
        String reportType = intent.getStringExtra("reportType");
        if(reportType!=null && reportType.equals("lastSchedule")){
            date.add(Calendar.DAY_OF_MONTH, 1);
            toTime = date.getTimeInMillis();
            toolbar.setTitle(getResources().getText(R.string.last_schedule_report));
        }

        DBHelper dbHelper = new DBHelper(getBaseContext());
        ArrayList<Trip> trips = dbHelper.getTrips(fromTime, toTime);

        if(trips.size()>0) {
            TextView startTime = (TextView) findViewById(R.id.scheduleStartTime);
            startTime.setText(trips.get(0).getDate().toString());
            TextView totalTickets = (TextView) findViewById(R.id.totalTickets);
            TextView totalPassengers = (TextView) findViewById(R.id.totalPassengers);
            TextView totalFare = (TextView) findViewById(R.id.totalFare);
            int totalT = 0, totalP = 0;
            double totalF = 0;
            for(Trip trip : trips) {
                totalT+=trip.getTotalTickets();
                totalP+=trip.getTotalPassengers();
                totalF+=trip.getTotalFare();
            }
            totalTickets.setText(String.valueOf(totalT));
            totalPassengers.setText(String.valueOf(totalP));
            totalFare.setText(String.valueOf(totalF));
        }
        ScheduleReportAdapter adapter = new ScheduleReportAdapter(this, trips);
        ListView listView = (ListView) findViewById(R.id.tripList);
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
