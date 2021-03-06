package com.thomasdevelops.spaceappsfuego;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoogleMapsFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMyLocationButtonClickListener {

    GoogleMap map;
    private Location currentLocation;
    private EditText latText, lngText;
    MarkerOptions markerOptions;


    public GoogleMapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(com.thomasdevelops.spaceappsfuego.R.layout.fragment_maps, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(com.thomasdevelops.spaceappsfuego.R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        final Context context = this.getContext();
        int duration  = Toast.LENGTH_LONG;

        // Check if we have permission to check user location
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            CharSequence text = "Location Permission Granted";
            map.setMyLocationEnabled(true);
            map.setOnMyLocationButtonClickListener(this);
            map.setOnMyLocationClickListener(this);

            // If we are allowed to check the location, we grab the location using Location Manager and move the camera to that
            if(map.isMyLocationEnabled()){
                LocationManager locationManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                Location location = locationManager.getLastKnownLocation(locationManager
                        .getBestProvider(criteria, false));
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10));
            }
            //Toast toast = Toast.makeText(context, text, duration);
            //toast.show();
        } else {
            CharSequence text = "Sorry location permission not granted";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        // This lets the user place markers by tapping the map
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Creating a marker
                markerOptions = new MarkerOptions();
                // setting position for the marker
                markerOptions.position(latLng);
                // setting the title for the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // uses our custom icons
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_fire));
                map.clear();
                // move camera to touched position
                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                // finally, add the marker to the map
                map.addMarker(markerOptions);
                SharedPreferences preferences=context.getSharedPreferences("temp_marker",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("marker_lat", Double.toString(latLng.latitude));
                editor.putString("marker_lng", Double.toString(latLng.longitude));
                editor.commit();
            }
        });

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this.getContext(), "MyLocation button clicked", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this.getContext(), "Current Location:\n" + location, Toast.LENGTH_LONG).show();
        currentLocation = location;
    }
}
