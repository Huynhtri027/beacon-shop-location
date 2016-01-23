package com.example.sebo.shoplocationmobile.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.example.sebo.shoplocationmobile.presenters.MvpPresenter;

/**
 * Created by Sebo on 2016-01-03.
 */
public abstract class MvpFragment<P extends MvpPresenter> extends Fragment {

    protected P presenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (presenter == null) {
            presenter = createPresenter();
        }

        presenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        presenter.detachView();
    }

    protected abstract P createPresenter();
}
