package com.example.user.bluetoothle

import android.bluetooth.BluetoothDevice

class Device(var name : String, var mac : String, var rssi : Int, var bluetoothDevice: BluetoothDevice, var isEnabled : Boolean) {

    override fun toString(): String {
        return "$name $mac $rssi "
    }
}