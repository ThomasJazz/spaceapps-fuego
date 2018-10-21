package com.thomasdevelops.spaceappsfuego;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.Log;
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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.thomasdevelops.spaceappsfuego.pojo.FireReport;
import static android.support.constraint.Constraints.TAG;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GoogleMapsFragment gMapsFragment;
    private android.support.v4.app.FragmentManager manager;
    private GoogleMap gMap;
    private MapsActivity mapsActivity;
    private double latitudeFireReported, longitudeFireReported;
    private String markerLat, markerLng;
    private DrawerLayout drawer;
    private FloatingActionButton fab;

    final private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final private String testReports = "reports_test";
    private FireReport report;
    private String android_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set the initial content view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get users id
        android_id = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        // Test if we were able to get unique android device id
        //Toast.makeText(getApplicationContext(), android_id, Toast.LENGTH_LONG).show();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // this is the navigation drawer
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle); //setDrawerListener is deprecated
        toggle.syncState();

        // floating button
        fab = findViewById(R.id.addfire);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this,
                        ReportFireActivity.class);
                SharedPreferences preferences=getSharedPreferences("temp_marker",MODE_PRIVATE);
                String tempLat = preferences.getString("marker_lat", "");
                String tempLng = preferences.getString("marker_lng", "");
                EditText etLat = (EditText) view.findViewById(R.id.latitude);
                EditText etLng = (EditText) view.findViewById(R.id.longitude);
                myIntent.putExtra("Marker_Lat", tempLat);
                myIntent.putExtra("Marker_Lng", tempLng);
                startActivity(myIntent);
            }
        });

        // We get the extras from the intent which is returned when we come back from the ReportFireActivity
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            latitudeFireReported = Double.valueOf(extras.getString("latitude"));
            longitudeFireReported = Double.valueOf(extras.getString("longitude"));
            //Toast.makeText(getApplicationContext(), "Latitude: " + latitudeFireReported + "\nLongitude: " + longitudeFireReported, Toast.LENGTH_LONG).show();
            report = new FireReport(android_id, latitudeFireReported, longitudeFireReported);
            db.collection(testReports).add(report);
        }

        // with this we no longer need loadMap
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainLayout, new GoogleMapsFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_map);
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        // set up our manager and pop the last fragment off of it
        FragmentManager manager = getSupportFragmentManager();
        int stackSize = manager.getBackStackEntryCount();

        switch (id) {
            case R.id.nav_chatter:
                fab.setVisibility(View.INVISIBLE);
                manager.beginTransaction()
                        .replace(R.id.mainLayout, new SocFeed())
                        .commit();
                break;
            case R.id.nav_settings:
                fab.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_map:
                fab.setVisibility(View.VISIBLE);
                GoogleMapsFragment gMapsFragment = new GoogleMapsFragment();
                manager.beginTransaction()
                        .replace(R.id.mainLayout, gMapsFragment)
                        .commit();
                break;
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
