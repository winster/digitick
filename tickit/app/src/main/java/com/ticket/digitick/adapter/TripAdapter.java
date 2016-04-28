package com.ticket.digitick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ticket.digitick.R;
import com.ticket.digitick.model.Trip;

import java.util.List;

/**
 * Created by jitin on 02-04-2016.
 */
public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    List<Trip> tripItems;
    private LayoutInflater inflater;

    public TripAdapter(Context context, List<Trip> tripItems) {
        inflater = LayoutInflater.from(context);
        this.tripItems = tripItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_trip, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder myViewHolder, int i) {
        Trip current = tripItems.get(i);
        myViewHolder.tripDate.setText(current.getDate());
        myViewHolder.tripFrom.setText(current.getTripFrom());
        myViewHolder.tripTo.setText(current.getTripTo());
    }

    @Override
    public int getItemCount() {
        return tripItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tripFrom;
        TextView tripTo;
        TextView tripDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tripFrom = (TextView) itemView.findViewById(R.id.trip_from);
            tripTo = (TextView) itemView.findViewById(R.id.trip_to);
            tripDate = (TextView) itemView.findViewById(R.id.trip_date);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
