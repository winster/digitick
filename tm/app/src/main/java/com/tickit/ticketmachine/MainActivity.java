package com.tickit.ticketmachine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tickit.ticketmachine.utils.ConfigUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MenuItem selectedItem = null;
    Toolbar toolbar = null;
    boolean routeDirUp = true;
    Button returnBtn = null;

    public static String busId = "KL03A-8790";

    private static final int REQUEST_ENABLE_BT = 101;

    TicketsFragment ticketFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ConfigUtils.getInstance().init(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(ConfigUtils.getRouteTitle(true));
        routeDirUp = true;
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        returnBtn = (Button) findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (routeDirUp) {
                    toolbar.setTitle(ConfigUtils.getRouteTitle(false));
                    ticketFragment.initDataset(MainActivity.this, false, true);
                    routeDirUp = false;
                } else {
                    toolbar.setTitle(ConfigUtils.getRouteTitle(true));
                    ticketFragment.initDataset(MainActivity.this, true, true);
                    routeDirUp = true;
                }
            }
        });

        ticketFragment = new TicketsFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, ticketFragment);
        fragmentTransaction.commit();
        selectedItem = navigationView.getMenu().getItem(0);
        selectedItem.setChecked(true);

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(getString(R.string.current_trip_number), 0);
        editor.putBoolean(getString(R.string.active_trip), false);
        editor.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                //startActivity(new Intent(Intent.ACTION_VIEW, data));
            } else {
                Toast.makeText(this, R.string.bluetooth_not_supported, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        toolbar.setTitle(item.getTitle());
        if (id == R.id.nav_ticket) {
            toolbar.setTitle(ConfigUtils.getRouteTitle(true));
            returnBtn.setVisibility(View.VISIBLE);
            ticketFragment  = new TicketsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, ticketFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_settings) {
            returnBtn.setVisibility(View.INVISIBLE);
            SettingsFragment fragment = new SettingsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_report) {
            returnBtn.setVisibility(View.INVISIBLE);
            ReportsFragment fragment = new ReportsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();
        }
        selectedItem.setChecked(false);
        selectedItem = item;
        selectedItem.setChecked(true);
        routeDirUp = true;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
