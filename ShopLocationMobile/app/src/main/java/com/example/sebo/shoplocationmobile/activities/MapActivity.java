package com.example.sebo.shoplocationmobile.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sebo.shoplocationmobile.R;
import com.qozix.tileview.TileView;

public class MapActivity extends AppCompatActivity {

    public static final String TAG = MapActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TileView tileView = new TileView(this);
        tileView.setSize(1080, 1620);  // the original size of the untiled image
        //tileView.addDetailLevel(1f, "room-%d-%d.png", 240, 240);
        tileView.addDetailLevel(1f, "room/1000/%d_%d.png");
        tileView.addDetailLevel(0.5f, "room/500/%d_%d.png");
        tileView.addDetailLevel(0.25f, "room/250/%d_%d.png");
        tileView.addDetailLevel(0.125f, "room/125/%d_%d.png");
        tileView.setScaleLimits(0, 2);
        tileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick() called with: " + "v = [" + v + "]");
            }
        });
        setContentView(tileView);
    }
}
