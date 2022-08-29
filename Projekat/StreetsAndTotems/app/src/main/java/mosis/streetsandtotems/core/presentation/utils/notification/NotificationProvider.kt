package mosis.streetsandtotems.core.presentation.utils.notification

import android.app.*
import android.app.Notification.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import androidx.core.app.NotificationCompat
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ButtonConstants.TURN_OFF_BACKGROUND_SERVICE_BUTTON
import mosis.streetsandtotems.core.NotificationConstants
import mosis.streetsandtotems.core.NotificationConstants.CHANNEL_ID
import mosis.streetsandtotems.core.NotificationConstants.CHANNEL_ID2
import mosis.streetsandtotems.core.NotificationConstants.CHANNEL_NAME2
import mosis.streetsandtotems.core.NotificationConstants.DISABLE_BACKGROUND_SERVICE_ID
import mosis.streetsandtotems.core.NotificationConstants.DISABLE_BACKGROUND_SERVICE_TEXT
import mosis.streetsandtotems.core.NotificationConstants.DISABLE_BACKGROUND_SERVICE_TITLE
import mosis.streetsandtotems.core.NotificationConstants.NOTIFY_NEARBY_PASS_ID
import mosis.streetsandtotems.core.NotificationConstants.NOTIFY_NEARBY_PASS_TEXT
import mosis.streetsandtotems.core.NotificationConstants.NOTIFY_NEARBY_PASS_TITLE
import mosis.streetsandtotems.core.presentation.MainActivity
import javax.inject.Inject


class NotificationProvider @Inject constructor(private val context: Context) {

    private var notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    init {
        val channel = NotificationChannel(
            CHANNEL_ID,
            NotificationConstants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_MIN
        )
        channel.description = NotificationConstants.CHANNEL_NAME
        channel.enableVibration(false)
        channel.setSound(null, null)

        notificationManager.createNotificationChannel(channel)

//        (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            (context.getSystemService(Service.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
//        } else {
//            context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
//        }).vibrate(VibrationEffect.createWaveform(longArrayOf(0, 500, 300, 500, 750), -1))


        val channel2 = NotificationChannel(
            CHANNEL_ID2,
            CHANNEL_NAME2,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel2.description = CHANNEL_NAME2

        channel2.enableLights(true)
        channel2.enableVibration(true)
        channel2.vibrationPattern = longArrayOf(0, 500, 300, 500, 750)
        channel2.lightColor = Color.argb(255, 100, 200, 200)


//        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
//            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//            .build()
//
//        channel2.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, audioAttributes)

        notificationManager.createNotificationChannel(channel2)


    }

    fun notifyNearbyPass() {
        val intent = Intent(context, MainActivity::class.java)

        val startApp = PendingIntent.getActivity(context, 0, intent, 0)

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setOngoing(false)
            .setSmallIcon(R.drawable.logo_only_tiki)
            .setContentTitle(NOTIFY_NEARBY_PASS_TITLE)
            .setContentText(NOTIFY_NEARBY_PASS_TEXT)
            .setContentIntent(startApp)
            .setAutoCancel(true)
//            .setVibrate(longArrayOf(0, 500, 300/*, 500, 750*/))
//            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//            .setDefaults(NotificationCompat.DEFAULT_SOUND)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

//        (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            (context.getSystemService(Service.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
//        } else {
//            context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
//        }).vibrate(VibrationEffect.createWaveform(longArrayOf(0, 500, 300/*, 500, 750*/), -1))


//treba za prvu notif
//        val action = Notification.Action.Builder(
//            Icon.createWithResource(context, R.drawable.logo_only_tiki),
//            TURN_OFF_BACKGROUND_SERVICE_BUTTON,
//            startApp
//        )
//
//        notificationBuilder.addAction(action.build())

//        notificationBuilder.setFlag(FLAG_INSISTENT, true)

        notificationManager.notify(
            NOTIFY_NEARBY_PASS_ID,
            notificationBuilder.build(),
        )
    }

    fun returnDisableBackgroundServiceNotification(showDisableButton: Boolean): Notification {


        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setOngoing(true)
            .setSmallIcon(R.drawable.logo_only_tiki)
            .setContentTitle(DISABLE_BACKGROUND_SERVICE_TITLE)
            .setContentText(DISABLE_BACKGROUND_SERVICE_TEXT)
            .setForegroundServiceBehavior(FOREGROUND_SERVICE_IMMEDIATE)


        if (showDisableButton) {
            val disableBackgroundServiceIntent = PendingIntent.getBroadcast(
                context,
                1,
                Intent(context, NotificationBroadcastReceiver::class.java),
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
            )
            notificationBuilder.addAction(
                R.drawable.logo_only_tiki,
                TURN_OFF_BACKGROUND_SERVICE_BUTTON,
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

    fun cancelDisableBackgroundServiceNotification() {
        notificationManager.cancel(DISABLE_BACKGROUND_SERVICE_ID)
    }
}