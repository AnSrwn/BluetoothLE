package com.example.user.bluetoothle

class Device(var name : String, var mac : String, var rssi : Int, var isEnabled : Boolean) {

    override fun toString(): String {
        return "$name $mac $rssi "
    }
}