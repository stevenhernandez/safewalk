package com.example.steven.safewalk;

import java.util.List;

/**
 * Created by Steven on 12/20/2016.
 */

public class TrafficResponse {

    private List<String> destination_addresses;
    private List<String> origin_addresses;
    private List<TrafficInfo> rows;

    public List<String> getDestination_addresses() {
        return destination_addresses;
    }

    public void setDestination_addresses(List<String> destination_addresses) {
        this.destination_addresses = destination_addresses;
    }

    public List<String> getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(List<String> origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public List<TrafficInfo> getRows() {
        return rows;
    }

    public void setRows(List<TrafficInfo> rows) {
        this.rows = rows;
    }
}
