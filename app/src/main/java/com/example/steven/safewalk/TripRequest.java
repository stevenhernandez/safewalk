package com.example.steven.safewalk;

import android.location.Location;

/**
 * Created by Steven on 12/21/2016.
 */

public class TripRequest {

    private Location origin;
    private String destination;
    private String mode;


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

}
