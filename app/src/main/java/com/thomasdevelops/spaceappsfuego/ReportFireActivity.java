package com.thomasdevelops.spaceappsfuego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReportFireActivity extends AppCompatActivity {
    private String latitude, longitude;
    private EditText latText, lngText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_fire);
        latText = (EditText) findViewById(R.id.latitude);
        lngText = (EditText) findViewById(R.id.longitude);


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            latText.setText(extras.getString("Marker_Lat"));
            lngText.setText(extras.getString("Marker_Lng"));
            //Toast.makeText(getApplicationContext(), "Marker Latitude: " + extras.getString("Marker_Lat") + "\nMarker Longitude: " + extras.get("Marker_Lng"), Toast.LENGTH_LONG).show();
        }

        Button reportFireButton = findViewById(R.id.submit_fire);
        reportFireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                latitude = latText.getText().toString();
                longitude = lngText.getText().toString();

                Log.v("Latitude: ", latitude);
                Log.v("Longitude: ", longitude);
               Toast.makeText(getApplicationContext(), "Fire successfully reported at: " + "\n\tLatitude: "+ latitude + "\n\tLongitude: " + longitude, Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(ReportFireActivity.this,
                        MainActivity.class);
                myIntent.putExtra("latitude", latitude);
                myIntent.putExtra("longitude", longitude);
                startActivity(myIntent);
            }
        });
    }
}
