package com.winster.bo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.winster.bo.R;
import com.winster.bo.vo.StopDetails;

import java.util.List;

/**
 * Created by wjose on 30/03/2016.
 */
public class RoutesAdapter extends ArrayAdapter<StopDetails> {
    List<StopDetails> stops;
    private CustomButtonListener listener;

    public RoutesAdapter(Context context, List<StopDetails> stops, CustomButtonListener listener) {
        super(context, 0, stops);
        this.stops = stops;
        this.listener = listener;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        StopDetails stopDetails = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stop_row, parent, false);
        }
        final TextView stop = (TextView) convertView.findViewById(R.id.stop);
        final EditText description = (EditText) convertView.findViewById(R.id.description);
        ImageButton update = (ImageButton) convertView.findViewById(R.id.updateStop);
        // Populate the data into the template view using the data object
        stop.setText(String.valueOf(stopDetails.getStop()));
        description.setText(stopDetails.getDescription());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopDetails stopObj = new StopDetails();
                stopObj.setDescription(description.getText().toString());
                stopObj.setStop(Integer.valueOf(stop.getText().toString()));
                listener.onCustomButtonClick(stopObj, position, v);
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }

    public interface CustomButtonListener {
        void onCustomButtonClick(StopDetails stop, int position, View view);
    }
}
