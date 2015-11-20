package com.example.sebo.shoplocationmobile.beacons;

import android.content.Context;
import android.util.Log;

import com.kontakt.sdk.android.ble.configuration.ScanPeriod;
import com.kontakt.sdk.android.ble.configuration.scan.EddystoneScanContext;
import com.kontakt.sdk.android.ble.configuration.scan.IBeaconScanContext;
import com.kontakt.sdk.android.ble.configuration.scan.ScanContext;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.discovery.BluetoothDeviceEvent;
import com.kontakt.sdk.android.ble.discovery.EventType;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.common.model.Beacon;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sebo on 2015-11-16.
 */
public class BeaconScanner implements ProximityManager.ProximityListener {

    public static final String TAG = BeaconScanner.class.getSimpleName();

    private ProximityManager proximityManager;

    public BeaconScanner(Context context) {
        EddystoneScanContext eddystoneScanContext = new EddystoneScanContext.Builder()
                .setEventTypes(Arrays.asList(
                        EventType.SPACE_ENTERED,
                        EventType.SPACE_ABANDONED,
                        EventType.DEVICE_DISCOVERED,
                        EventType.DEVICES_UPDATE))
                .build();

        IBeaconScanContext iBeaconScanContext = new IBeaconScanContext.Builder()
                .setEventTypes(Arrays.asList(
                        EventType.SPACE_ENTERED,
                        EventType.SPACE_ABANDONED,
                        EventType.DEVICE_DISCOVERED,
                        EventType.DEVICES_UPDATE))
                .build();

        ScanContext scanContext = new ScanContext.Builder()
                .setEddystoneScanContext(EddystoneScanContext.DEFAULT)
                .setIBeaconScanContext(IBeaconScanContext.DEFAULT)
                .setScanPeriod(new ScanPeriod(TimeUnit.SECONDS.toMillis(7), TimeUnit.SECONDS.toMillis(0)))
                .build();

        proximityManager = new ProximityManager(context);
        proximityManager.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                Log.d(TAG, "kontakt.io backend connected");
            }

            @Override
            public void onConnectionFailure() {
                Log.d(TAG, "kontakt.io backend connection failure.");
            }
        });
        proximityManager.attachListener(this);
        proximityManager.initializeScan(scanContext);
        Log.d(TAG, "starting scan");

        if (proximityManager.isScanning()) {
            Log.d(TAG, "PM is scanning");
        }

        if (proximityManager.isConnected()) {
            Log.d(TAG, "PM is connected");
        }
    }

    @Override
    public void onScanStart() {
        Log.d(TAG, "onScanStart() called with: " + "");
    }

    @Override
    public void onScanStop() {
        Log.d(TAG, "onScanStop() called with: " + "");
    }

    @Override
    public void onEvent(BluetoothDeviceEvent event) {
        Log.d(TAG, "event type: " + event.getEventType());
    }

    public void stopScan() {
        if (proximityManager != null) {
            proximityManager.finishScan();
            proximityManager.disconnect();
            Log.d(TAG, "stopScan() called with: " + "");
        }
    }
}
