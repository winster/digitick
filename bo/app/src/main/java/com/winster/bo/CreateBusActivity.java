package com.winster.bo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.winster.bo.sqlite.DBHelper;
import com.winster.bo.vo.Bus;

/**
 * Created by wjose on 13/04/2016.
 */
public class CreateBusActivity extends AppCompatActivity {

    protected TextView busId;
    protected TextView busName;
    protected TextView error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createbus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.addBusTitle));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        busId = (TextView) findViewById(R.id.busid_text);
        busName = (TextView) findViewById(R.id.busname_text);
        error = (TextView) findViewById(R.id.error_text);

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        long ownerId= sharedPref.getLong(getString(R.string.bus_owner_id), 0);

        Button addBtn = (Button) findViewById(R.id.btnSubmit);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteTask task = new SQLiteTask();
                task.execute(new String[]{busId.getText().toString(), busName.getText().toString()});
            }
        });
        Button cancelBtn = (Button) findViewById(R.id.btnCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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


    private class SQLiteTask extends AsyncTask<String, Void, Long> {
        @Override
        protected Long doInBackground(String... params) {
            DBHelper dbHelper = new DBHelper(getBaseContext());

            Bus bus = new Bus();
            bus.setBusId(params[0]);
            bus.setBusName(params[1]);
            return dbHelper.insertBus(bus);
        }

        @Override
        protected void onPostExecute(Long result) {
            if(result==-1) {
                error.setText("Error while adding bus");
            } else {
                onBackPressed();
            }
        }
    }
}
