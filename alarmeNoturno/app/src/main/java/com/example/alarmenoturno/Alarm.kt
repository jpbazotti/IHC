package com.example.alarmenoturno

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.media.RingtoneManager.getDefaultUri
import android.net.Uri
import android.os.*
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.test.core.app.ApplicationProvider.getApplicationContext


class Alarm : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.S)
    companion object {
        lateinit var ringtoneSound: Ringtone
    }
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onReceive(context: Context, intent: Intent) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        val vibrator = vibratorManager.defaultVibrator
        context.applicationContext

        vibrator.vibrate(VibrationEffect.createOneShot(10000,VibrationEffect.DEFAULT_AMPLITUDE))
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Alarm:here")
        wl.acquire()
        val phoneList = intent.getStringArrayExtra("phones")
        val sender = intent.getIntExtra("sender",0)
        Log.d("Alarm Bell", "Alarm received" + sender)
       if(sender==0) {
           if (phoneList != null) {
               setAlarm(context,1,phoneList)
           }
           ringtoneSound = RingtoneManager.getRingtone(context, getDefaultUri(RingtoneManager.TYPE_ALARM))
           ringtoneSound.play()
           Toast.makeText(context, "Alarm", Toast.LENGTH_SHORT).show()
           val ringLength = 8
           val countLength = 1000 * ringLength.toLong()
           object : CountDownTimer(countLength, 1000) {

               override fun onTick(millisUntilFinished: Long) {}

               override fun onFinish() {
                   ringtoneSound.stop()
               }
           }.start()
        }
        else{
           Toast.makeText(context, "Emergency!", Toast.LENGTH_SHORT).show()

           val smsManager: SmsManager = SmsManager.getDefault()
           if (phoneList != null) {
               for (phone:String in phoneList){
                   smsManager.sendTextMessage(phone, null, "alerta", null, null)
               }
           }



       }

        wl.release()



    }


    fun setAlarm(context: Context,sender:Int,list:Array<String>) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(context, Alarm::class.java)
        i.putExtra("sender",sender)
        if(list.isNotEmpty()) {
            i.putExtra("phones", list)
        }
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