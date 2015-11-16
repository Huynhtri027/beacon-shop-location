package com.example.sebo.shoplocationmobile.products;

/**
 * Created by Sebo on 2015-11-11.
 */
public class Product {

    private int id;
    private String title;
    private String description;
    private String icon;
    private int price;
    private double posX;
    private double posY;

    public Product(int id, String title, String description, String icon, int price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.price = price;
    }

    public Product(int id, String title, String description, String icon, int price, double posX, double posY) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.price = price;
        this.posX = posX;
        this.posY = posY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
}
