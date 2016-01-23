package com.example.sebo.shoplocationmobile.beacons;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.example.sebo.shoplocationmobile.objects.Location;
import com.example.sebo.shoplocationmobile.objects.NullLocation;
import com.example.sebo.shoplocationmobile.products.Product;
import com.example.sebo.shoplocationmobile.products.Sector;
import com.example.sebo.shoplocationmobile.rest.ShopRestService;
import com.example.sebo.shoplocationmobile.rest.SimpleRestAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Sebo on 2016-01-04.
 */
public class LocationManager implements BeaconScanner.BeaconRegionListener {

    public static final String TAG = LocationManager.class.getSimpleName();

    private static LocationManager instance;

    public static LocationManager getInstance(Context context) {
        if (instance == null) {
            instance = new LocationManager(context);
        }

        return instance;
    }

    private LocationUpdateListener mListener;

    private BeaconScanner beaconScanner;
    private String lastKnownVenueId;

    private List<Sector> beaconVenues = new ArrayList<>();
    private Location customerPosition;

    private LocationManager() {
    }

    private LocationManager(Context context) {
        getBeaconsPosition();

        beaconScanner = BeaconScanner.getInstance(context);
        beaconScanner.setRegionListener(this);
    }

    public void getBeaconsPosition() {
        SimpleRestAdapter adapter = new SimpleRestAdapter();
        ShopRestService restService = adapter.getRetrofitAdapter().create(ShopRestService.class);

        restService.getSectors().enqueue(new Callback<List<Sector>>() {
            @Override
            public void onResponse(Response<List<Sector>> response, Retrofit retrofit) {
                beaconVenues.clear();
                beaconVenues.addAll(response.body());
                Log.d(TAG, "sectors synced.");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "downloading sectors error: " + t.getMessage());
            }
        });
    }

    public Location findProductLocation(Product product) {
        for (Sector sector: beaconVenues) {
            if (sector.getId().equals(product.getSectorId())) {
                return new Location(sector.getPosX(), sector.getPosY());
            }
        }
        Log.d(TAG, "findProductLocation: location not found");
        return new NullLocation();
    }

    @Override
    public void onLocationDetected(String beaconId) {
        Log.d(TAG, "onLocationDetected() called with: " + "beaconId = [" + beaconId + "]");
        if (beaconId.equals(lastKnownVenueId))
            return;

        for (Sector sector: beaconVenues) {
            if (sector.getId().equals(beaconId)) {
                customerPosition = new Location(sector.getPosX(), sector.getPosY());
                lastKnownVenueId = new String(beaconId);

                if (mListener != null) {
                    mListener.onLocationUpdate(customerPosition);
                }
            }
        }
    }

    public Location getCustomerPosition() {
        if (customerPosition == null) {
            return new NullLocation();
        }

        return customerPosition;
    }

    public void setListener(LocationUpdateListener mListener) {
        this.mListener = mListener;
    }

    public interface LocationUpdateListener {
        void onLocationUpdate(Location location);
    }
}
