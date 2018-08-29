package com.example.user.bluetoothle

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class DeviceListAdapter(context: Context, private val devices: MutableList<Device>): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.item_device, parent, false)
        val thisDevice = devices[position]

        var tv = rowView.findViewById(R.id.textViewName) as TextView
        tv.text = thisDevice.name

        tv = rowView.findViewById(R.id.textViewMAC) as TextView
        tv.text = String.format(thisDevice.mac)

        tv = rowView.findViewById(R.id.textViewRSSI) as TextView
        tv.text = String.format(thisDevice.rssi.toString())

        return rowView
    }

    override fun getCount(): Int {
        return devices.size
    }

    override fun getItem(position: Int): Any {
        return devices[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun isEnabled(position: Int): Boolean {
        return devices[position].isEnabled
    }
}