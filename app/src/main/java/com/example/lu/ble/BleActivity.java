package com.example.lu.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lu.R;
import com.example.lu.base.BaseActivity;
import com.example.lu.log.KLog;
import com.example.lu.recyclerview.CommonRecyclerViewAdapter;
import com.example.lu.recyclerview.MultiItemTypeAdapterForRV;
import com.example.lu.recyclerview.base.CommonRecyclerViewHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import no.nordicsemi.android.ble.BleManager;
import no.nordicsemi.android.ble.BleManagerCallbacks;

public class BleActivity extends BaseActivity {

    List<BLEDevice> deviceList = new ArrayList<>();
    List<Integer> rssList=new ArrayList<>();

    private BluetoothAdapter mBluetoothAdapter;
    BluetoothManager mBluetoothManager;
    CommonRecyclerViewAdapter<BLEDevice> adapter;

    BleManager<BleManagerCallbacks> bleManager;
    @Override
    public void initData() {


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        adapter=new CommonRecyclerViewAdapter<BLEDevice>(this,R.layout.item_device_list,deviceList) {
            @Override
            protected void convert(CommonRecyclerViewHolder holder, BLEDevice o, int position) {
                holder.setText(R.id.tvDeviceName,o.device.getName());
                holder.setText(R.id.tvMac,o.device.getAddress());
                holder.setText(R.id.tvDbValue,String.valueOf(o.mRssi));
            }

        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapterForRV.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                KLog.i("connect......"+deviceList.get(position).device.getAddress());

                bleManager.connect(deviceList.get(position).device).retry(3).enqueue();
//                bleManager.disconnect().enqueue();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        findViewById(R.id.btnRefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        recyclerView.setAdapter(adapter);

         mBluetoothManager = (BluetoothManager)this.getSystemService(Context.BLUETOOTH_SERVICE);
        if (this.mBluetoothManager != null) {
            this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
        }

        mBluetoothAdapter.startLeScan(this.mLeScanCallback);


        bleManager=new BleManager<BleManagerCallbacks>(this) {
            @NonNull
            @Override
            protected BleManagerGattCallback getGattCallback() {
                return new BleManagerGattCallback() {
                    @Override
                    protected boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {
                        return false;
                    }

                    @Override
                    protected void onDeviceDisconnected() {

                    }
                };
            }
        };
        bleManager.setGattCallbacks(callbacks);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        },4000);
    }
    BleManagerCallbacks callbacks=new BleManagerCallbacks() {
        @Override
        public void onDeviceConnecting(@NonNull BluetoothDevice device) {
            KLog.i("onDeviceConnecting "+device);
        }

        @Override
        public void onDeviceConnected(@NonNull BluetoothDevice device) {
            KLog.i("onDeviceConnected "+device.getAddress());
        }

        @Override
        public void onDeviceDisconnecting(@NonNull BluetoothDevice device) {
            KLog.i("onDeviceDisconnecting "+device);
        }

        @Override
        public void onDeviceDisconnected(@NonNull BluetoothDevice device) {
            KLog.i("onDeviceDisconnected "+device);
        }

        @Override
        public void onLinkLossOccurred(@NonNull BluetoothDevice device) {

        }

        @Override
        public void onServicesDiscovered(@NonNull BluetoothDevice device, boolean optionalServicesFound) {
            KLog.i("onServicesDiscovered");
        }

        @Override
        public void onDeviceReady(@NonNull BluetoothDevice device) {

        }

        @Override
        public void onBondingRequired(@NonNull BluetoothDevice device) {

        }

        @Override
        public void onBonded(@NonNull BluetoothDevice device) {

        }

        @Override
        public void onBondingFailed(@NonNull BluetoothDevice device) {

        }

        @Override
        public void onError(@NonNull BluetoothDevice device, @NonNull String message, int errorCode) {

        }

        @Override
        public void onDeviceNotSupported(@NonNull BluetoothDevice device) {

        }
    };
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback(){
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

            BLEDevice bleDevice = new BLEDevice();
            bleDevice.mDeviceAddress = device.getAddress();
            bleDevice.device = device;
            bleDevice.mRssi = rssi;
            if (!deviceList.contains(bleDevice)){
                deviceList.add(bleDevice);
                Collections.sort(deviceList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }
    };

    public class BLEDevice implements Serializable, Comparable<BLEDevice> {
        private static final long serialVersionUID = -5217710157640312976L;
        public String mDeviceName;
        public String mDeviceAddress;
        public String mDeviceProduct;
        public String mDeviceVersion;
        public int power;
        public int mRssi;
        public boolean isBind;
        BluetoothDevice device;
        public BLEDevice() {
        }

        public int compareTo(BLEDevice var1) {
            return this.mRssi > var1.mRssi ? -1 : (this.mRssi == var1.mRssi ? 0 : 1);
        }

        public boolean equals(Object var1) {
            if (var1 == null) {
                return false;
            } else {
                BLEDevice var2 = (BLEDevice)var1;
                return this.mDeviceAddress.equals(var2.mDeviceAddress);
            }
        }

        @Override
        public String toString() {
            return "BLEDevice{" +
                    "mDeviceName='" + mDeviceName + '\'' +
                    ", mDeviceAddress='" + mDeviceAddress + '\'' +
                    ", mDeviceProduct='" + mDeviceProduct + '\'' +
                    ", mDeviceVersion='" + mDeviceVersion + '\'' +
                    ", mRssi=" + mRssi +
                    '}';
        }
    }
    @Override
    public int getLayoutResID() {
        return R.layout.activity_ble;
    }
}
