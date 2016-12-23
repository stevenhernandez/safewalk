package com.example.steven.safewalk;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;


/**
 * Created by Steven on 12/21/2016.
 */
public class LocationEventService extends IntentService implements  GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, TrafficResponseListener  {


    private GoogleApiClient mGoogleApiClient;
    private TrackingRequest trackingRequest;
    private Intent intent;
    private static Timer mTimer = null;
    public static final long NOTIFY_INTERVAL = 1 * 60 * 1000;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LocationEventService(String name) {
        super(name);
    }

    public LocationEventService() {
        super("Background Location Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
        this.intent = intent;

    }

    @Override
    public void TrafficResponseCompleted(TrafficResponse trafficResponse) {
        TrafficElement trafficElement = trafficResponse.getRows().get(0).getElements().get(0);

        if (trafficElement.getDuration_in_traffic() != null) {
            trackingRequest.setEta(trafficElement.getDuration_in_traffic().getValue());
        } else {
            trackingRequest.setEta(trafficElement.getDuration().getValue());
        }
        //trackingRequest.setEta(trafficResponse.getRows()[0].getElements()[0].getDuration_in_traffic().getValue());
        Toast.makeText(getApplicationContext(), trafficResponse.getRows().get(0).getElements().get(0).getDistance().getText(), Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), trafficResponse.getRows().get(0).getElements().get(0).getDuration().getText(), Toast.LENGTH_LONG).show();
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);

    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            new TrackingService().execute(trackingRequest);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, createLocationRequest(), this);
            Location destination = getLocationFromAddress(intent.getExtras().getString("destination"));
            TripRequest request = new TripRequest();
            request.setDestination(destination);
            request.setMode(intent.getExtras().getString("mode"));
            request.setOrigin(LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient));

            trackingRequest = new TrackingRequest();
            trackingRequest.setCurrentLocation(request.getOrigin());
            trackingRequest.setPreviousLocation(trackingRequest.getCurrentLocation());
            trackingRequest.setDestination(destination);
            trackingRequest.setContact(intent.getExtras().getString("contact"));

            DirectionsService directionsService = new DirectionsService();
            directionsService.delegate = this;
            directionsService.execute(request);
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(6000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    public Location getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        Location p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new Location("Destination");
            p1.setLatitude(location.getLatitude());
            p1.setLongitude(location.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p1;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        trackingRequest.setPreviousLocation(trackingRequest.getCurrentLocation());
        trackingRequest.setCurrentLocation(location);
    }
}
