package com.example.sebo.shoplocationmobile.presenters;

import android.util.Log;

import com.example.sebo.shoplocationmobile.beacons.BeaconScanner;
import com.example.sebo.shoplocationmobile.fragments.ProductListFragment;
import com.example.sebo.shoplocationmobile.products.Observer;
import com.example.sebo.shoplocationmobile.products.Product;
import com.example.sebo.shoplocationmobile.products.SearchEngine;
import com.example.sebo.shoplocationmobile.rest.ShopRestService;
import com.example.sebo.shoplocationmobile.rest.SimpleRestAdapter;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Sebo on 2016-01-04.
 */
public class ProductListPresenter implements MvpPresenter<ProductListFragment>, Observer, BeaconScanner.BeaconApproachListener {

    public static final String TAG = ProductListPresenter.class.getSimpleName();

    private ProductListFragment view;

    private SearchEngine searchEngine;
    private BeaconScanner beaconScanner;

    private String lastBeaconId;
    private boolean retrievingBeaconData;

    public ProductListPresenter() {
        lastBeaconId = "";
        searchEngine = SearchEngine.getInstance();
    }

    @Override
    public void attachView(ProductListFragment view) {
        this.view = view;

        beaconScanner = BeaconScanner.getInstance(view.getActivity());
        searchEngine.registerObserver(this);
        beaconScanner.setApproachListener(this);
    }

    @Override
    public void detachView() {
        this.view = null;

        searchEngine.unregisterObserver(this);
        beaconScanner.setApproachListener(null);
    }

    public void search(String tag) {
        view.showProgressDialog();
        searchEngine.executeSearch(tag);
    }

    @Override
    public void inform() {
        retrievingBeaconData = false;
        view.hideProgressDialog();
        view.setProducts(searchEngine.getSearchResult());
    }


    @Override
    public void onDeviceApproached(String beaconId) {
        Log.d(TAG, "onDeviceApproached() called with: " + "beaconId = [" + beaconId + "]");

        if (retrievingBeaconData || lastBeaconId.equals(beaconId))
            return;

        Log.d(TAG, "device " + beaconId + " approached");
        lastBeaconId = beaconId;
        retrievingBeaconData = true;

        view.showProgressDialog();
        searchEngine.getProductsByBeaconId(beaconId);
    }
}
