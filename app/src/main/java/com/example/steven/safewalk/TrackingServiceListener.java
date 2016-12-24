package com.example.steven.safewalk;

import android.location.Location;

/**
 * Created by Steven on 12/22/2016.
 */

public interface TrackingServiceListener {
    void arrived();

    void updateRequest(TrackingRequest request);

    void timeout();
}
