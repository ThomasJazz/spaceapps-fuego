package com.thomasdevelops.spaceappsfuego;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GoogleMapsFragment gMapsFragment;
    private android.support.v4.app.FragmentManager manager;
    private GoogleMap mMap;
    private MapsActivity mapsActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the initial content view
        setContentView(com.thomasdevelops.spaceappsfuego.R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(com.thomasdevelops.spaceappsfuego.R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(com.thomasdevelops.spaceappsfuego.R.id.addfire);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
