package com.example.sebo.shoplocationmobile.beacons;

/**
 * Created by Sebo on 2015-11-27.
 */
public class BeaconOffer {

    private int offerId;
    private int productId;
    private String title;
    private String description;
    private String bargain;

    public BeaconOffer(int offerId, int productId, String title, String description,  String bargain) {
        this.offerId = offerId;
        this.title = title;
        this.description = description;
        this.productId = productId;
        this.bargain = bargain;
    }

    public int getOfferId() {
        return offerId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getProductId() {
        return productId;
    }

    public String getBargain() {
        return bargain;
    }
}
