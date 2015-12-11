package com.example.sebo.shoplocationmobile.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sebo.shoplocationmobile.R;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sebo on 2015-11-15.
 */
public class BeaconListAdapter extends RecyclerView.Adapter<BeaconListAdapter.ViewHolder> {

    private List<IBeaconDevice> deviceList;

    public BeaconListAdapter(List<IBeaconDevice> deviceList) {
        this.deviceList = deviceList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.beacon_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IBeaconDevice device = deviceList.get(position);

        holder.beaconName.setText(device.getName());
        holder.beaconMajorMinor.setText("Major: " + device.getMajor() + " minor: " + device.getMinor());
        holder.beaconUuidDistance.setText("Distance: " + device.getDistance() + " UniqID: " + device.getUniqueId());
        holder.beaconBattery.setText(device.getBatteryPower() + "%");
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.beacon_name)
        TextView beaconName;

        @Bind(R.id.beacon_major_minor)
        TextView beaconMajorMinor;

        @Bind(R.id.beacon_uuid_distance)
        TextView beaconUuidDistance;

        @Bind(R.id.beacon_battery)
        TextView beaconBattery;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
