package com.example.bheemnegi.geofenceapplication;

import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;

import io.nlopez.smartlocation.OnGeofencingTransitionListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.geofencing.utils.TransitionGeofence;

public class GeofenceIntentService extends Service {

    private static final String TAG = "GeofenceIntentService";

    private static int NOTIFICATION_ID = 333;
    public static int count=0;


    public GeofenceIntentService() {
        super();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        SmartLocation.with(getApplicationContext()).geofencing().start(new OnGeofencingTransitionListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGeofenceTransition(TransitionGeofence geoFence) {

                Log.v(TAG, "User has Entered the location");
                if (geoFence.getTransitionType() == Geofence.GEOFENCE_TRANSITION_ENTER) {
//                    AlertDialog dialog = new AlertDialog.Builder(getApplicationContext())
//                            .setMessage("User has Entered the GeoFence")
//                            .setPositiveButton("Ok", null)
//                            .create();
//                    dialog.show();

                    showNotification("User has Entered the location");


                }

                if (geoFence.getTransitionType() == Geofence.GEOFENCE_TRANSITION_EXIT) {
//                    AlertDialog dialog = new AlertDialog.Builder(getApplicationContext())
//                            .setMessage("User has left the GeoFence")
//                            .setPositiveButton("Ok", null)
//                            .create();
//                    dialog.show();
                   showNotification("User has left the Geofence");

                }
            }


        });
        return START_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void stopMyService() {
        stopSelf();
        showNotification("Service stopped");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification(String message) {
        Intent intent = new Intent(GeofenceIntentService.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(GeofenceIntentService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(GeofenceIntentService.this)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle("GeoLocation")
                .setContentIntent(pendingIntent)
                .setContentText(message).build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, notification);
    }

}
