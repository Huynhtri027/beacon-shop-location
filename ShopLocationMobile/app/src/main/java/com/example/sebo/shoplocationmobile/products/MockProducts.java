package com.example.sebo.shoplocationmobile.products;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebo on 2015-11-11.
 */
public class MockProducts {

    private static final String TAG = MockProducts.class.getSimpleName();
    private static MockProducts ourInstance = new MockProducts();

    public static MockProducts getInstance() {
        return ourInstance;
    }

    private List<Product> products;

    private MockProducts() {
        products = new ArrayList<>();

        products.add(new Product(1, "Mleko", "Mleko UHT 2.0%", null, 2, 50, 50));
        products.add(new Product(2, "Platki", "Platki kukurydziane", null, 5, 100, 100));
        products.add(new Product(3, "Chleb", "Chleb wiejski krojony", null, 2, 150, 150));
        products.add(new Product(4, "Ketchup", "Ketchup pudliszki bez konserwantow", null, 3, 200, 200));
        products.add(new Product(5, "Wodka", "Wodka zytnia", null, 20, 250, 250));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public List<Product> find(String tag) {
        List<Product> result = new ArrayList<>();

        Log.d(TAG, "searching for: " + tag);
        for (Product product: products) {
            Log.d(TAG, "product: " + product.getTitle().toLowerCase() + ", description: " + product.getDescription().toLowerCase());
            if (product.getTitle().toLowerCase().contains(tag) || product.getDescription().toLowerCase().contains(tag)) {
                Log.d(TAG, "added: " + product.getTitle());
                result.add(product);
            }
        }

        return result;
    }
}
