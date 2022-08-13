package mosis.streetsandtotems.services

import android.app.Application
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.*
import android.util.Log
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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


    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    override fun onCreate() {
        super.onCreate()
        isServiceStarted = true
        startForeground(
            1,//constants.......
            notificationProvider.returnDisableBackgroundServiceNotification(false)
        )

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {


        startListeningUserLocation(
            this,
            object : MyLocationListener {
                override fun onLocationChanged(location: Location?) {
                    mLocation = location
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
            })



        return START_STICKY
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        //notificationService.cancelDisableBackgroundServiceNotification()
        Log.d("tag", "ugasennnn")
        isServiceStarted = false
        serviceJob.cancel()

    }

    private fun startListeningUserLocation(context: Context, myListener: MyLocationListener) {

        val locationRequest = LocationRequest.create()
            .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            .setWaitForAccurateLocation(true)
            .setInterval(4000)
        //.setSmallestDisplacement(2f)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                myListener.onLocationChanged(result.lastLocation)
                Log.d(
                    "tag",
                    "NEW LOCATION: ${result.lastLocation?.latitude}, ${result.lastLocation?.longitude}, ${result.lastLocation?.accuracy}"
                )
            }
        }


        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

    }

    companion object {
        var mLocation: Location? = null
        var isServiceStarted = false
    }
}

interface MyLocationListener {
    fun onLocationChanged(location: Location?)
}
