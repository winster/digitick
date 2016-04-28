package com.tickit.ticketmachine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tickit.ticketmachine.R;
import com.tickit.ticketmachine.sqlite.vo.Ticket;
import com.tickit.ticketmachine.vo.FareDetails;

import java.util.ArrayList;

/**
 * Created by wjose on 30/03/2016.
 */
public class TicketReportAdapter extends ArrayAdapter<Ticket> {

    public TicketReportAdapter(Context context, ArrayList<Ticket> tickets) {
        super(context, 0, tickets);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ticket ticket = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ticket_report_row, parent, false);
        }
        TextView ticketId = (TextView) convertView.findViewById(R.id.ticketid);
        TextView from = (TextView) convertView.findViewById(R.id.ticket_from);
        TextView to = (TextView) convertView.findViewById(R.id.ticket_to);
        TextView fare = (TextView) convertView.findViewById(R.id.ticket_fare);
        ticketId.setText(String.valueOf(ticket.getTicketId()));
        from.setText(String.valueOf(ticket.getOrg()));
        to.setText(String.valueOf(ticket.getDst()));
        fare.setText(String.valueOf(ticket.getFare()));
        return convertView;
    }
}
