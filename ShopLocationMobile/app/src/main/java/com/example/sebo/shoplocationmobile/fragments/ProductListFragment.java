package com.example.sebo.shoplocationmobile.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sebo.shoplocationmobile.R;
import com.example.sebo.shoplocationmobile.activities.MainActivity;
import com.example.sebo.shoplocationmobile.adapters.ProductListAdapter;
import com.example.sebo.shoplocationmobile.presenters.ProductDetailsPresener;
import com.example.sebo.shoplocationmobile.presenters.ProductListPresenter;
import com.example.sebo.shoplocationmobile.products.Product;
import com.example.sebo.shoplocationmobile.products.SearchEngine;
import com.example.sebo.shoplocationmobile.products.Observer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.util.Log.d;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductListFragment extends MvpFragment<ProductListPresenter> {

    public static final String TAG = ProductListFragment.class.getSimpleName();

    public static final int DETAILS_ONCLICK = 0;
    public static final int MAP_ONCLICK = 1;

    public static ProductListFragment newInstance(int viewType) {
        ProductListFragment plf = new ProductListFragment();
        plf.setViewType(viewType);

        return plf;
    }

    @Bind(R.id.products_list)
    public RecyclerView productsRecyclerView;
    @Bind(R.id.product_search_text)
    public EditText searchText;
    @Bind(R.id.search_button)
    public Button searchButton;

    @OnClick(R.id.search_button)
    public void onSearchButtonClick() {
        presenter.search(searchText.getText().toString().trim());
    }

    private List<Product> products;
    private ProductListAdapter adapter;
    private OnProductItemClickedListener mListener;
    private ProgressDialog progressDialog;

    private int viewType = MAP_ONCLICK;

    public ProductListFragment() {
        this.products = new ArrayList<>();
        this.adapter = new ProductListAdapter(products);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        d(TAG, "observing search engine");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        ButterKnife.bind(this, view);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        productsRecyclerView.setHasFixedSize(true);
        productsRecyclerView.setLayoutManager(layoutManager);
        productsRecyclerView.setAdapter(adapter);
        productsRecyclerView.addOnItemTouchListener(new RecyclerViewOnClickListener(getActivity().getApplicationContext(), new RecyclerViewOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                d(TAG, "clicked " + products.get(position));
                mListener.onProductItemSelected(products.get(position));
            }
        }));

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnProductItemClickedListener) getActivity();
            d(TAG, "listener attached");
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        d(TAG, "listener detached");
        mListener = null;
    }

    public void setProducts(List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
        adapter.notifyDataSetChanged();
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public void showProgressDialog() {
        d(TAG, "showing progress dialog");
        if (this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(getActivity());

            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            this.progressDialog.setMessage("Loading... please wait...");
            this.progressDialog.setCancelable(false);
            this.progressDialog.setIndeterminate(true);
        }

        this.progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.hide();
        }
    }

    @Override
    protected ProductListPresenter createPresenter() {
        return new ProductListPresenter();
    }


    public interface OnProductItemClickedListener {
        void onProductItemSelected(Product product);
    }
}
