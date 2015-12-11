package com.example.sebo.shoplocationmobile.beacons;

import java.util.List;

/**
 * Created by Sebo on 2015-11-27.
 */
public class SynchronizationManager {

    private static SynchronizationManager instance;

    public static SynchronizationManager getInstance() {
        if (instance == null)
            instance = new SynchronizationManager();

        return instance;
    }

    private SynchronizationManager() {
    }

    private SynchronizationManagerListener listener;

    public void getBeaconsPosition() {
        List<BeaconVenue> venues = MockBeaconLocation.getInstance().getBeaconVenueList();

        if (listener != null) {
            listener.onBeaconsPositionDownloaded(venues);
        }
    }

    public void setListener(SynchronizationManagerListener listener) {
        this.listener = listener;
    }

    public interface SynchronizationManagerListener {
        void onBeaconsPositionDownloaded(List<BeaconVenue> venues);
    }
}
