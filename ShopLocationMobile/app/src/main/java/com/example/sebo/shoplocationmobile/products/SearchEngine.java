package com.example.sebo.shoplocationmobile.products;

import android.util.Log;

import com.example.sebo.shoplocationmobile.rest.ShopRestService;
import com.example.sebo.shoplocationmobile.rest.SimpleRestAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Sebo on 2015-11-11.
 */
public class SearchEngine extends Observable {

    public static final String TAG = SearchEngine.class.getSimpleName();
    private static SearchEngine ourInstance = new SearchEngine();

    public static SearchEngine getInstance() {
        return ourInstance;
    }

    private List<Product> searchResult;

    private SearchEngine() {
        searchResult = new ArrayList<>();
    }

    public void executeSearch(String tag) {
        SimpleRestAdapter adapter = new SimpleRestAdapter();
        ShopRestService restService = adapter.getRetrofitAdapter().create(ShopRestService.class);

        restService.queryProduct(tag).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Response<List<Product>> response, Retrofit retrofit) {
                searchResult = response.body();
                notifyObservers();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, t.getMessage());
                notifyObservers();
            }
        });
        notifyObservers();
//        Log.d(TAG, "products found: " + searchResult.size());
    }

    public void getProductsByBeaconId(String beaconId) {
        SimpleRestAdapter adapter = new SimpleRestAdapter();
        ShopRestService restService = adapter.getRetrofitAdapter().create(ShopRestService.class);

        restService.getProductsBySector(beaconId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Response<List<Product>> response, Retrofit retrofit) {
                Log.d(TAG, "response: " + response.raw());
                searchResult = response.body();
                notifyObservers();
                Log.d(TAG, "response: " + response.code() + " - " + response.message());
//                view.setProducts(response.body());
//                view.hideProgressDialog();
//                retrievingBeaconData = false;
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "failure: " + t.getMessage());
                notifyObservers();
//                lastBeaconId = "";
//                retrievingBeaconData = false;
//                view.hideProgressDialog();
            }
        });
    }

    public Product getProductById(int id) {
        if (searchResult != null) {
            for (Product p: searchResult)
                if (p.getId() == id)
                    return p;
        }

        return null;
    }

    public List<Product> getSearchResult() {
        return searchResult;
    }
}
