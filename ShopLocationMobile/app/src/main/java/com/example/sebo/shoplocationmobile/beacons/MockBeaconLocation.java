package com.example.sebo.shoplocationmobile.beacons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebo on 2015-11-23.
 */
public class MockBeaconLocation {

    private static MockBeaconLocation instance;

    public static MockBeaconLocation getInstance() {
        if (instance == null) {
            instance = new MockBeaconLocation();
        }

        return instance;
    }

    private List<BeaconVenue> beaconVenueList;

    private MockBeaconLocation() {
        beaconVenueList = new ArrayList<>();

        beaconVenueList.add(new BeaconVenue("ELgt", 270, 300));
        beaconVenueList.add(new BeaconVenue("wezW", 20, 310));
    }

    public List<BeaconVenue> getBeaconVenueList() {
        return beaconVenueList;
    }
}

