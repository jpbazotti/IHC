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
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.alarmenoturno.Alarm
import java.util.*


lateinit var alarmSetButton: Button
lateinit var alarmCancelButton: Button
lateinit var alarmACKButton: Button
lateinit var addPhoneButton: Button
lateinit var alarmTextView: TextView
lateinit var phoneEditText: EditText
lateinit var timePicker: TimePicker
var list = arrayOf("1","2","3","4","5")
var count =0
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
        phoneEditText = findViewById(R.id.editTextPhone)
        addPhoneButton = findViewById(R.id.addPhone)
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

            al.setAlarm(this,0,list)

        }
        alarmCancelButton.setOnClickListener{
            al.cancelAlarm(this,0)
            alarmTextView.text = getString(R.string.notSet)

        }
        alarmACKButton.setOnClickListener{
            al.cancelAlarm(this,1)

        }
        addPhoneButton.setOnClickListener{
            list[count] = phoneEditText.text.toString()
            count++
            Log.d("Alarm Bell", list.joinToString("\n"))

        }

    }

}