package com.oxymium.realestatemanager.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.oxymium.realestatemanager.R

class Notifications(val context: Context, private val channel: String): NotificationCompat() {

    fun createNotification(estateId: Long) {
        val builder = Builder(context, channel)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Estate successfully created")
            .setContentText("Estate ID.$estateId added")
            .setStyle(
                BigTextStyle()
                    .bigText("Estate ID.$estateId added")
            )
            .setPriority(PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(10, builder.build())
        }
    }

}
