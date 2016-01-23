package com.example.sebo.shoplocationmobile.presenters;

/**
 * Created by Sebo on 2016-01-03.
 */
public interface MvpPresenter<V> {

    public void attachView(V view);

    public void detachView();
}
