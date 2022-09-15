package mosis.streetsandtotems.core.presentation.utils.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import mosis.streetsandtotems.services.LocationService

class NotificationBroadcastReceiver :
    BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        NotificationProvider(context).cancelDisableBackgroundServiceNotification()

        context.stopService(Intent(context, LocationService::class.java))
    }
}