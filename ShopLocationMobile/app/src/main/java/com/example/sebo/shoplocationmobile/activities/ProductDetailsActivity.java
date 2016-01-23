package com.example.sebo.shoplocationmobile.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sebo.shoplocationmobile.R;
import com.example.sebo.shoplocationmobile.presenters.MvpPresenter;
import com.example.sebo.shoplocationmobile.presenters.ProductDetailsPresener;
import com.example.sebo.shoplocationmobile.products.Product;
import com.example.sebo.shoplocationmobile.products.SearchEngine;
import com.squareup.okhttp.Interceptor;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailsActivity extends MvpActivity<ProductDetailsPresener> {

    public static Intent createIntent(Context context, int productId) {
        Bundle extras = new Bundle();
        extras.putInt("productId", productId);

        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtras(extras);

        return intent;
    }

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.product_large_image)
    ImageView productImage;
    @Bind(R.id.product_title)
    TextView productTitle;
    @Bind(R.id.product_description_short)
    TextView productDescriptionShort;
    @Bind(R.id.product_price)
    TextView productPrice;
    @Bind(R.id.product_description_long)
    TextView productDescriptionLong;
    @Bind(R.id.product_details_fab)
    FloatingActionButton actionButton;

    @OnClick(R.id.product_details_fab)
    public void onFabClick(View view) {
        presenter.showMap();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            presenter.init(extras.getInt("productId"));
        } else {
            finish();
        }
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    public void setProductInfo(String title, String description, double price) {
        productTitle.setText(title);
        productDescriptionShort.setText(description);
        productPrice.setText(price + "$");
    }

    @Override
    protected ProductDetailsPresener createPresenter() {
        return new ProductDetailsPresener();
    }


}
