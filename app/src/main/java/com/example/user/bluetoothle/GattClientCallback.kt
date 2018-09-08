package com.example.user.bluetoothle

import android.bluetooth.*
import android.util.Log
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT8
import android.bluetooth.BluetoothGattDescriptor

class GattClientCallback(private val main: MainActivity): BluetoothGattCallback() {

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
            gatt.discoverServices()
        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
        }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        super.onServicesDiscovered(gatt, status)
        if (status != BluetoothGatt.GATT_SUCCESS) {
            return
        }

        Log.d("DBG", "onServicesDiscovered()")

        val characteristic = gatt.getService(MainActivity.HEART_RATE_SERVICE_UUID).getCharacteristic(MainActivity.HEART_RATE_MEASUREMENT_CHAR_UUID)
        gatt.setCharacteristicNotification(characteristic, true)

        val desc = characteristic.getDescriptor(MainActivity.CLIENT_CHARACTERISTIC_CONFIG_UUID)
        Log.i("BLE", "Descriptor is $desc") // this is not null
        desc.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
        Log.i("BLE", "Descriptor write: " + gatt.writeDescriptor(desc)) // returns true
    }

    override fun onDescriptorWrite(gatt: BluetoothGatt, descriptor: BluetoothGattDescriptor, status: Int) {
        Log.d("DBG", "onDescriptorWrite")
    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
        Log.d("DBG", "Characteristic data received")

        val heartRate = characteristic.getIntValue(FORMAT_UINT8, 1)

        main.setBpmView(heartRate)
    }
}