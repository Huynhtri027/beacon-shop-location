package com.example.sebo.shoplocationmobile.beacons;

import android.content.Context;
import android.util.Log;

import com.kontakt.sdk.android.ble.configuration.ActivityCheckConfiguration;
import com.kontakt.sdk.android.ble.configuration.ForceScanConfiguration;
import com.kontakt.sdk.android.ble.configuration.ScanPeriod;
import com.kontakt.sdk.android.ble.configuration.scan.EddystoneScanContext;
import com.kontakt.sdk.android.ble.configuration.scan.IBeaconScanContext;
import com.kontakt.sdk.android.ble.configuration.scan.ScanContext;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.device.BeaconRegion;
import com.kontakt.sdk.android.ble.device.EddystoneNamespace;
import com.kontakt.sdk.android.ble.discovery.BluetoothDeviceEvent;
import com.kontakt.sdk.android.ble.discovery.DistanceSort;
import com.kontakt.sdk.android.ble.discovery.EventType;
import com.kontakt.sdk.android.ble.discovery.ibeacon.IBeaconDeviceEvent;
import com.kontakt.sdk.android.ble.filter.ibeacon.IBeaconFilters;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.rssi.RssiCalculators;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.model.Beacon;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sebo on 2015-11-16.
 */
public class BeaconScanner implements ProximityManager.ProximityListener {

    public static final String TAG = BeaconScanner.class.getSimpleName();

    private ProximityManager proximityManager;
    private BeaconScanListener mListener;

    public BeaconScanner(Context context, BeaconScanListener listener) {
        this.mListener = listener;

        IBeaconScanContext iBeaconScanContext = new IBeaconScanContext.Builder()
                .setRssiCalculator(RssiCalculators.newLimitedMeanRssiCalculator(5))
                .setEventTypes(EnumSet.of(EventType.SPACE_ENTERED, EventType.SPACE_ABANDONED, EventType.DEVICE_DISCOVERED, EventType.DEVICES_UPDATE))
                .setDevicesUpdateCallbackInterval(TimeUnit.SECONDS.toMillis(2))
                .setDistanceSort(DistanceSort.ASC)
//                                .setIBeaconRegions(Collections.<IBeaconRegion>singletonList(
//                                        new BeaconRegion(KontaktSDK.DEFAULT_KONTAKT_BEACON_PROXIMITY_UUID, 1, 2)
//                                ))
                .build();


        ScanContext scanContext = new ScanContext.Builder()
                .setScanMode(ProximityManager.SCAN_MODE_BALANCED)
                .setScanPeriod(new ScanPeriod(TimeUnit.SECONDS.toMillis(7), TimeUnit.SECONDS.toMillis(3)))
                .setForceScanConfiguration(ForceScanConfiguration.DEFAULT)
                .setActivityCheckConfiguration(ActivityCheckConfiguration.DEFAULT)
                .setIBeaconScanContext(iBeaconScanContext)
                .build();

        final ProximityManager proximityManager = new ProximityManager(context);
        proximityManager.initializeScan(scanContext, new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.attachListener(BeaconScanner.this);
            }

            @Override
            public void onConnectionFailure() {
                Log.d(TAG, "connection error");
            }
        });
    }

    @Override
    public void onScanStart() {
    }

    @Override
    public void onScanStop() {
    }

    @Override
    public void onEvent(BluetoothDeviceEvent event) {
        IBeaconDeviceEvent iBeaconDeviceEvent = (IBeaconDeviceEvent) event;

        switch (event.getEventType()) {
            case DEVICES_UPDATE:
                Log.d(TAG, iBeaconDeviceEvent.getRegion().toString());
                List<IBeaconDevice> devices = iBeaconDeviceEvent.getDeviceList();
                mListener.onDevicesUpdate(devices);
                break;
            default:
                Log.d(TAG, iBeaconDeviceEvent.getRegion().toString());
        }
    }

    public void stopScan() {
        if (proximityManager != null) {
            proximityManager.finishScan();
            proximityManager.disconnect();
            Log.d(TAG, "stopScan() called with: " + "");
        }
    }

    public interface BeaconScanListener {
        void onDevicesUpdate(List<IBeaconDevice> devices);
    }
}
