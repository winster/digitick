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

import com.winster.bo.adapter.FareAdapter;
import com.winster.bo.sqlite.DBHelper;
import com.winster.bo.vo.FareDetails;

import java.util.List;

/**
 * Created by wjose on 16/04/2016.
 */
public class ManageFareActivity extends AppCompatActivity implements FareAdapter.CustomButtonListener {

    List<FareDetails> fares;
    FareAdapter adapter;
    int pointCounter=0;
    EditText pointInput;
    EditText fullInput;
    EditText halfInput;
    EditText studentInput;
    EditText passInput;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managefare);
        final String busIdStr = getIntent().getStringExtra("busId");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.fareTitle));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        populateFares();

        adapter = new FareAdapter(this, fares, this);
        final ListView listView = (ListView) findViewById(R.id.fareList);
        listView.setAdapter(adapter);

        pointInput = (EditText) findViewById(R.id.point);
        fullInput = (EditText) findViewById(R.id.fareFull);
        halfInput = (EditText) findViewById(R.id.fareHalf);
        studentInput = (EditText) findViewById(R.id.fareStudent);
        passInput = (EditText) findViewById(R.id.farePass);
        pointCounter++;
        pointInput.setText(String.valueOf(pointCounter));
        Button addFareBtn = (Button) findViewById(R.id.addfare);
        addFareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fullInput.getText().toString().trim().length()==0){
                    error.setText("full fare is mandatory");
                    error.setVisibility(View.VISIBLE);
                    return;
                }
                SQLiteAddFareTask task = new SQLiteAddFareTask();
                task.execute(new String[]{pointInput.getText().toString(), fullInput.getText().toString(),
                        halfInput.getText().toString(), studentInput.getText().toString(),
                        passInput.getText().toString()});
            }
        });
        Button removeAllFares = (Button) findViewById(R.id.removeAllFares);
        removeAllFares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteRemoveFareTask task = new SQLiteRemoveFareTask();
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

    private void populateFares(){
        DBHelper dbHelper = new DBHelper(getBaseContext());
        fares = dbHelper.getFares();
        pointCounter = fares.size();
    }


    private class SQLiteRemoveFareTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            DBHelper dbHelper = new DBHelper(getBaseContext());
            dbHelper.clearStops();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(!result) {
                error.setText("Error while removing fares");
                error.setVisibility(View.VISIBLE);
            } else {
                fares.clear();
                pointCounter=0;
                pointCounter++;
                pointInput.setText(String.valueOf(pointCounter));
                adapter.notifyDataSetChanged();
                error.setVisibility(View.GONE);
            }
        }
    }

    private class SQLiteAddFareTask extends AsyncTask<String, Void, Long> {
        @Override
        protected Long doInBackground(String... params) {
            DBHelper dbHelper = new DBHelper(getBaseContext());
            FareDetails fare = new FareDetails();
            fare.setPoint(Integer.valueOf(params[0]));
            fare.setFull(Double.valueOf(params[1]));
            fare.setHalf(Double.valueOf(params[2]));
            fare.setStudent(Double.valueOf(params[3]));
            fare.setPass(Double.valueOf(params[4]));
            return dbHelper.insertFare(fare);
        }

        @Override
        protected void onPostExecute(Long result) {
            if(result==-1) {
                error.setText("Fare point should be unique");
                error.setVisibility(View.VISIBLE);
            } else {
                adapter.notifyDataSetChanged();
                error.setVisibility(View.GONE);

                pointCounter++;
                FareDetails newFare = new FareDetails();
                newFare.setPoint(Integer.valueOf(pointInput.getText().toString()));
                newFare.setFull(Double.valueOf(fullInput.getText().toString()));
                newFare.setHalf(Double.valueOf(halfInput.getText().toString()));
                newFare.setStudent(Double.valueOf(studentInput.getText().toString()));
                newFare.setPass(Double.valueOf(passInput.getText().toString()));
                fares.add(newFare);
                pointInput.setText(String.valueOf(pointCounter));
                fullInput.setText("");
                halfInput.setText("");
                studentInput.setText("");
                passInput.setText("");
            }
        }
    }

    private class SQLiteUpdateFareTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            DBHelper dbHelper = new DBHelper(getBaseContext());
            FareDetails fare = new FareDetails();
            fare.setPoint(Integer.valueOf(params[0]));
            fare.setFull(Double.valueOf(params[1]));
            fare.setHalf(Double.valueOf(params[2]));
            fare.setStudent(Double.valueOf(params[3]));
            fare.setPass(Double.valueOf(params[4]));

            dbHelper.updateFare(fare);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(!result) {
                error.setText("Error while updating fare");
                error.setVisibility(View.VISIBLE);
            } else {
                adapter.notifyDataSetChanged();
                error.setVisibility(View.GONE);
            }
        }
    }

    public void onCustomButtonClick(FareDetails fare, int position, View view) {
        for(FareDetails fareObj : fares) {
            if(fare.getPoint()==fareObj.getPoint()) {
                fareObj.setFull(fare.getFull());
                fareObj.setHalf(fare.getHalf());
                fareObj.setStudent(fare.getStudent());
                fareObj.setPass(fare.getPass());
                break;
            }
        }
        SQLiteUpdateFareTask task = new SQLiteUpdateFareTask();
        task.execute(new String[]{String.valueOf(fare.getPoint()), String.valueOf(fare.getFull())
                , String.valueOf(fare.getHalf()), String.valueOf(fare.getStudent())
                , String.valueOf(fare.getPass())});
    }
}
