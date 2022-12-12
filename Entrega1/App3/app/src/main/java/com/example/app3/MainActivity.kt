package com.example.app3

import android.annotation.SuppressLint
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs


class MainActivity : AppCompatActivity(), SensorEventListener {
    lateinit var mSensorManager:SensorManager
    lateinit var mAcceleromater:Sensor
    lateinit var x:TextView
    lateinit var y:TextView
    lateinit var z:TextView
    var previousX=0.0f
    var previousY=0.0f
    var previousZ=0.0f
    var ignoreInitial=true
    override fun onCreate(savedInstanceState: Bundle?) {
        mSensorManager=getSystemService(SENSOR_SERVICE) as SensorManager
        mAcceleromater=mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        x= findViewById<TextView>(R.id.x)
        y=findViewById<TextView>(R.id.y)
        z=findViewById<TextView>(R.id.z)

    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mAcceleromater, SensorManager.SENSOR_DELAY_NORMAL);
    }
    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val sensorX = event.values[0]
            val sensorY = event.values[1]
            val sensorZ = event.values[2]
            x.text= "X:$sensorX"
            y.text= "Y:$sensorY"
            z.text= "Z:$sensorZ"
            if(!ignoreInitial&&(abs(previousX-sensorX)>10||abs(previousY-sensorY)>10 || abs(previousY-sensorY)>10)){
                var it = Intent(this,Trigger::class.java)
                startActivity(it)
            }else{
                ignoreInitial=false
            }
            previousX =sensorX
            previousY =sensorY
            previousZ =sensorZ
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}