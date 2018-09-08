package com.example.user.bluetoothle

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_graph.*


class GraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        val intent = intent
        val dataBPM: IntArray = intent.getIntArrayExtra("data")

        val series = LineGraphSeries(data(dataBPM))

        graph.addSeries(series)

        graph.viewport.isYAxisBoundsManual = true
        graph.viewport.setMinimalViewport(0.toDouble(), dataBPM.size.toDouble(), 0.toDouble(), 400.toDouble())

        graph.gridLabelRenderer.horizontalAxisTitle = getString(R.string.time)
        graph.gridLabelRenderer.verticalAxisTitle = getString(R.string.bpm)
        graph.title = getString(R.string.heartBeat)

        graph.viewport.isScalable = true
        graph.viewport.isScrollable = true
    }

    fun data(dataBPM : IntArray): Array<DataPoint?> {
        val values = arrayOfNulls<DataPoint>(dataBPM.size)
        for ((index, value) in dataBPM.withIndex()) {
            values[index] = DataPoint(index.toDouble(), value.toDouble())
        }
        return values
    }
}
