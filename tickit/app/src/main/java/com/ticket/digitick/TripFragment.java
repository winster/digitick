package com.ticket.digitick;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ticket.digitick.adapter.TripAdapter;
import com.ticket.digitick.model.Trip;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TripFragment extends Fragment {
    RecyclerView tripView;
    String[] fromList = {"Bangalore", "Hyderabad", "Maharashtra", "Mangalore","Goa"};
    String[] toList = {"Kerala", "Tamil Nadu", "Pondicherry", "Chennai","Kanyakumari"};
    String[] dateList = {"10 April 16", "11 April 16", "12 April 16", "13 April 16", "14 April 16"};
    public TripFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_trip, container, false);
        tripView = (RecyclerView)root.findViewById(R.id.trips);
        ArrayList<Trip> tripArrayList = new ArrayList<>();

        for (int i=0; i<5;i++){
            Trip trip = new Trip();
            trip.setDate(dateList[i]);
            trip.setTripFrom(fromList[i]);
            trip.setTripTo(toList[i]);
            tripArrayList.add(trip);
        }

        tripView.setItemAnimator(new DefaultItemAnimator());
        tripView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tripView.setAdapter(new TripAdapter(getContext(), tripArrayList));
        return root;
    }
}
