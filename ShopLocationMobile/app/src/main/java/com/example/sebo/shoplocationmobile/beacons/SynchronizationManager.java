package com.example.sebo.shoplocationmobile.beacons;

import android.util.Log;

import com.example.sebo.shoplocationmobile.products.Sector;
import com.example.sebo.shoplocationmobile.rest.ShopRestService;
import com.example.sebo.shoplocationmobile.rest.SimpleRestAdapter;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Sebo on 2015-11-27.
 */
public class SynchronizationManager {

    public static final String TAG = SynchronizationManager.class.getSimpleName();

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
        SimpleRestAdapter adapter = new SimpleRestAdapter();
        ShopRestService restService = adapter.getRetrofitAdapter().create(ShopRestService.class);

        restService.getSectors().enqueue(new Callback<List<Sector>>() {
            @Override
            public void onResponse(Response<List<Sector>> response, Retrofit retrofit) {
                listener.onBeaconsPositionDownloaded(response.body());
                Log.d(TAG, "sectors synced.");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "downloading sectors error: " + t.getMessage());
            }
        });
    }

    public void setListener(SynchronizationManagerListener listener) {
        this.listener = listener;
    }

    public interface SynchronizationManagerListener {
        void onBeaconsPositionDownloaded(List<Sector> venues);
    }
}
