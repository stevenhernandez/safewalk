package com.example.steven.safewalk;

import android.os.AsyncTask;
import android.telephony.SmsManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by Steven on 12/21/2016.
 */

public class TrackingService extends AsyncTask<TrackingRequest, Void, TrackingRequest> {

    public TrackingServiceListener delegate = null;

    @Override
    protected TrackingRequest doInBackground(TrackingRequest... params) {
        TrackingRequest request = params[0];
        float distToDest = request.getCurrentLocation().distanceTo(request.getDestination());
        boolean arrived = distToDest <= 150;

        if (!request.getStopwatch().isRunning() && distToDest < request.getClosestLocation().distanceTo(request.getDestination())) {
            request.getStopwatch().start();
        }

        if (arrived) {
            SmsManager.getDefault().sendTextMessage(request.getContact(), null, "Your friend has arrived at his/her destination", null, null);
            delegate.arrived();
            return request;
        }

        if (request.getStopwatch().isRunning()) {
            if (distToDest < request.getClosestLocation().distanceTo(request.getDestination())) {
                request.setClosestLocation(request.getCurrentLocation());
                request.resetCounter();
            } else {
                request.incrementCounter();
                if (request.getCounter() >= 4) {
                    SmsManager.getDefault().sendTextMessage(request.getContact(), null, "Your friend has not moved any closer to their destination in " + request.getCounter() * 5 + " minutes. The closest they made it was about http://www.google.com/maps/place/"
                            + request.getClosestLocation().getLatitude() + "," + request.getClosestLocation().getLongitude() + " and they are most recently at http://www.google.com/maps/place/"
                            + request.getCurrentLocation().getLatitude() + "," + request.getCurrentLocation().getLongitude(), null, null);
                    if (request.getCounter() >= 10) {
                        delegate.timeout();
                    }
                    return request;
                }
            }
        }

        if (request.getStopwatch().elapsed(TimeUnit.SECONDS) > request.getEta()*1.2) {
            SmsManager.getDefault().sendTextMessage(request.getContact(), null, "Your friend has not arrived at their destination despite their trip already lasting 20% longer than expected they are most recently at http://www.google.com/maps/place/" +
                                                + request.getCurrentLocation().getLatitude() + "," + request.getCurrentLocation().getLongitude(), null, null);
        }

        return request;
    }

    @Override
    protected void onPostExecute(TrackingRequest result) {
        delegate.updateRequest(result);
    }


}
