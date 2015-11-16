package com.example.sebo.shoplocationmobile.fragments;

import android.content.Context;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sebo.shoplocationmobile.R;
import com.example.sebo.shoplocationmobile.activities.MainActivity;
import com.example.sebo.shoplocationmobile.adapters.ProductListAdapter;
import com.example.sebo.shoplocationmobile.products.Product;
import com.example.sebo.shoplocationmobile.products.SearchEngine;
import com.example.sebo.shoplocationmobile.products.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductListFragment extends Fragment implements Observer {

    public static final String TAG = ProductListFragment.class.getSimpleName();

    private List<Product> products;
    private ProductListAdapter adapter;
    private OnProductItemClickedListener mListener;
    private SearchEngine searchEngine;

    private Button searchButton;
    private EditText searchText;
    private RecyclerView productsRecyclerView;
    private Button beaconFragmentButton;

    public static ProductListFragment newInstance() {
        return new ProductListFragment();
    }

    public ProductListFragment() {
        this.products = new ArrayList<>();
        this.adapter = new ProductListAdapter(products);
        this.searchEngine = SearchEngine.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "observing search engine");
        SearchEngine.getInstance().registerObserver(this);

        try {
            mListener = (OnProductItemClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }

        Log.d(TAG, "listener: " + mListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        searchText = (EditText) view.findViewById(R.id.product_search_text);
        searchButton = (Button) view.findViewById(R.id.search_button);
        beaconFragmentButton = (Button) view.findViewById(R.id.beacon_button);
        productsRecyclerView = (RecyclerView) view.findViewById(R.id.products_list);

        beaconFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).switchToBeaconFragment();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEngine.executeSearch(searchText.getText().toString());
                searchText.setText("");
                searchText.clearFocus();
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        productsRecyclerView.setHasFixedSize(true);
        productsRecyclerView.setLayoutManager(layoutManager);
        productsRecyclerView.setAdapter(adapter);
        productsRecyclerView.addOnItemTouchListener(new RecyclerViewOnClickListener(getActivity().getApplicationContext(), new RecyclerViewOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(ProductListFragment.this.TAG, "clicked " + products.get(position));
                mListener.onProductItemSelected(products.get(position).getId());
            }
        }));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.d(TAG, "stopped observing search engine");
        SearchEngine.getInstance().unregisterObserver(this);
        mListener = null;
    }

    public void setProducts(List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void inform() {
        setProducts(searchEngine.getSearchResult());
        Log.d(TAG, searchEngine.getSearchResult().get(0).getTitle());
    }

    public interface OnProductItemClickedListener {
        void onProductItemSelected(int productId);
    }
}
