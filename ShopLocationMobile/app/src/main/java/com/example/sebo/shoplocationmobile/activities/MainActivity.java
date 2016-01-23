package com.example.sebo.shoplocationmobile.activities;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.sebo.shoplocationmobile.beacons.BeaconScanner;
import com.example.sebo.shoplocationmobile.beacons.LocationManager;
import com.example.sebo.shoplocationmobile.fragments.BeaconFragment;
import com.example.sebo.shoplocationmobile.fragments.MapFragment;
import com.example.sebo.shoplocationmobile.R;
import com.example.sebo.shoplocationmobile.fragments.ProductListFragment;
import com.example.sebo.shoplocationmobile.products.Product;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ProductListFragment.OnProductItemClickedListener, BeaconFragment.OnFragmentInteractionListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static Intent createIntent(Context context, int productId) {
        Bundle extras = new Bundle();
        extras.putInt("productId", productId);

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtras(extras);

        return intent;
    }

    private BeaconScanner beaconScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        ButterKnife.bind(this);

        // intializing to sync sectors
        LocationManager.getInstance(this);

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            switchToProductListFragment(ProductListFragment.MAP_ONCLICK);
        } else {
            Integer productId = extras.getInt("productId");
            switchToMapFragment(productId);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        beaconScanner = BeaconScanner.getInstance(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        beaconScanner.stopScan();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void switchToProductListFragment(int viewType) {
        Log.d(TAG, "switchToProductListFragment() called with: " + "");

        ProductListFragment productListFragment = ProductListFragment.newInstance(ProductListFragment.MAP_ONCLICK);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, productListFragment, ProductListFragment.TAG);
        fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(ProductListFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void switchToMapFragment(Integer productId) {

        MapFragment mapFragment = MapFragment.newInstance(productId);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, mapFragment);
        fragmentTransaction.addToBackStack(ProductListFragment.TAG);
        fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commitAllowingStateLoss();
        Log.d(TAG, "switchToMapFragment() called with: " + productId);
    }

    public void switchToBeaconFragment() {
        Log.d(TAG, "switchToBeaconListFragment() called with: " + "");

        BeaconFragment beaconFragment = BeaconFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, beaconFragment, BeaconFragment.TAG);
        fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(BeaconFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onProductItemSelected(Product product) {
        Log.d(TAG, "onProductItemSelected() called with: " + "productId = [" + product.getId() + "]");
//        switchToMapFragment(findProductLocation(product));

        startActivity(ProductDetailsActivity.createIntent(this, product.getId()));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0 || count == 1) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
