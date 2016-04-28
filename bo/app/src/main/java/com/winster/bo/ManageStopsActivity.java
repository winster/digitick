package com.winster.bo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.winster.bo.adapter.RoutesAdapter;
import com.winster.bo.sqlite.DBHelper;
import com.winster.bo.vo.StopDetails;

import java.util.List;

/**
 * Created by wjose on 16/04/2016.
 */
public class ManageStopsActivity extends AppCompatActivity implements RoutesAdapter.CustomButtonListener {

    List<StopDetails> stops;
    RoutesAdapter adapter;
    int stopCounter=0;
    EditText stopInput;
    EditText stopName;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managestops);
        final String busIdStr = getIntent().getStringExtra("busId");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.stopsTitle));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        populateStops();

        adapter = new RoutesAdapter(this, stops, this);
        final ListView listView = (ListView) findViewById(R.id.routeList);
        listView.setAdapter(adapter);

        stopInput = (EditText) findViewById(R.id.stop);
        stopName = (EditText) findViewById(R.id.description);
        stopCounter++;
        stopInput.setText(String.valueOf(stopCounter));
        Button addStopBtn = (Button) findViewById(R.id.addstop);
        addStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stopName.getText().toString().trim().length()==0){
                    error.setText("Name is mandatory");
                    error.setVisibility(View.VISIBLE);
                    return;
                }
                SQLiteAddStopsTask task = new SQLiteAddStopsTask();
                task.execute(new String[]{stopInput.getText().toString(), stopName.getText().toString()});
            }
        });
        Button removeAllStops = (Button) findViewById(R.id.removeAllStops);
        removeAllStops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteRemoveStopsTask task = new SQLiteRemoveStopsTask();
                task.execute(new String[]{});
            }
        });

        error = (TextView) findViewById(R.id.error_text);
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

    private void populateStops(){
        DBHelper dbHelper = new DBHelper(getBaseContext());
        stops = dbHelper.getStops();
        stopCounter = stops.size();
    }


    private class SQLiteRemoveStopsTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            DBHelper dbHelper = new DBHelper(getBaseContext());
            dbHelper.clearStops();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(!result) {
                error.setText("Error while removing stops");
                error.setVisibility(View.VISIBLE);
            } else {
                stops.clear();
                stopCounter=0;
                stopCounter++;
                stopInput.setText(String.valueOf(stopCounter));
                adapter.notifyDataSetChanged();
                error.setVisibility(View.GONE);
            }
        }
    }

    private class SQLiteAddStopsTask extends AsyncTask<String, Void, Long> {
        @Override
        protected Long doInBackground(String... params) {
            DBHelper dbHelper = new DBHelper(getBaseContext());
            StopDetails stop = new StopDetails(Integer.valueOf(params[0]), params[1]);
            return dbHelper.insertStop(stop);
        }

        @Override
        protected void onPostExecute(Long result) {
            if(result==-1) {
                error.setText("Stop should be unique");
                error.setVisibility(View.VISIBLE);
            } else {
                adapter.notifyDataSetChanged();
                error.setVisibility(View.GONE);

                stopCounter++;
                StopDetails newStop = new StopDetails(Integer.valueOf(stopInput.getText().toString()), stopName.getText().toString());
                stops.add(newStop);
                stopInput.setText(String.valueOf(stopCounter));
                stopName.setText("");
            }
        }
    }

    private class SQLiteUpdateStopsTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            DBHelper dbHelper = new DBHelper(getBaseContext());
            StopDetails stop = new StopDetails(Integer.valueOf(params[0]), params[1]);
            dbHelper.updateStop(stop);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(!result) {
                error.setText("Error while updating stop");
                error.setVisibility(View.VISIBLE);
            } else {
                adapter.notifyDataSetChanged();
                error.setVisibility(View.GONE);
            }
        }
    }

    public void onCustomButtonClick(StopDetails stop, int position, View view) {
        for(StopDetails stopObj : stops) {
            if(stop.getStop()==stopObj.getStop()) {
                stopObj.setDescription(stop.getDescription());
                break;
            }
        }
        SQLiteUpdateStopsTask task = new SQLiteUpdateStopsTask();
        task.execute(new String[]{String.valueOf(stop.getStop()), stop.getDescription()});
    }
}
