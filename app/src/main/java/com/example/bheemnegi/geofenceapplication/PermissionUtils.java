package com.example.bheemnegi.geofenceapplication;


public class PermissionUtils {


}
///*
//package com.example.bheemnegi.geofenceapplication;
//
//import android.Manifest;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.location.Location;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.android.gms.location.DetectedActivity;
//import com.google.android.gms.location.Geofence;
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.Circle;
//import com.google.android.gms.maps.model.CircleOptions;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.LatLngBounds;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import io.nlopez.smartlocation.OnActivityUpdatedListener;
//import io.nlopez.smartlocation.OnGeofencingTransitionListener;
//import io.nlopez.smartlocation.OnLocationUpdatedListener;
//import io.nlopez.smartlocation.SmartLocation;
//import io.nlopez.smartlocation.geofencing.model.GeofenceModel;
//import io.nlopez.smartlocation.geofencing.utils.TransitionGeofence;
//import io.nlopez.smartlocation.location.config.LocationAccuracy;
//import io.nlopez.smartlocation.location.config.LocationParams;
//import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;
//
//public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, OnActivityUpdatedListener {
//
//    private static final String TAG = "MainActivity";
//    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
//    SupportMapFragment supportMapFragment;
//    GoogleMap mGoogleMap;
//    LocationGooglePlayServicesProvider provider;
//    private Marker fenceMarker;
//    Marker locationMarker;
//    private GeofenceModel geoUser;
//    private Circle geoFenceLimits, geoUserLimits;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        checkingPermissions();
//        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        supportMapFragment.getMapAsync(this);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mGoogleMap = googleMap;
//        Log.v(TAG, "On Map ready Called");
//        startGeoFence();
//    }
//
//    private void startGeoFence() {
//        provider = new LocationGooglePlayServicesProvider();
//        provider.setCheckLocationSettings(true);
//
//        SmartLocation smartLocation = new SmartLocation.Builder(this)
//                .logging(true).build();
//
//
//        smartLocation.location(provider).start(new OnLocationUpdatedListener() {
//            @Override
//            public void onLocationUpdated(Location location) {
//
//            }
//        });
//        smartLocation.activity().start(this);
//
//
//        /**GeoFence for the location where we need to reach
//         *
//         */
//GeofenceModel geoRamada = new GeofenceModel.Builder("Ramada")
//        .setTransition(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
//        .setLatitude(28.450097)
//        .setLongitude(77.071368)
//        .setExpiration(Geofence.NEVER_EXPIRE)
//        .setRadius(25.00f)
//        .build();
//
//    // User fence Model
//
//    LatLng latLng = new LatLng(28.450097, 77.071368);
//    MarkerOptions markerOptions = getMarkerOption(latLng, "Office location");
//
//        if (mGoogleMap != null) {
//                // Remove last geoFenceMarker
//                if (fenceMarker != null) {
//                fenceMarker.remove();
//                }
//                fenceMarker = mGoogleMap.addMarker(markerOptions);
//                }
//                createFence(markerOptions, 25);
//                LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                //Marker added
//                builder.include(fenceMarker.getPosition());
//                LatLngBounds bounds = builder.build();
//
//                int width = getResources().getDisplayMetrics().widthPixels;
//                int height = getResources().getDisplayMetrics().heightPixels;
//                int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen
//                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
//                mGoogleMap.animateCamera(cu);
//                getUserLocationMarker();
//
//                smartLocation.geofencing().add(geoRamada)
//                .start(new OnGeofencingTransitionListener() {
//@Override
//public void onGeofenceTransition(TransitionGeofence geofence) {
//        if (geofence.getTransitionType() == Geofence.GEOFENCE_TRANSITION_ENTER) {
//        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
//        .setMessage("User Entered the GeoFence")
//        .setNegativeButton("Ok", null)
//        .create();
//        dialog.show();
//        }
//
//        if (geofence.getTransitionType() == Geofence.GEOFENCE_TRANSITION_EXIT) {
//        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
//        .setMessage("User has left the GeoFence")
//        .setNegativeButton("Ok", null)
//        .create();
//        dialog.show();
//        }
//        }
//        });
//        }
//
//private GeofenceModel createUserFenceModel(Location location, float radius) {
//        return new GeofenceModel.Builder("User")
//        .setLatitude(location.getLatitude())
//        .setLongitude(location.getLongitude())
//        .setRadius(radius)
//        .build();
//        }
//
//private void createFence(MarkerOptions markerOptions, double radius) {
//        if (geoFenceLimits != null)
//        geoFenceLimits.remove();
//
//        CircleOptions circleOptions = new CircleOptions()
//        .center(markerOptions.getPosition())
//        .strokeColor(Color.argb(50, 70, 70, 70))
//        .fillColor(Color.argb(100, 150, 150, 150))
//        .radius(radius);
//        geoFenceLimits = mGoogleMap.addCircle(circleOptions);
//        }
//
//private void createUserFence(MarkerOptions markerOptions, double radius) {
//        if (geoUserLimits != null)
//        geoUserLimits.remove();
//
//        CircleOptions circleOptions = new CircleOptions()
//        .center(markerOptions.getPosition())
//        .strokeColor(Color.argb(50, 70, 70, 70))
//        .fillColor(Color.argb(100, 150, 150, 150))
//        .radius(radius);
//        geoUserLimits = mGoogleMap.addCircle(circleOptions);
//        }
//
//private void getUserLocationMarker() {
//
//        /** User's location, a notification will be sent to the user
//         *  if he enters inside the GeoFence
//         */
//        long mLocTrackingInterval = 1000; // 5 sec
//        float trackingDistance = 0;
//        LocationAccuracy trackingAccuracy = LocationAccuracy.HIGH;
//        LocationParams.Builder builder = new LocationParams.Builder()
//        .setAccuracy(trackingAccuracy)
//        .setDistance(trackingDistance)
//        .setInterval(mLocTrackingInterval);
//
//        SmartLocation.with(MainActivity.this)
//        .location()
//        .continuous()
//        .start(new OnLocationUpdatedListener() {
//@Override
//public void onLocationUpdated(Location location) {
//
////                        geoUser = createUserFenceModel(location, 3.00f);
//
//        LatLng coordinates = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions options = getMarkerOption(coordinates, "User Location");
//        if (mGoogleMap != null) {
//        if (locationMarker != null) {
//        locationMarker.remove();
//        }
//        locationMarker = mGoogleMap.addMarker(options);
//        Log.v(TAG, "User Location");
//        }
//
////                        createUserFence(options, 3.00);
//
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        builder.include(locationMarker.getPosition());
//        LatLngBounds bounds = builder.build();
//
//        int width = getResources().getDisplayMetrics().widthPixels;
//        int height = getResources().getDisplayMetrics().heightPixels;
//        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen
//
//        CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
//        }
//        });
//
//        }
//
//@Override
//public void onActivityUpdated(DetectedActivity detectedActivity) {
//        }
//
//public MarkerOptions getMarkerOption(LatLng latLng, String title) {
//        return new MarkerOptions()
//        .position(latLng)
//        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
//        .title(title);
//        }
//
//
//private void showActivity(DetectedActivity detectedActivity) {
//        }
//
//private void checkingPermissions() {
//        if (ContextCompat.checkSelfPermission
//        (this, android.Manifest.permission.ACCESS_FINE_LOCATION
//        ) != PackageManager.PERMISSION_GRANTED &&
//        ActivityCompat.checkSelfPermission
//        (this,
//        android.Manifest.permission.ACCESS_COARSE_LOCATION)
//        != PackageManager.PERMISSION_GRANTED) {
//
//        if (ActivityCompat.shouldShowRequestPermissionRationale
//        (this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
//        new AlertDialog.Builder(this)
//        .setTitle("Need Location Permission")
//        .setMessage("Location permission is needed")
//        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//@Override
//public void onClick(DialogInterface dialogInterface, int i) {
//        //Prompt the user once explanation has been shown
//        ActivityCompat.requestPermissions(MainActivity.this,
//        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//        MY_PERMISSIONS_REQUEST_LOCATION);
//        }
//        })
//        .create()
//        .show();
//        } else {
//        ActivityCompat.requestPermissions(this,
//        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
//        MY_PERMISSIONS_REQUEST_LOCATION);
//        }
//
//        }
//        }
//
//@Override
//public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//        case MY_PERMISSIONS_REQUEST_LOCATION: {
//        // If request is cancelled, the result arrays are empty.
//        if (grantResults.length > 0
//        && grantResults[0] == PackageManager.PERMISSION_GRANTED
//        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//        // permission was granted, yay! Do the
//        // contacts-related task you need to do.
//        } else {
//        // permission denied, boo! Disable the
//        // functionality that depends on this permission.
//        }
//        return;
//        }
//
//        // other 'case' lines to check for other
//        // permissions this app might request.
//        }
//        }
//
//
//        }
//
//
//
// */