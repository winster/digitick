package com.tickit.ticketmachine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tickit.ticketmachine.R;
import com.tickit.ticketmachine.vo.FareDetails;

import java.util.ArrayList;

/**
 * Created by wjose on 30/03/2016.
 */
public class FareDetailsAdapter extends ArrayAdapter<FareDetails> {

    public FareDetailsAdapter(Context context, ArrayList<FareDetails> stops) {
        super(context, 0, stops);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FareDetails fareDetails = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fare_row, parent, false);
        }
        TextView point = (TextView) convertView.findViewById(R.id.point);
        TextView standard = (TextView) convertView.findViewById(R.id.standard);
        TextView half = (TextView) convertView.findViewById(R.id.half);
        TextView student = (TextView) convertView.findViewById(R.id.student);
        point.setText(String.valueOf(fareDetails.getPoint()));
        standard.setText(String.valueOf(fareDetails.getFull()));
        half.setText(String.valueOf(fareDetails.getHalf()));
        student.setText(String.valueOf(fareDetails.getStudent()));
        return convertView;
    }
}
