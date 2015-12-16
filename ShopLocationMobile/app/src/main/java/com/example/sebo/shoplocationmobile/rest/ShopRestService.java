package com.example.sebo.shoplocationmobile.rest;

import com.example.sebo.shoplocationmobile.products.Product;
import com.example.sebo.shoplocationmobile.products.Sector;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Sebo on 2015-12-13.
 */
public interface ShopRestService {
    @GET("/api/sectors/")
    Call<List<Sector>> getSectors();

    @GET("/api/sectors/{id}/products")
    Call<List<Product>> getProductsBySector(@Path("id") String id);

    @GET("/api/products")
    Call<List<Product>> queryProduct(@Query("q") String q);

    @GET("/api/products/{id}/?_expand=sector")
    Call<Product> getProductWithSector(@Path("id") String id);
}
