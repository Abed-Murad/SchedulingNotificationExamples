package com.am.remindernotificationexample

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.work.Data
import kotlinx.android.synthetic.main.activity_main.*
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notifyBtn1.setOnClickListener {
            scheduleNotification(getNotification("5 second delay"), 5000)
        }
        notifyBtn2.setOnClickListener {
            scheduleNotification(getNotification("10 second delay"), 10000)
        }
        notifyBtn3.setOnClickListener {
            scheduleNotification(getNotification("30 second delay"), 30000)
        }

        //we set a tag to be able to cancel all work of this type if needed
        val workTag = "notificationWork"

        //store DBEventID to pass it to the PendingIntent and open the appropriate event page on notification click
        val inputData = Data.Builder().putString("my_name", "Abed Murad").build()
        // we then retrieve it inside the NotifyWorker with:
        // final int DBEventID = getInputData().getInt(DBEventIDTag, ERROR_VALUE);

        val notificationWork = OneTimeWorkRequest.Builder(NotifyWorker::class.java)
            .setInitialDelay(calculateDelay(Date()), TimeUnit.SECONDS)
            .setInputData(inputData)
            .addTag(workTag)
            .build()

        WorkManager.getInstance(this).enqueue(notificationWork)
    }

    private fun calculateDelay(notificationDate: Date): Long {
        val currentTime = System.currentTimeMillis()
        val diff = notificationDate.time - currentTime
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        Log.d("ttt", "$days days, $hours hours, $minutes minutes, $seconds seconds")
        return diff
    }


    private fun scheduleNotification(notification: Notification, delay: Int) {

        val notificationIntent = Intent(this, NotificationPublisher::class.java)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )


        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent)
    }

    private fun getNotification(content: String): Notification {
        val builder = NotificationCompat.Builder(this)
        builder.setContentTitle("Scheduled Notification")
        builder.setContentText(content)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        return builder.build()
    }


}
