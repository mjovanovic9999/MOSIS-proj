package mosis.streetsandtotems.services

import android.Manifest
import android.app.Application
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.*
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mosis.streetsandtotems.core.domain.util.LocationBroadcastReceiver
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationBroadcastReceiver
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
import javax.inject.Inject


@AndroidEntryPoint
class LocationService : Service() {
    @Inject
    lateinit var context: Application

    @Inject
    lateinit var notificationProvider: NotificationProvider

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    lateinit var locationCallback: LocationCallback

    @Inject
    lateinit var networkManager: NetworkManager


    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    override fun onCreate() {
        super.onCreate()
        isServiceStarted = true
        startForeground(
            1,
            notificationProvider.returnDisableBackgroundServiceNotification(false)
        )

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startListeningUserLocation(this)
        return START_STICKY_COMPATIBILITY
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        notificationProvider.cancelDisableBackgroundServiceNotification()
        isServiceStarted = false
        networkManager.unregister()

        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        Log.d("tag", "ugasennnn")
        serviceJob.cancel()
        super.onDestroy()

    }


    private fun startListeningUserLocation(context: Context) {

        val locationRequest = LocationRequest.create()
            .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            .setWaitForAccurateLocation(true)
            .setInterval(4000)
        //.setSmallestDisplacement(2f)

        Log.d("tag", "skloniti komenatr za smallest displacement")


        val locationSettingsRequest =
            LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true) //sta je bre ovoo???????? //https://stackoverflow.com/questions/29861580/locationservices-settingsapi-reset-settings-change-unavailable-flag
                .build()
//mozda ovako il sa location manager


        //da se proveri je l netvork enabled

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                Log.d(
                    "tag",
                    "NEW LOCATION: ${result.lastLocation?.latitude}, ${result.lastLocation?.longitude}, ${result.lastLocation?.accuracy}"
                )
                mLocation = result.lastLocation
                mLocation?.let {
                    serviceScope.launch {
                        //            Log.d("tag", it.accuracy.toString())
//                            val apiClient = ApiClient.getInstance(this@LocationService)
//                                .create(ApiClient::class.java)
//                            val response = apiClient.updateLocation()
//                            response.enqueue(object : Callback<LocationResponse> {
//                                override fun onResponse(
//                                    call: Call<LocationResponse>,
//                                    response: Response<LocationResponse>
//                                ) {
//                                    Log.d(TAG, "onLocationChanged: Latitude ${it.latitude} , Longitude ${it.longitude}")
//                                    Log.d(TAG, "run: Running = Location Update Successful")
//                                }
//
//                                override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
//                                    Log.d(TAG, "run: Running = Location Update Failed")
//
//                                }
//                            })

                    }

                }
            }
        }


        val task =
            LocationServices.getSettingsClient(this).checkLocationSettings(locationSettingsRequest)

        task.addOnCompleteListener {
            if (it.isSuccessful) {

                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && (Build.VERSION.SDK_INT < Build.VERSION_CODES.R || checkSelfPermission(
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED)
                ) {
                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.getMainLooper()
                    )

                    Log.d("tag", "isLocationEnabled = true")
                } else {


                    Log.d("tag", "nece self permission")
                    this.stopSelf()

                }
            } else {
                isLocationEnabled.value = false
                Log.d("tag", "isLocationEnabled = false")
                this.stopSelf()
            }
        }


    }

    companion object {
        var mLocation: Location? = null
        var isServiceStarted = false
        var isLocationEnabled = mutableStateOf(true)
    }
}