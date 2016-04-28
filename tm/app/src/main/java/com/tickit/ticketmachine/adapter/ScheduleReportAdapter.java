package com.tickit.ticketmachine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tickit.ticketmachine.R;
import com.tickit.ticketmachine.sqlite.vo.Ticket;
import com.tickit.ticketmachine.sqlite.vo.Trip;

import java.util.ArrayList;

/**
 * Created by wjose on 31/03/2016.
 */
public class ScheduleReportAdapter extends ArrayAdapter<Trip> {

    public ScheduleReportAdapter(Context context, ArrayList<Trip> trips) {
        super(context, 0, trips);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Trip trip = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trip_report_row, parent, false);
        }
        TextView tripNumber = (TextView) convertView.findViewById(R.id.trip_number);
        TextView totalTickets = (TextView) convertView.findViewById(R.id.total_tickets);
        TextView totalPassengers = (TextView) convertView.findViewById(R.id.total_passengers);
        TextView totalFare = (TextView) convertView.findViewById(R.id.total_fare);
        tripNumber.setText(String.valueOf(trip.getTripNumber()));
        totalTickets.setText(String.valueOf(trip.getTotalTickets()));
        totalPassengers.setText(String.valueOf(trip.getTotalPassengers()));
        totalFare.setText(String.valueOf(trip.getTotalFare()));
        return convertView;
    }
}
