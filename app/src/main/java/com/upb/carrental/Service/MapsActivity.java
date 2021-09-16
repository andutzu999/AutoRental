package com.upb.carrental.Service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.upb.carrental.Auto.MainMenu;
import com.upb.carrental.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, ConnectionCallbacks, OnConnectionFailedListener {

    private GoogleMap mMap;
    HashMap<String, String> markerMap = new HashMap<String, String>();
    FusedLocationProviderClient client;
    SupportMapFragment mapFragment;
    Location mCurrentLocation;
    LocationRequest mLocationRequest;
    LocationCallback mLocationCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.ACCESS_FINE_LOCATION, 1);
            } else {
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1);
            }
        }

        
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull @NotNull LocationResult locationResult) {
                for (Location location: locationResult.getLocations()) {
                    mapFragment.getMapAsync(googleMap -> {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        MarkerOptions options = new MarkerOptions()
                                .position(latLng);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        googleMap.addMarker(options);
                    });
                }
            }
        };

        Button b = findViewById(R.id.button1);
        b.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainMenu.class);
            view.getContext().startActivity(intent);
        });
    }


    private void getCurrentLocation() {
        @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                mCurrentLocation = location;
                mapFragment.getMapAsync(googleMap -> {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions options = new MarkerOptions()
                            .position(latLng);
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    googleMap.addMarker(options);
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String @NotNull [] permissions,
            int @NotNull [] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MapsActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MapsActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> requestPermission(permission, permissionRequestCode));
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NotNull GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.ACCESS_FINE_LOCATION, 1);
            } else {
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1);
            }
        }

        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng bucharest1 = new LatLng(44.58D, 26.08D);
        Marker markerOne = mMap.addMarker(new MarkerOptions()
                .position(bucharest1)
                .title("AutoRental Otopeni")
                .snippet("See the vehicles available")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bucharest1, 11.0f));
        String idOne = markerOne.getId();
        markerMap.put(idOne, "action_one");

        LatLng bucharest2 = new LatLng(44.43D, 26.07D);
        Marker markerTwo = mMap.addMarker(new MarkerOptions()
                .position(bucharest2)
                .title("AutoRental Eroilor")
                .snippet("See the vehicles available")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bucharest2, 11.0f));
        String idTwo = markerTwo.getId();
        markerMap.put(idTwo, "action_two");

        LatLng bucharest3 = new LatLng(44.41D, 26.12D);
        Marker markerThree = mMap.addMarker(new MarkerOptions()
                .position(bucharest3)
                .title("AutoRental Vitan")
                .snippet("See the vehicles available")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bucharest3, 11.0f));
        String idThree = markerThree.getId();
        markerMap.put(idThree, "action_three");


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                String actionId = markerMap.get(marker.getId());
                String email = getIntent().getStringExtra("email");
                if (actionId.equals("action_one")) {
                    Intent i = new Intent(MapsActivity.this, MainMenu.class);
                    i.putExtra("email", email);
                    startActivity(i);
                } else if (actionId.equals("action_two")) {
                    Intent i = new Intent(MapsActivity.this, MainMenu.class);
                    i.putExtra("email", email);
                    startActivity(i);
                } else if (actionId.equals("action_three")) {
                    Intent i = new Intent(MapsActivity.this, MainMenu.class);
                    i.putExtra("email", email);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        getCurrentLocation();
    }

    @Override
    public void onConnected(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            client.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull @NotNull ConnectionResult connectionResult) {

    }
}
