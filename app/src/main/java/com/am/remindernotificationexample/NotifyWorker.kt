package com.am.remindernotificationexample

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.annotation.NonNull
import androidx.core.app.NotificationCompat
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotifyWorker(var context: Context, var params: WorkerParameters) : Worker(context, params) {


    override fun doWork(): Result {
        // Method to trigger an instant notification
        triggerNotification()

        return Result.success()
        // (Returning RETRY tells WorkManager to try this task again
        // later; FAILURE says not to try again.)
    }

    private fun triggerNotification() {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = getNotification("fuck Alarm Manger")
        notificationManager.notify(555, notification)

    }


    private fun getNotification(content: String): Notification {
        val builder = NotificationCompat.Builder(context)
        builder.setContentTitle("Scheduled Notification")
        builder.setContentText(content)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        return builder.build()
    }



}