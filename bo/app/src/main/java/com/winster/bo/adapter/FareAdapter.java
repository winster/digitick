package com.winster.bo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.winster.bo.R;
import com.winster.bo.vo.FareDetails;

import java.util.List;

/**
 * Created by wjose on 30/03/2016.
 */
public class FareAdapter extends ArrayAdapter<FareDetails> {
    List<FareDetails> fares;
    private CustomButtonListener listener;

    public FareAdapter(Context context, List<FareDetails> fares, CustomButtonListener listener) {
        super(context, 0, fares);
        this.fares = fares;
        this.listener = listener;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        FareDetails fareDetails = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fare_row, parent, false);
        }
        final EditText point = (EditText) convertView.findViewById(R.id.point);
        final EditText fareFull = (EditText) convertView.findViewById(R.id.fare_full);
        final EditText fareHalf = (EditText) convertView.findViewById(R.id.fare_half);
        final EditText fareStudent = (EditText) convertView.findViewById(R.id.fare_student);
        final EditText farePass = (EditText) convertView.findViewById(R.id.fare_pass);
        Button update = (Button) convertView.findViewById(R.id.updateFare);
        // Populate the data into the template view using the data object
        point.setText(String.valueOf(fareDetails.getPoint()));
        fareFull.setText(String.valueOf(fareDetails.getFull()));
        fareHalf.setText(String.valueOf(fareDetails.getHalf()));
        fareStudent.setText(String.valueOf(fareDetails.getStudent()));
        farePass.setText(String.valueOf(fareDetails.getPass()));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FareDetails fareObj = new FareDetails();
                fareObj.setPoint(Integer.valueOf(point.getText().toString()));
                fareObj.setFull(Double.valueOf(fareFull.getText().toString()));
                fareObj.setHalf(Double.valueOf(fareHalf.getText().toString()));
                fareObj.setStudent(Double.valueOf(fareStudent.getText().toString()));
                fareObj.setPass(Double.valueOf(farePass.getText().toString()));
                listener.onCustomButtonClick(fareObj, position, v);
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }

    public interface CustomButtonListener {
        void onCustomButtonClick(FareDetails stop, int position, View view);
    }
}
