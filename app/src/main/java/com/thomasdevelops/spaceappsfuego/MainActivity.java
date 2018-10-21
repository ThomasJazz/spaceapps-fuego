package com.thomasdevelops.spaceappsfuego;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.provider.Settings.Secure;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thomasdevelops.spaceappsfuego.pojo.FireReport;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GoogleMapsFragment gMapsFragment;
    private android.support.v4.app.FragmentManager manager;
    private GoogleMap mMap;
    private MapsActivity mapsActivity;
    private double latitudeFireReported, longitudeFireReported;

    final private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final private String testReports = "reports_test";
    private FireReport report;
    private String android_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android_id = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        // Test if we were able to get unique android device id
        Toast.makeText(getApplicationContext(), android_id, Toast.LENGTH_LONG).show();

        // set the initial content view
        setContentView(com.thomasdevelops.spaceappsfuego.R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(com.thomasdevelops.spaceappsfuego.R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(com.thomasdevelops.spaceappsfuego.R.id.addfire);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this,
                        ReportFireActivity.class);
                startActivity(myIntent);
            }
        });


        // We get the extras from the intent which is returned when we come back from the ReportFireActivity
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            latitudeFireReported = Double.valueOf(extras.getString("latitude"));
            longitudeFireReported = Double.valueOf(extras.getString("longitude"));
            Toast.makeText(getApplicationContext(), "Latitude: " + latitudeFireReported + "\nLongitude: " + longitudeFireReported, Toast.LENGTH_LONG).show();
            report = new FireReport(android_id, latitudeFireReported, longitudeFireReported);
            db.collection(testReports).add(report);
        }

        // add to database



        // Toast for testing that we can pass lat lng through activities


        DrawerLayout drawer = (DrawerLayout) findViewById(com.thomasdevelops.spaceappsfuego.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.thomasdevelops.spaceappsfuego.R.string.navigation_drawer_open, com.thomasdevelops.spaceappsfuego.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle); //setDrawerListener is deprecated
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(com.thomasdevelops.spaceappsfuego.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadMap();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(com.thomasdevelops.spaceappsfuego.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.thomasdevelops.spaceappsfuego.R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.thomasdevelops.spaceappsfuego.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     *
     */
    private void loadMap(){
        gMapsFragment = new GoogleMapsFragment();
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(com.thomasdevelops.spaceappsfuego.R.id.mainLayout, gMapsFragment).commit();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent;

        if (id == R.id.nav_chatter) {
            startActivity(new Intent(this,ContentFeed.class));
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_map) {
            GoogleMapsFragment gMapsFragment = new GoogleMapsFragment();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(com.thomasdevelops.spaceappsfuego.R.id.mainLayout, gMapsFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(com.thomasdevelops.spaceappsfuego.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
