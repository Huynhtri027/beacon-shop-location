package com.example.sebo.shoplocationmobile.products;

/**
 * Created by Sebo on 2015-11-11.
 */
public class Product {

    private int id;
    private String sectorId;
    private String name;
    private String desc;
    private String icon;
    private double price;

    public Product(int id, String sectorId, String name, String desc, String icon, double price) {
        this.id = id;
        this.sectorId = sectorId;
        this.name = name;
        this.desc = desc;
        this.icon = icon;
        this.price = price;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSectorId() {
        return sectorId;
    }
}
