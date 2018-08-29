package com.example.user.bluetoothle

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.*
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mScanCallback: ScanCallback? = null
    private var mBluetoothLeScanner: BluetoothLeScanner? = null
    private val devices: kotlin.collections.MutableList<Device> = java.util.ArrayList()
    private var adapter: DeviceListAdapter? = null

    companion object {
        const val SCAN_PERIOD: Long = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager.adapter


        buttonStartScan.setOnClickListener {
            startScan()
        }
    }

    private fun startScan() {
        if (hasPermissions()) {
            mScanCallback = BtleScanCallback()
            mBluetoothLeScanner = mBluetoothAdapter!!.bluetoothLeScanner

            val settings = ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
                    .build()

            Handler().postDelayed({ stopScan()}, SCAN_PERIOD)

            mBluetoothLeScanner!!.startScan(null, settings, mScanCallback)

        } else {
            Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class BtleScanCallback : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            addScanResult(result)
        }

        override fun onBatchScanResults(results: List<ScanResult>) {
            for (result in results) {
                addScanResult(result)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Log.d("DBG", "BLE Scan Failed with code $errorCode")
        }

        private fun addScanResult(result: ScanResult) {
            val device = result.device

            devices.add(Device(if (device.name == null) "no name" else device.name, device.address, result.rssi, true))
        }
    }

    private fun stopScan() {
        mBluetoothLeScanner!!.stopScan(mScanCallback)

        if(devices.size == 0) {
            Toast.makeText(this, getString(R.string.noDevices), Toast.LENGTH_SHORT).show()
        } else {
            adapter = DeviceListAdapter(this, devices)
            listView.adapter = adapter

        }
    }

    private fun hasPermissions(): Boolean {
        if (mBluetoothAdapter == null || !mBluetoothAdapter!!.isEnabled) {
            Log.d("DBG", "No Bluetooth LE capability")
            return false
        } else if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d("DBG", "No fine location access")
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return true // assuming that the user grants permission
        }
        return true
    }
}
