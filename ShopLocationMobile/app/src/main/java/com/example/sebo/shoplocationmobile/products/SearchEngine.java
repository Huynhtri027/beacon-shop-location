package com.example.sebo.shoplocationmobile.products;

import android.util.Log;

import java.util.List;

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
    }

    public void executeSearch(String tag) {
        searchResult = MockProducts.getInstance().find(tag);
        notifyObservers();
//        Log.d(TAG, "products found: " + searchResult.size());
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
