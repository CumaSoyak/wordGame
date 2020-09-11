package app.word.game

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    private val notificationIcon: Int
        get() {
            val useWhiteIcon =
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            return if (useWhiteIcon) R.drawable.ic_notification_icon else R.drawable.ic_notification_icon
        }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // console'dan bildirim gönderiliyorsa
        remoteMessage.notification?.let {
            sendNotification(it.body, it.title)
            return
        }


    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    private fun sendNotification(messageBody: String?, title: String?) {
        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        if (messageBody?.contains("rating")!! || messageBody.contains("puan")) {
            intent.putExtra("puan", "puan")
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0 /* Request code */,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val title = title ?: R.string.app_name_notification

        val channelId = getString(R.string.app_name)
        val pattern = longArrayOf(400, 400)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(notificationIcon)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            .setContentTitle(title.toString())
            .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
            .setLights(Color.BLUE, 500, 500)
            .setColor(resources.getColor(R.color.green))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setVibrate(pattern)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                resources.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }


}
