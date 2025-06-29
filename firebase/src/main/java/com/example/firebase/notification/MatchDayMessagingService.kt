package com.example.firebase.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import com.example.firebase.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MatchDayMessagingService() : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToken(token = token)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)


        // FIRST TRY
//        val data = message.data
//        if (data.isNotEmpty()) {
//            val type = data["type"]
//            val matchId = data["matchId"]
//            val navigateTo = data["navigateTo"]
//            val title = message.notification?.title ?: "Notification"
//            val body = message.notification?.body ?: "You have a new notification"
//
//            Log.d("FCM", "Data received: $data")
//
//            showLocalNotification(title = title, body = body, type = type, matchId = matchId, navigateTo = navigateTo)
//        }

        // SECOND TRY
        /*message.notification?.let {
            val title = it.title ?: "Notification"
            val body = it.body ?: "You have a new notification"
            createNotification(title = title, body = body)
        }*/
    }


//    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
//    private fun showLocalNotification(title: String, body: String, type: String?, matchId: String?, navigateTo: String?) {
//        val channelId = "matchday_channel"
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                channelId,
//                "MatchDay Notifications",
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//
//            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            manager.createNotificationChannel(channel)
//        }
//
//        val builder = NotificationCompat.Builder(this, channelId)
//            .setContentTitle(title)
//            .setContentText(body)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setAutoCancel(true)
//
//        val notificationManager = NotificationManagerCompat.from(this)
//        notificationManager.notify(1001, builder.build())
//    }

/*
    private fun createNotification(title: String, body: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val channelId = getString(R.string.default_channel)
        val pendingIntent = PendingIntent.getActivity(this, 999, intent, PendingIntent.FLAG_IMMUTABLE)
        val notificationBuilder = NotificationCompat.Builder(this, "")
            .setSmallIcon(R.drawable.ic_matchday_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setAutocancel(true)
            .setChannelId(channelId)
            .setSound(sound)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Promociones",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
*/

    private fun sendRegistrationToken(token: String) {
        Log.i("MatchDayToken", token)
    }
}
