package com.example.sebo.shoplocationmobile.presenters;

import com.example.sebo.shoplocationmobile.beacons.LocationManager;
import com.example.sebo.shoplocationmobile.fragments.MapFragment;
import com.example.sebo.shoplocationmobile.objects.Location;
import com.example.sebo.shoplocationmobile.products.Product;
import com.example.sebo.shoplocationmobile.products.SearchEngine;

/**
 * Created by Sebo on 2016-01-05.
 */
public class MapPresenter implements MvpPresenter<MapFragment>, LocationManager.LocationUpdateListener {

    private MapFragment view;
    private LocationManager locationManager;
    private Integer productId;

    public void init(Integer productId) {
        this.productId = productId;

        retrieveProductLocation();
    }

    @Override
    public void attachView(MapFragment view) {
        this.view = view;

        retrieveCustomerLocation();
    }

    @Override
    public void detachView() {
        this.view = null;

        locationManager.setListener(null);
    }

    private void retrieveCustomerLocation() {
        locationManager = LocationManager.getInstance(view.getActivity());
        locationManager.setListener(this);

        Location userLocation = locationManager.getCustomerPosition();
        view.setCustomerLocation(userLocation.getPosX(), userLocation.getPosY());
    }

    private void retrieveProductLocation() {
        Product product = SearchEngine.getInstance().getProductById(productId);
        Location productLocation = locationManager.findProductLocation(product);

        view.setProductLocation(productLocation.getPosX(), productLocation.getPosY());
    }

    @Override
    public void onLocationUpdate(Location location) {
        view.setCustomerLocation(location.getPosX(), location.getPosY());
    }
}
