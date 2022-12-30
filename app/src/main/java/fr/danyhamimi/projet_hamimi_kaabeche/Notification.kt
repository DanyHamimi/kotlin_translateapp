package fr.danyhamimi.projet_hamimi_kaabeche

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Notification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
// Build the notification
        val notificationBuilder = NotificationCompat.Builder(context, "CHANNEL_IDDANY")
            .setSmallIcon(androidx.activity.ktx.R.drawable.notification_bg_normal_pressed)
            .setContentTitle("Tradogtion")
            .setContentText("Ca fait longtemps que vous n'êtres pas venus !!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

// Display the notification
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {
        const val NOTIFICATION_ID = 0
    }
}