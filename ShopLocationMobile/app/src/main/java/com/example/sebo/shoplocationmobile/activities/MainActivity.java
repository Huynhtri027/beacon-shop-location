package com.example.sebo.shoplocationmobile.activities;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.sebo.shoplocationmobile.fragments.BeaconFragment;
import com.example.sebo.shoplocationmobile.fragments.MapFragment;
import com.example.sebo.shoplocationmobile.R;
import com.example.sebo.shoplocationmobile.fragments.ProductListFragment;

public class MainActivity extends AppCompatActivity implements ProductListFragment.OnProductItemClickedListener, MapFragment.OnFragmentInteractionListener, BeaconFragment.OnFragmentInteractionListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private STATE state;
    private ProductListFragment productListFragment;
    private MapFragment mapFragment;
    private BeaconFragment beaconFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        productListFragment = ProductListFragment.newInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart() called with: " + "");
        state = STATE.PRODUCT_LIST;

        showInitialFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume() called with: " + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    private void showInitialFragment() {
        if (state == STATE.PRODUCT_LIST) {
            switchToProductListFragment();
        } else {
            //switchToMapFragment();
        }
    }

    private void switchToProductListFragment() {
        Log.d(TAG, "switchToProductListFragment() called with: " + "");
        state = STATE.PRODUCT_LIST;

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, productListFragment, ProductListFragment.TAG);
        fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(ProductListFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void switchToMapFragment(String productId) {
        state = STATE.SHOP_MAP;

        mapFragment = MapFragment.newInstance(productId);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, mapFragment);
        fragmentTransaction.addToBackStack(ProductListFragment.TAG);
        fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commitAllowingStateLoss();
        Log.d(TAG, "switchToMapFragment() called with: " + "");
    }

    public void switchToBeaconFragment() {
        Log.d(TAG, "switchToProductListFragment() called with: " + "");

        beaconFragment = BeaconFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, beaconFragment, BeaconFragment.TAG);
        fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(BeaconFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onProductItemSelected(int productId) {
        Log.d(TAG, "onProductItemSelected() called with: " + "productId = [" + productId + "]");
        switchToMapFragment(String.valueOf(productId));

//        Product product = SearchEngine.getInstance().getProductById(productId);
//        if (product != null) {
//            Log.d(TAG, "updating marker");
////            mapFragment.updateProductMarker(product.getPosX(), product.getPosY());
//        }
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

    private enum STATE {
        PRODUCT_LIST,
        SHOP_MAP
    }
}
