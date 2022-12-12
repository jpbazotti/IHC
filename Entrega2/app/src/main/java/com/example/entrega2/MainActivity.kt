package com.example.entrega2

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest


class MainActivity : AppCompatActivity(), SensorEventListener,LocationListener {
    private lateinit var lumTextView: TextView
    lateinit var pressTextView: TextView
    lateinit var gpsTextView: TextView
    lateinit var sensorManager: SensorManager
    private lateinit var light: Sensor
    private lateinit var pressure: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lumTextView=findViewById(R.id.lum)
        pressTextView=findViewById(R.id.press)
        gpsTextView=findViewById(R.id.gps)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);

        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 123);
        gpsTextView.text = "longitude:" +getLocation().longitude +" latitude:"+getLocation().latitude

    }
        private fun getLocation():Location{
            var l =Location("dummy")
            l.latitude=0.0;
            l.longitude=0.0

            if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "GPS permission denied", Toast.LENGTH_SHORT).show()
                return l
            }


            val lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGPSEnabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if(isGPSEnabled){
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10.0f,this)
                l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER) as Location
                return l
            }else{
                Toast.makeText(this, "Please enable GPS", Toast.LENGTH_LONG).show();
            }
            return l
        }

    override fun onSensorChanged(event: SensorEvent) {
        if(event.sensor.type == Sensor.TYPE_LIGHT)
        {
            lumTextView.text="Light Intensity: " + event.values[0];
        }
        if(event.sensor.type == Sensor.TYPE_PRESSURE)
        {
            pressTextView.text="Pressure: " + event.values[0];
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
    override fun onLocationChanged(p0: Location) {
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "GPS permission denied", Toast.LENGTH_SHORT).show()
        }else{val lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGPSEnabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if(isGPSEnabled){
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10.0f,this)
                val l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER) as Location
                gpsTextView.text = "longitude:" +l.longitude +" latitude:"+l.latitude}
        }

    }

}