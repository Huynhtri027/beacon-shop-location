package com.example.sebo.shoplocationmobile.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.sebo.shoplocationmobile.presenters.MvpPresenter;

/**
 * Created by Sebo on 2016-01-03.
 */
public abstract class MvpActivity<P extends MvpPresenter> extends AppCompatActivity {
    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    protected abstract P createPresenter();
}
