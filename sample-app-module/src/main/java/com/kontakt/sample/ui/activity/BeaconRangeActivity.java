package com.kontakt.sample.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.kontakt.sample.R;
import com.kontakt.sample.dialog.PasswordDialogFragment;
import com.kontakt.sdk.android.ble.configuration.BeaconActivityCheckConfiguration;
import com.kontakt.sdk.android.ble.configuration.ForceScanConfiguration;
import com.kontakt.sdk.android.ble.configuration.ScanContext;
import com.kontakt.sdk.android.ble.configuration.ScanPeriod;
import com.kontakt.sdk.android.ble.discovery.IBeaconAdvertisingPacket;
import com.kontakt.sdk.android.ble.filter.CustomFilter;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.rssi.RssiCalculators;
import com.kontakt.sdk.android.common.ibeacon.IBeaconDevice;
import com.kontakt.sdk.android.common.interfaces.SDKBiConsumer;

public class BeaconRangeActivity extends BaseBeaconRangeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void callOnListItemClick(int position) {
        final IBeaconDevice beacon = (IBeaconDevice) adapter.getItem(position);
        if (beacon != null) {
            PasswordDialogFragment.newInstance(getString(R.string.format_connect, beacon.getAddress()),
                    getString(R.string.password),
                    getString(R.string.connect),
                    new SDKBiConsumer<DialogInterface, String>() {
                        @Override
                        public void accept(DialogInterface dialogInterface, String password) {

                            beacon.setPassword(password.getBytes());

                            final Intent intent = new Intent(BeaconRangeActivity.this, BeaconManagementActivity.class);
                            intent.putExtra(BeaconManagementActivity.EXTRA_BEACON_DEVICE, beacon);

                            startActivityForResult(intent, REQUEST_CODE_CONNECT_TO_DEVICE);
                        }
                    }).show(getFragmentManager(), "dialog");
        }
    }
}
