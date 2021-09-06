package com.oxymium.realestatemanager.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.oxymium.realestatemanager.R

class Notifications(val context: Context, private val channel: String): NotificationCompat() {

    fun createNotification(estateId: Long) {
        val builder = NotificationCompat.Builder(context, channel)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Estate successfully created")
            .setContentText("Estate ID.$estateId added")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Estate ID.$estateId added")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(10, builder.build())
        }
    }

}
