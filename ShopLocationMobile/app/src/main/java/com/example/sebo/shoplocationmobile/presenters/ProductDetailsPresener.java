package com.example.sebo.shoplocationmobile.presenters;

import com.example.sebo.shoplocationmobile.activities.MainActivity;
import com.example.sebo.shoplocationmobile.activities.ProductDetailsActivity;
import com.example.sebo.shoplocationmobile.products.Product;
import com.example.sebo.shoplocationmobile.products.SearchEngine;

/**
 * Created by Sebo on 2016-01-03.
 */
public class ProductDetailsPresener implements MvpPresenter<ProductDetailsActivity> {

    private ProductDetailsActivity view;
    private Product product;

    public void init(Integer productId) {
        product = SearchEngine.getInstance().getProductById(productId);

        view.setToolbarTitle(product.getName());
        view.setProductInfo(product.getName(), product.getDesc(), product.getPrice());
    }

    public void showMap() {
        view.startActivity(MainActivity.createIntent(view, product.getId()));
    }

    @Override
    public void attachView(ProductDetailsActivity view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
