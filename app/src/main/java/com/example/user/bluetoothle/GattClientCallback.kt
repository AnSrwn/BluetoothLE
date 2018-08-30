package com.example.user.bluetoothle

import android.bluetooth.*
import android.util.Log

class GattClientCallback(): BluetoothGattCallback() {

    override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)

        if (status == BluetoothGatt.GATT_FAILURE) {
            Log.d("DBG", "GATT connection failure")
            return
        } else if (status != BluetoothGatt.GATT_SUCCESS) {
            Log.d("DBG", "GATT connection success")
            return
        }

        if (newState == BluetoothProfile.STATE_CONNECTED) {
            Log.d("DBG", "Connected GATT service")
            gatt.discoverServices();
        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
        }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        super.onServicesDiscovered(gatt, status)
        if (status != BluetoothGatt.GATT_SUCCESS) {
            return
        }

        Log.d("DBG", "onServicesDiscovered()")

        for (gattService in gatt.services) {
        }
    }

    override fun onDescriptorWrite(gatt: BluetoothGatt, descriptor: BluetoothGattDescriptor, status: Int) {
        Log.d("DBG", "onDescriptorWrite")
    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
        Log.d("DBG", "Characteristic dataa received")
    }
}