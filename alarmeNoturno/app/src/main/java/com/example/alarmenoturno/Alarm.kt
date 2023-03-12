package com.example.alarmenoturno

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.*
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi


class Alarm : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onReceive(context: Context, intent: Intent) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        val vibrator = vibratorManager.defaultVibrator
        context.applicationContext

        vibrator.vibrate(VibrationEffect.createOneShot(10000,VibrationEffect.DEFAULT_AMPLITUDE))
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Alarm:here")
        wl.acquire()
        Log.d("Alarm Bell", "Alarm received")
       if(intent.getIntExtra("sender",0)==0) {
            setAlarm(context,1)
        }
        else{
           Toast.makeText(context, "Emergency!", Toast.LENGTH_SHORT).show()
           val smsManager: SmsManager = SmsManager.getDefault()
           smsManager.sendTextMessage("55 51 992123675", null, "alerta", null, null)
       }

        wl.release()



    }


    fun setAlarm(context: Context,sender:Int) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(context, Alarm::class.java)
        i.putExtra("sender",sender)
        val pi = PendingIntent.getBroadcast(context, sender, i, PendingIntent.FLAG_IMMUTABLE)
        am.setExact(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() +1000,
            pi
        )
        Log.d("Alarm Bell", "Alarm created")

    }

    fun cancelAlarm(context: Context,sender:Int) {
        val intent = Intent(context, Alarm::class.java)
        val sender = PendingIntent.getBroadcast(context, sender, intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(sender)
    }




}