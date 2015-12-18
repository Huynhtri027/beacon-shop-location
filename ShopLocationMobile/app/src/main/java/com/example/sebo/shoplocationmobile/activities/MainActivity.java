package com.example.sebo.shoplocationmobile.activities;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.sebo.shoplocationmobile.beacons.BeaconScanner;
import com.example.sebo.shoplocationmobile.beacons.SynchronizationManager;
import com.example.sebo.shoplocationmobile.fragments.BeaconFragment;
import com.example.sebo.shoplocationmobile.fragments.MapFragment;
import com.example.sebo.shoplocationmobile.R;
import com.example.sebo.shoplocationmobile.fragments.ProductListFragment;
import com.example.sebo.shoplocationmobile.products.Observer;
import com.example.sebo.shoplocationmobile.products.Product;
import com.example.sebo.shoplocationmobile.products.SearchEngine;
import com.example.sebo.shoplocationmobile.products.Sector;
import com.example.sebo.shoplocationmobile.rest.ShopRestService;
import com.example.sebo.shoplocationmobile.rest.SimpleRestAdapter;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements ProductListFragment.OnProductItemClickedListener, MapFragment.OnFragmentInteractionListener, BeaconFragment.OnFragmentInteractionListener,
        SynchronizationManager.SynchronizationManagerListener, Observer, BeaconScanner.BeaconRegionListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static Intent createIntent(Context context, int productId) {
        Bundle extras = new Bundle();
        extras.putInt("productId", productId);

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtras(extras);

        return intent;
    }

    @Bind(R.id.product_search_text)
    public EditText searchText;

    @Bind(R.id.search_button)
    public Button searchButton;

    @OnClick(R.id.search_button)
    public void onSearchButtonClick() {
        showProgressDialog();
        searchEngine.executeSearch(searchText.getText().toString());
        searchText.setText("");
        searchText.clearFocus();
    }

    private STATE state;
    private ProductListFragment productListFragment;
    private MapFragment mapFragment;
    private BeaconFragment beaconFragment;

    private SearchEngine searchEngine;
    private BeaconScanner beaconScanner;

    private ProgressDialog progressDialog;

    private boolean retrievingBeaconData = false;
    private String lastBeaconId = "";

    private SynchronizationManager syncManager;
    private List<Sector> beaconVenues = new ArrayList<>();
    private Pair<Double, Double> customerPosition;
    private String lastKnownVenueId;

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

        searchEngine = SearchEngine.getInstance();
        searchEngine.registerObserver(this);

        syncManager = SynchronizationManager.getInstance();
        syncManager.setListener(this);

        beaconScanner = BeaconScanner.getInstance(this);
        beaconScanner.setRegionListener(this);

        beaconVenues = syncManager.getVenues();
        if (beaconVenues.isEmpty()) {
            syncData();
        }

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            switchToProductListFragment(ProductListFragment.MAP_ONCLICK);
        } else {
            Integer productId = extras.getInt("productId");
            Pair<Double, Double> productLocation = findProductLocation(SearchEngine.getInstance().getProductById(productId));
            switchToMapFragment(productLocation);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart() called with: " + "");

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume() called with: " + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        searchEngine.unregisterObserver(this);
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

    private void syncData() {
        syncManager.getBeaconsPosition();
    }

    private void switchToProductListFragment(int viewType) {
        Log.d(TAG, "switchToProductListFragment() called with: " + "");
        state = STATE.PRODUCT_LIST;

        productListFragment = ProductListFragment.newInstance(ProductListFragment.MAP_ONCLICK);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, productListFragment, ProductListFragment.TAG);
        fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(ProductListFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void switchToMapFragment(Pair<Double, Double> productLocation) {
        state = STATE.SHOP_MAP;

        mapFragment = MapFragment.newInstance(productLocation, customerPosition);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, mapFragment);
        fragmentTransaction.addToBackStack(ProductListFragment.TAG);
        fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commitAllowingStateLoss();
        Log.d(TAG, "switchToMapFragment() called with: " + productLocation.first + "," + productLocation.second);
    }

    public void switchToBeaconFragment() {
        Log.d(TAG, "switchToBeaconListFragment() called with: " + "");
        state = STATE.BEACON_LIST;

        beaconFragment = BeaconFragment.newInstance();
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

    @Override
    public void onBeaconsPositionDownloaded(List<Sector> venues) {
        this.beaconVenues.clear();
        this.beaconVenues.addAll(venues);
    }

    @Override
    public void inform() {
        progressDialog.hide();

        if (state != STATE.PRODUCT_LIST) {
            switchToProductListFragment(ProductListFragment.MAP_ONCLICK);
        }

        productListFragment.setProducts(searchEngine.getSearchResult());
    }

    @Override
    public void onDeviceApproached(String beaconId) {
        Log.d(TAG, "onDeviceApproached() called with: " + "beaconId = [" + beaconId + "]");

        if (retrievingBeaconData || lastBeaconId.equals(beaconId))
            return;

        Log.d(TAG, "device " + beaconId + " approached");
        lastBeaconId = beaconId;
        retrievingBeaconData = true;

        showProgressDialog();
        SimpleRestAdapter adapter = new SimpleRestAdapter();
        ShopRestService restService = adapter.getRetrofitAdapter().create(ShopRestService.class);

        restService.getProductsBySector(beaconId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Response<List<Product>> response, Retrofit retrofit) {
                Log.d(TAG, "response: " + response.raw());
                Log.d(TAG, "response: " + response.code() + " - " + response.message());
                switchToProductListFragment(ProductListFragment.DETAILS_ONCLICK);
                productListFragment.setProducts(response.body());
                retrievingBeaconData = false;
                progressDialog.hide();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "failure: " + t.getMessage());
                lastBeaconId = "";
                retrievingBeaconData = false;
                progressDialog.hide();
            }
        });
    }

    @Override
    public void onLocationDetected(String beaconId) {
        Log.d(TAG, "onLocationDetected() called with: " + "beaconId = [" + beaconId + "]");
        if (beaconId.equals(lastKnownVenueId))
            return;

        for (Sector sector: beaconVenues) {
            if (sector.getId().equals(beaconId)) {
                customerPosition = new Pair<>(sector.getPosX(), sector.getPosY());
                lastKnownVenueId = new String(beaconId);

                if (state == STATE.SHOP_MAP) {
                    mapFragment.updateCustomerMarker(customerPosition);
                }
            }
        }
    }

    private Pair<Double, Double> findProductLocation(Product p) {
        for (Sector sector: beaconVenues) {
            if (p.getSectorId().equals(sector.getId())) {
                return new Pair<>(sector.getPosX(), sector.getPosY());
            }
        }

        return null;
    }

    public void showProgressDialog() {
        Log.d(TAG, "showing progress dialog");
        if (this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(this);

            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            this.progressDialog.setMessage("Loading... please wait...");
            this.progressDialog.setCancelable(false);
            this.progressDialog.setIndeterminate(true);
        }

        this.progressDialog.show();
    }

    private enum STATE {
        PRODUCT_LIST,
        SHOP_MAP,
        BEACON_LIST
    }
}
