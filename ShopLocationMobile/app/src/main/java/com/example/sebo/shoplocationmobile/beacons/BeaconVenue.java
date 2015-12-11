package com.example.sebo.shoplocationmobile.beacons;

/**
 * Created by Sebo on 2015-11-23.
 */
public class BeaconVenue {

    private int posX;
    private int posY;
    private String beaconUID;

    public BeaconVenue(String beaconUID, int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.beaconUID = beaconUID;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public String getBeaconUID() {
        return beaconUID;
    }
}
