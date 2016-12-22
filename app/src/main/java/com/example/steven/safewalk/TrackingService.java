package com.example.steven.safewalk;

import android.os.AsyncTask;
import android.telephony.SmsManager;

/**
 * Created by Steven on 12/21/2016.
 */

public class TrackingService extends AsyncTask<TrackingRequest, Void, Void> {

    @Override
    protected Void doInBackground(TrackingRequest... params) {
        TrackingRequest request = params[0];
        boolean closer = (request.getCurrentLocation().distanceTo(request.getDestination()) <= request.getPreviousLocation().distanceTo(request.getDestination()));

        //boolean moving = request.getCurrentLocation().distanceTo(request.getPreviousLocation()) >
        SmsManager.getDefault().sendTextMessage("+14806008313", null, "test message", null, null);

        return null;
    }
}
