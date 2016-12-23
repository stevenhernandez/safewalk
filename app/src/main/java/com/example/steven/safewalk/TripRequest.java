package com.example.steven.safewalk;

import android.location.Location;

/**
 * Created by Steven on 12/21/2016.
 */

public class TripRequest {

    private Location origin;
    private Location destination;
    private String mode;


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

}
