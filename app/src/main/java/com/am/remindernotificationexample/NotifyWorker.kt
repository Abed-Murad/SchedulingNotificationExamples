package com.am.remindernotificationexample

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.annotation.NonNull
import androidx.core.app.NotificationCompat
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class NotifyWorker(var context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        // Method to trigger an instant notification
        val title = inputData.getString("notification_title")
        val content = inputData.getString("notification_content")
        triggerNotification(title!!, content!!)


        return Result.success()
        // (Returning RETRY tells WorkManager to try this task again
        // later; FAILURE says not to try again.)
    }

    private fun triggerNotification(title: String, content: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = getNotification(title, content)
        notificationManager.notify(555, notification)
    }

    private fun getNotification(title: String, content: String): Notification {
        val builder = NotificationCompat.Builder(context)
        builder.setContentTitle(title)
        builder.setContentText(content)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        return builder.build()
    }
}