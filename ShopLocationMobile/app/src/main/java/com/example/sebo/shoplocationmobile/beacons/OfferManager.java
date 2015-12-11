package com.example.sebo.shoplocationmobile.beacons;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.sebo.shoplocationmobile.R;
import com.kontakt.sdk.android.ble.device.BeaconDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebo on 2015-11-27.
 */
public class OfferManager {

    private static OfferManager instance;

    public static OfferManager getInstance() {
        if (instance == null)
            instance = new OfferManager();

        return instance;
    }

    private OfferManager() {
        beaconOffers = new ArrayList<>();
    }

    private List<BeaconOffer> beaconOffers;

    public void showOfferNotification(BeaconOffer offer, Context context) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_corp_icon)
                        .setContentTitle("Biscuits -20% off!")
                        .setContentText("Pieguski are now -20% cheaper!");
        // Creates an explicit intent for an Activity in your app
//        Intent resultIntent = new Intent(this, ResultActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        // Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(ResultActivity.class);
//        // Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(Integer.valueOf(offer.getOfferId()), mBuilder.build());
    }

    public void checkForBeaconOffer(BeaconDevice device) {
    }
}
