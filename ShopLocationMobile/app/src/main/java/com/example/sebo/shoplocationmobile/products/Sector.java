package com.example.sebo.shoplocationmobile.products;

import java.util.List;

/**
 * Created by Pawel on 2015-12-13.
 */
public class Sector {
    private String id;
    private String name;
    private List<Product> products;
    private double posX;
    private double posY;

    public Sector(String id, String name, List<Product> products, double posX, double posY) {
        this.id = id;
        this.name = name;
        this.products = products;
        this.posX = posX;
        this.posY = posY;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }
}
