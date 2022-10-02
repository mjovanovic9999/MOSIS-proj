package mosis.streetsandtotems.core.domain.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.location.LocationManager
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


class LocationBroadcastReceiver(private val locationStateMutableFlow: MutableSharedFlow<Boolean>) :
    BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.location.PROVIDERS_CHANGED") {
            val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
            CoroutineScope(Dispatchers.Default).launch {
                locationStateMutableFlow.emit(
                    !(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || !locationManager.isProviderEnabled(
                        LocationManager.NETWORK_PROVIDER
                    ))
                )
            }
        }
    }
}
