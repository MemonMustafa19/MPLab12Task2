package com.example.mplab12task2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    TextView textView;
    Geocoder geocoder;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);


        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(2000);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);

        }
        geocoder = new Geocoder(getBaseContext(), Locale.getDefault());


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                try {
                    List<Address> addresses = geocoder.getFromLocation(
                            location.getLatitude(), location.getLongitude(), 1);
                    Address address = addresses.get(0);
                    textView.setText(address.getAddressLine(0) + " Updatedx" + String.valueOf(i));
                    i++;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        };

        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, locationCallback, null);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION: {
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG).show();

                }
            }
        }

    }

}
