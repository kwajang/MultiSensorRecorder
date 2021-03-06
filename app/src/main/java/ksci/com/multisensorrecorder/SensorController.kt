package ksci.com.multisensorrecorder

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.github.mikephil.charting.data.Entry

class SensorController(_sensorManager: SensorManager, _type: String) : SensorEventListener {

    private val sensorManager = _sensorManager
    private val type = _type

    override fun onSensorChanged(event: SensorEvent) {
        when (type) {
            "record" -> {
                when (event.sensor.type) {
                    Sensor.TYPE_ACCELEROMETER -> {
                        sensorDataRecord(MainPageActivity.sensorData[event.sensor.type]!!, event)
                    }
                    Sensor.TYPE_GRAVITY -> {
                        sensorDataRecord(MainPageActivity.sensorData[event.sensor.type]!!, event)
                    }
                    Sensor.TYPE_GYROSCOPE -> {
                        sensorDataRecord(MainPageActivity.sensorData[event.sensor.type]!!, event)
                    }
                    Sensor.TYPE_LIGHT -> {
                        sensorDataRecord(MainPageActivity.sensorData[event.sensor.type]!!, event)
                    }
                    Sensor.TYPE_LINEAR_ACCELERATION -> {
                        sensorDataRecord(MainPageActivity.sensorData[event.sensor.type]!!, event)
                    }
                    Sensor.TYPE_MAGNETIC_FIELD -> {
                        sensorDataRecord(MainPageActivity.sensorData[event.sensor.type]!!, event)
                    }
                    Sensor.TYPE_PROXIMITY -> {
                        sensorDataRecord(MainPageActivity.sensorData[event.sensor.type]!!, event)
                    }
                    Sensor.TYPE_ROTATION_VECTOR -> {
                        sensorDataRecord(MainPageActivity.sensorData[event.sensor.type]!!, event)
                    }
                }
            }
            "preview" -> {
                sensorPreview(event)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    fun registerSensor(sensor: Int,
                       register: Boolean,
                       mDelay: Int = SensorManager.SENSOR_DELAY_GAME) {

        if (register) {
            sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(sensor),
                mDelay
            )
        } else {
            sensorManager.unregisterListener(
                this,
                sensorManager.getDefaultSensor(sensor)
            )
        }
    }

    private fun sensorDataRecord(sensorData: FloatArray, event: SensorEvent) {
        for (i in 0 until event.values.size) {
            sensorData[i] = event.values[i]
        }
    }

    private fun sensorPreview(event: SensorEvent) {
        val mChart = PreviewPageActivity.mChart
        val data = mChart.data

        val sensorType = PreviewPageActivity.sensorType

        val s1 = PreviewPageActivity.s1
        val s2 = PreviewPageActivity.s2
        val s3 = PreviewPageActivity.s3
        val s4 = PreviewPageActivity.s4

        when (sensorType) {
            Sensor.TYPE_LIGHT,
            Sensor.TYPE_PROXIMITY -> {
                s1.addEntry(Entry(s1.entryCount.toFloat(), event.values[0]))

                data.getDataSetByIndex(0).addEntry(Entry(s1.entryCount.toFloat(), event.values[0]))
            }
            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_GRAVITY,
            Sensor.TYPE_GYROSCOPE,
            Sensor.TYPE_LINEAR_ACCELERATION,
            Sensor.TYPE_MAGNETIC_FIELD -> {
                s1.addEntry(Entry(s1.entryCount.toFloat(), event.values[0]))
                s2.addEntry(Entry(s2.entryCount.toFloat(), event.values[1]))
                s3.addEntry(Entry(s3.entryCount.toFloat(), event.values[2]))

                data.getDataSetByIndex(0).addEntry(Entry(s1.entryCount.toFloat(), event.values[0]))
                data.getDataSetByIndex(1).addEntry(Entry(s2.entryCount.toFloat(), event.values[1]))
                data.getDataSetByIndex(2).addEntry(Entry(s3.entryCount.toFloat(), event.values[2]))
            }
            Sensor.TYPE_ROTATION_VECTOR -> {
                s1.addEntry(Entry(s1.entryCount.toFloat(), event.values[0]))
                s2.addEntry(Entry(s2.entryCount.toFloat(), event.values[1]))
                s3.addEntry(Entry(s3.entryCount.toFloat(), event.values[2]))
                s4.addEntry(Entry(s4.entryCount.toFloat(), event.values[3]))

                data.getDataSetByIndex(0).addEntry(Entry(s1.entryCount.toFloat(), event.values[0]))
                data.getDataSetByIndex(1).addEntry(Entry(s2.entryCount.toFloat(), event.values[1]))
                data.getDataSetByIndex(2).addEntry(Entry(s3.entryCount.toFloat(), event.values[2]))
                data.getDataSetByIndex(3).addEntry(Entry(s4.entryCount.toFloat(), event.values[3]))
            }
        }

        mChart.data.notifyDataChanged()
        mChart.notifyDataSetChanged()
        mChart.setVisibleXRangeMaximum(150f)
        mChart.moveViewToX(data.entryCount.toFloat())
    }
}