package com.example.steven.safewalk;

import android.location.Location;

/**
 * Created by Steven on 12/21/2016.
 */

public class TrackingRequest {

    private Location previousLocation;
    private Location currentLocation;
    private Location destination;
    private String contact;
    private int counter;

    public Location getPreviousLocation() {
        return previousLocation;
    }

    public void setPreviousLocation(Location previousLocation) {
        this.previousLocation = previousLocation;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
