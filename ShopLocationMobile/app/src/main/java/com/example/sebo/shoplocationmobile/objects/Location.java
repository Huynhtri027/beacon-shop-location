package com.example.sebo.shoplocationmobile.objects;

/**
 * Created by Sebo on 2015-12-30.
 */
public class Location {
    private double posX;
    private double posY;

    public Location(double x, double y) {
        this.posX = x;
        this.posY = y;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
