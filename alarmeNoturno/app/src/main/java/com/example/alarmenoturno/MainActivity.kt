package com.example.alarmenoturno

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.VibratorManager
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.alarmenoturno.Alarm
import java.util.*


lateinit var alarmSetButton: Button
lateinit var alarmCancelButton: Button
lateinit var alarmACKButton: Button
lateinit var alarmTextView: TextView
lateinit var timePicker: TimePicker
private lateinit var alarmIntent: PendingIntent
private var alarmMgr: AlarmManager? = null
var ringedOnce =false
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alarmSetButton=findViewById(R.id.alarmSet)
        alarmCancelButton=findViewById(R.id.alarmCancel)
        alarmACKButton=findViewById(R.id.ACK)
        alarmTextView=findViewById(R.id.alarmText)
        timePicker = findViewById(R.id.timePicker)
        val al = Alarm()
        alarmSetButton.setOnClickListener{
            var hour = timePicker.hour
            var minute = timePicker.minute
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                hour,
                minute,
                0
            )
            alarmTextView.text = getString(R.string.alarmMessage,hour,minute)

            al.setAlarm(this,0)

        }
        alarmCancelButton.setOnClickListener{
            al.cancelAlarm(this,0)
            alarmTextView.text = getString(R.string.notSet)

        }
        alarmACKButton.setOnClickListener{
            al.cancelAlarm(this,1)

        }

    }

}