package mosis.streetsandtotems.core.domain.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.location.LocationManager
import mosis.streetsandtotems.services.LocationService


class LocationBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.location.PROVIDERS_CHANGED") {
            val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
            LocationService.isLocationEnabled.value =
                !(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || !locationManager.isProviderEnabled(
                    LocationManager.NETWORK_PROVIDER
                ))
        }
    }
}