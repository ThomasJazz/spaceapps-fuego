package com.thomasdevelops.spaceappsfuego;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReportFireActivity extends AppCompatActivity {
    private String latitude, longitude;
    private EditText latText, lonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_fire);
        latText = (EditText) findViewById(R.id.latitude);
        lonText = (EditText)findViewById(R.id.longitude);

        Button reportFireButton = findViewById(R.id.submit_fire);
        reportFireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                latitude = latText.getText().toString();
                longitude = lonText.getText().toString();

                Log.v("Latitude: ", latitude);
                Log.v("Longitude: ", longitude);
//                Toast.makeText(getApplicationContext(), "Latitude: " + latitude + "\nLongitude: " + longitude, Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(ReportFireActivity.this,
                        MainActivity.class);
                myIntent.putExtra("latitude", latitude);
                myIntent.putExtra("longitude", longitude);
                startActivity(myIntent);
            }
        });
    }
}
