package com.example.sebo.shoplocationmobile.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sebo.shoplocationmobile.R;
import com.example.sebo.shoplocationmobile.presenters.MapPresenter;
import com.qozix.tileview.TileView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends MvpFragment<MapPresenter> {

    public static final String TAG = MapFragment.class.getSimpleName();

    public static final String PRODUCT_ID = "arg1";
    // TODO: Rename and change types of parameters
    private Integer productId;

    private TileView tileView;
    private ImageView customerPositionMarker;
    private ImageView productPositionMarker;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(Integer productId) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getInt(PRODUCT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tileView = new TileView(getActivity().getApplicationContext());
        tileView.setSize(1080, 1620);  // the original size of the untiled image
        tileView.addDetailLevel(1f, "room/1000/%d_%d.png");
        tileView.addDetailLevel(0.5f, "room/500/%d_%d.png");
        tileView.addDetailLevel(0.25f, "room/250/%d_%d.png");
        tileView.addDetailLevel(0.125f, "room/125/%d_%d.png");
        tileView.setScaleLimits(0, 2);
        tileView.defineBounds(-20, 515, 320, -20);

        return tileView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.init(productId);
    }

    public void setProductLocation(double x, double y) {
        if (productPositionMarker == null) {
            productPositionMarker = new ImageView(getActivity().getApplicationContext());
            productPositionMarker.setImageResource(R.drawable.map_marker);

            tileView.addMarker(productPositionMarker, x, y, -0.5f, -1f);
            return;
        }

        tileView.moveMarker(productPositionMarker, x, y);
    }

    public void setCustomerLocation(double x, double y) {
        if (customerPositionMarker == null) {
            customerPositionMarker = new ImageView(getActivity().getApplicationContext());

            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.map_marker_circle);
            customerPositionMarker.setImageBitmap(Bitmap.createScaledBitmap(image, 160, 160, false));
//            customerPositionMarker.setImageResource(R.drawable.customer_position_marker);
            tileView.addMarker(customerPositionMarker, x, y, -0.5f, -0.75f);
            return;
        }

        tileView.moveMarker(customerPositionMarker, x, y);
    }

    @Override
    protected MapPresenter createPresenter() {
        return new MapPresenter();
    }
}
