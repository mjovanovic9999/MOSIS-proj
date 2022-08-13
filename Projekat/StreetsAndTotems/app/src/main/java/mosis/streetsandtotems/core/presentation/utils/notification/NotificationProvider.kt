package mosis.streetsandtotems.core.presentation.utils.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.NotificationsConstants
import mosis.streetsandtotems.core.NotificationsConstants.CHANNEL_ID
import mosis.streetsandtotems.core.NotificationsConstants.DISABLE_BACKGROUND_SERVICE_BUTTON
import mosis.streetsandtotems.core.NotificationsConstants.DISABLE_BACKGROUND_SERVICE_ID
import mosis.streetsandtotems.core.NotificationsConstants.DISABLE_BACKGROUND_SERVICE_TEXT
import mosis.streetsandtotems.core.NotificationsConstants.DISABLE_BACKGROUND_SERVICE_TITLE
import mosis.streetsandtotems.core.presentation.MainActivity

class NotificationProvider(private val context: Context) {

    private var notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
//        (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            (context.getSystemService(Service.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
//        } else {
//            context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
//        }).vibrate(VibrationEffect.createWaveform(longArrayOf(0, 500, 300, 500, 750), -1))


        val channel = NotificationChannel(
            CHANNEL_ID,
            NotificationsConstants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = NotificationsConstants.CHANNEL_NAME
//        channel.enableVibration(true)

        notificationManager.createNotificationChannel(channel)
    }


    fun returnDisableBackgroundServiceNotification(showDisableButton: Boolean): Notification {


        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setOngoing(true)
            .setSmallIcon(R.drawable.logo_only_tiki)
            .setContentTitle(DISABLE_BACKGROUND_SERVICE_TITLE)
            .setContentText(DISABLE_BACKGROUND_SERVICE_TEXT)

        if (showDisableButton) {
            val disableBackgroundServiceIntent = PendingIntent.getBroadcast(
                context,
                1,
                Intent(context, NotificationReceiver::class.java),
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
            )
            notificationBuilder.addAction(
                R.drawable.logo_only_tiki,
                DISABLE_BACKGROUND_SERVICE_BUTTON,
                disableBackgroundServiceIntent
            )
        }
        return notificationBuilder.build()

//        notificationManager.notify(DISABLE_BACKGROUND_SERVICE_ID, notification)
    }

    fun notifyDisable(showDisableButton: Boolean) {
        notificationManager.notify(
            DISABLE_BACKGROUND_SERVICE_ID,
            returnDisableBackgroundServiceNotification(showDisableButton)
        )
    }

//    fun cancelDisableBackgroundServiceNotification() {
//        notificationManager.cancel(DISABLE_BACKGROUND_SERVICE_ID)
//    }
}