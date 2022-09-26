package mosis.streetsandtotems.core.presentation.utils.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
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
import mosis.streetsandtotems.core.NotificationConstants.VIBRATION_PATTERN_TIMINGS
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

        val channel2 = NotificationChannel(
            CHANNEL_ID2,
            CHANNEL_NAME2,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel2.description = CHANNEL_NAME2

        notificationManager.createNotificationChannel(channel2)


    }

    fun notifyNearbyPass() {
        val intent = Intent(context, MainActivity::class.java)

        val startApp = PendingIntent.getActivity(
            context,
            0,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notificationBuilder = Notification.Builder(context, CHANNEL_ID)
            .setOngoing(false)
            .setSmallIcon(R.drawable.logo_only_tiki)
            .setContentTitle(NOTIFY_NEARBY_PASS_TITLE)
            .setContentText(NOTIFY_NEARBY_PASS_TEXT)
            .setContentIntent(startApp)
            .setAutoCancel(true)

        notificationManager.notify(
            NOTIFY_NEARBY_PASS_ID,
            notificationBuilder.build(),
        )
        makeVibrate()
    }


    fun cancelNotifyNearbyPass() {
        notificationManager.cancel(NOTIFY_NEARBY_PASS_ID)
    }

    private fun makeVibrate() {
        (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (context.getSystemService(Service.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
        } else {
            context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        }).vibrate(VibrationEffect.createWaveform(VIBRATION_PATTERN_TIMINGS, -1))
    }

    fun returnDisableBackgroundServiceNotification(showDisableButton: Boolean): Notification {

        val notificationBuilder = Notification.Builder(context, CHANNEL_ID)
            .setOngoing(true)
            .setSmallIcon(R.drawable.logo_only_tiki)
            .setContentTitle(DISABLE_BACKGROUND_SERVICE_TITLE)
            .setContentText(DISABLE_BACKGROUND_SERVICE_TEXT)

        if (showDisableButton) {
            val disableBackgroundServiceIntent = PendingIntent.getBroadcast(
                context,
                1,
                Intent(context, NotificationBroadcastReceiver::class.java),
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
            )

            val action = Notification.Action.Builder(
                Icon.createWithResource(context, R.drawable.logo_only_tiki),
                TURN_OFF_BACKGROUND_SERVICE_BUTTON,
                disableBackgroundServiceIntent
            )
            notificationBuilder.addAction(action.build())
        }

        return notificationBuilder.build()
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