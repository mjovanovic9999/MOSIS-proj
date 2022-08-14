package mosis.streetsandtotems.core.presentation.utils.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import mosis.streetsandtotems.services.LocationService

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        NotificationProvider(context).cancelDisableBackgroundServiceNotification()
        //service.showNotification(++Counter.value)
        context.stopService(Intent(context, LocationService::class.java))

//desava se da je serivs ugasen a notif onako pozvana i tad disable ne radi
        //da l da se ostavi 12-ta linija
        Log.d("tag", "uvacennnnn")

    }
}