package mosis.streetsandtotems.core.presentation.utils.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import mosis.streetsandtotems.services.LocationService

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        //NotificationService(context).cancelDisableBackgroundServiceNotification()
        //service.showNotification(++Counter.value)
        BackgroundServicesEnabled.isEnabled = false//da l se ovde poziva????
        context.stopService(Intent(context, LocationService::class.java))

        Log.d("tag","disabled")
    }
}