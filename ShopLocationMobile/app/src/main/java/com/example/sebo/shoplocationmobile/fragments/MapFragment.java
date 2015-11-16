package com.example.sebo.shoplocationmobile.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sebo.shoplocationmobile.R;
import com.example.sebo.shoplocationmobile.products.Product;
import com.example.sebo.shoplocationmobile.products.SearchEngine;
import com.qozix.tileview.TileView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    public static final String TAG = MapFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PRODUCT_ID = "param1";

    // TODO: Rename and change types of parameters
    private String productId;

    private OnFragmentInteractionListener mListener;

    private TileView tileView;
    private ImageView customerPositionMarker;
    private ImageView productPositionMarker;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(PRODUCT_ID, param1);
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
            productId = getArguments().getString(PRODUCT_ID);
        }

        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
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

        if (productId != null) {
            Product product = SearchEngine.getInstance().getProductById(Integer.valueOf(productId));

            updateProductMarker(product.getPosX(), product.getPosY());
        }

        return tileView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateProductMarker(double posX, double posY) {
        if (customerPositionMarker == null) {
            customerPositionMarker = new ImageView(getActivity().getApplicationContext());
            customerPositionMarker.setImageResource(R.drawable.map_marker_blue);

            tileView.addMarker(customerPositionMarker, posX, posY, -0.5f, -0.5f);
            return;
        }

        tileView.moveMarker(customerPositionMarker, posX, posY);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


}
