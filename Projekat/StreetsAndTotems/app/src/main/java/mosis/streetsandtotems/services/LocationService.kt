package mosis.streetsandtotems.services

import android.app.Application
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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
        Log.d("tag", "brmm brmm")

        super.onCreate()
        isServiceStarted = true
        startForeground(
            1,
            notificationProvider.returnDisableBackgroundServiceNotification(false)
        )

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startListeningUserLocation()
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
        Log.d("tag", "ugasennnn servis")
        serviceJob.cancel()
        super.onDestroy()

    }


    fun startListeningUserLocation() {

        val locationRequest = LocationRequest.create()
            .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            .setWaitForAccurateLocation(true)
            .setInterval(4000)
        // .setSmallestDisplacement(2f)

        Log.d("tag", "skloniti komenatr za smallest displacement")


        val locationSettingsRequest =
            LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)
                .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {

                isLocationEnabled.value = true

                Toast.makeText(
                    context,
                    result.lastLocation?.accuracy.toString(),
                    Toast.LENGTH_SHORT
                ).show()

                serviceScope.launch {
                    mLocation.emit(result.lastLocation)
                    Log.d(
                        "tag",
                        "NEW LOCATION: ${result.lastLocation?.latitude}, ${result.lastLocation?.longitude}, ${result.lastLocation?.accuracy}"
                    )
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


        val task =
            LocationServices.getSettingsClient(this).checkLocationSettings(locationSettingsRequest)

        task.addOnCompleteListener {
            if (it.isSuccessful) {
                isLocationEnabled.value = true
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            } else {
                isLocationEnabled.value = false
                this.stopSelf()
            }
        }
    }

    companion object {
        val mLocation: MutableStateFlow<Location?> = MutableStateFlow(null)
        var isServiceStarted = false
        var isLocationEnabled = mutableStateOf(true)

    }
}