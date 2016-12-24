package com.example.steven.safewalk;

import android.location.Location;
import android.telephony.SmsManager;

import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Steven on 12/21/2016.
 */

public class TrackingRequest {

    private List<Location> previousLocation = new ArrayList<>();
    private Location currentLocation;
    private Location destination;
    private Location closestLocation;
    private String contact;
    private long eta;
    private int counter = 0;
    private Stopwatch stopwatch = Stopwatch.createUnstarted();


    public List<Location> getPreviousLocations() {
        return previousLocation;
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

    public void resetCounter() {
        this.counter = 0;
    }

    public void incrementCounter() {
        this.counter++;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public long getEta() {
        return eta;
    }

    public void setEta(long eta) {
        this.eta = eta;
    }

    public Location getClosestLocation() {
        return closestLocation;
    }

    public void setClosestLocation(Location closestLocation) {
        this.closestLocation = closestLocation;
    }

    public List<Location> getPreviousLocation() {
        return previousLocation;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Stopwatch getStopwatch() {
        return stopwatch;
    }

    public void setStopwatch(Stopwatch stopwatch) {
        this.stopwatch = stopwatch;
    }
}
