package com.example.steven.safewalk;

/**
 * Created by Steven on 12/20/2016.
 */
public class TrafficElement {
    private DistanceElement distance;
    private DurationElement duration;
    private DurationInTrafficElement duration_in_traffic;

    public DistanceElement getDistance() {
        return distance;
    }

    public void setDistance(DistanceElement distance) {
        this.distance = distance;
    }

    public DurationElement getDuration() {
        return duration;
    }

    public void setDuration(DurationElement duration) {
        this.duration = duration;
    }

    public DurationInTrafficElement getDuration_in_traffic() {
        return duration_in_traffic;
    }

    public void setDuration_in_traffic(DurationInTrafficElement duration_in_traffic) {
        this.duration_in_traffic = duration_in_traffic;
    }
}
