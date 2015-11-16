package com.example.sebo.shoplocationmobile.products;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebo on 2015-11-11.
 */
public abstract class Observable {

    protected List<Observer> observers;

    public Observable() {
        observers = new ArrayList<>();
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    protected void notifyObservers() {
        for (Observer observer: observers) {
            observer.inform();
        }
    }
}
