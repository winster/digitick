package com.tickit.ticketmachine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tickit.ticketmachine.R;
import com.tickit.ticketmachine.vo.StopDetails;

import java.util.ArrayList;

/**
 * Created by wjose on 30/03/2016.
 */
public class RouteDetailsAdapter extends ArrayAdapter<StopDetails> {
    public RouteDetailsAdapter(Context context, ArrayList<StopDetails> stops) {
        super(context, 0, stops);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StopDetails stopDetails = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.route_row, parent, false);
        }
        TextView stop = (TextView) convertView.findViewById(R.id.stop);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        // Populate the data into the template view using the data object
        stop.setText(String.valueOf(stopDetails.getStop()));
        description.setText(stopDetails.getDescription());
        // Return the completed view to render on screen
        return convertView;
    }
}
