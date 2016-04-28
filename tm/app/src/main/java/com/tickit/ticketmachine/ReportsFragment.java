package com.tickit.ticketmachine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * Created by wjose on 22/03/2016.
 */
public class ReportsFragment extends ListFragment implements AdapterView.OnItemClickListener {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_reports,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Reports, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        if(position==0){
            Intent i = new Intent(getActivity(), TripReportActivity.class);
            startActivity(i);
        } else if(position==1){
            Intent i = new Intent(getActivity(), TripReportActivity.class);
            i.putExtra("reportType", "lastTrip");
            startActivity(i);
        } else if(position==2){
            Intent i = new Intent(getActivity(), ScheduleReportActivity.class);
            startActivity(i);
        } else if(position==3){
            Intent i = new Intent(getActivity(), ScheduleReportActivity.class);
            i.putExtra("reportType", "lastSchedule");
            startActivity(i);
        } else if(position==4){
            Intent i = new Intent(getActivity(), FareDetailsActivity.class);
            startActivity(i);
        } else if(position==5){
            Intent i = new Intent(getActivity(), RouteDetailsActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        }
    }
}
