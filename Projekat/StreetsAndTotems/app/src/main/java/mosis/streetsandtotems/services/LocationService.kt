package mosis.streetsandtotems.services

import android.app.Application
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.*
import com.google.firebase.firestore.GeoPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mosis.streetsandtotems.core.MapConstants
import mosis.streetsandtotems.core.MapConstants.MAP_PRECISION_METERS
import mosis.streetsandtotems.core.MapConstants.MAXIMUM_IGNORE_ACCURACY_METERS
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
import mosis.streetsandtotems.di.util.SharedFlowWrapper
import mosis.streetsandtotems.services.use_case.LocationServiceUseCases
import javax.inject.Inject


@AndroidEntryPoint
class LocationService : Service() {
    @Inject
    lateinit var locationStateFlow: MutableSharedFlow<Boolean>

    @Inject
    lateinit var locationServiceControlEventsFlow: SharedFlowWrapper<LocationServiceControlEvents>

    @Inject
    lateinit var locationServiceMapScreenEventsFlow: MutableSharedFlow<LocationServiceMapScreenEvents>

    @Inject
    lateinit var context: Application

    @Inject
    lateinit var notificationProvider: NotificationProvider

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Inject
    lateinit var networkManager: NetworkManager

    @Inject
    lateinit var locationServiceUseCases: LocationServiceUseCases


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {

            Toast.makeText(
                context, result.lastLocation?.accuracy.toString(), Toast.LENGTH_SHORT
            ).show()

            serviceScope.launch {
                result.lastLocation?.let {
                    if (it.accuracy <= MAXIMUM_IGNORE_ACCURACY_METERS) {
                        locationServiceMapScreenEventsFlow.emit(
                            LocationServiceMapScreenEvents.PlayerMapScreenLocationChanged(
                                GeoPoint(it.latitude, it.longitude)
                            )
                        )
                        locationServiceUseCases.updatePlayerLocation(it.latitude, it.longitude)
                        lastKnownLocation = GeoPoint(it.latitude, it.longitude)
                        locationServiceUseCases.updateNotificationQueries(
                            GeoPoint(
                                it.latitude, it.longitude
                            )
                        )
                        Log.d("tag", lastKnownLocation.toString())
                    }
                }
                Log.d(
                    "tag",
                    "NEW LOCATION: ${result.lastLocation?.latitude}, ${result.lastLocation?.longitude}, ${result.lastLocation?.accuracy}"
                )
            }
        }
    }

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)


    override fun onCreate() {
        Log.d("tag", "brmm brmm")
        isServiceStarted = true
        super.onCreate()
        startForeground(
            1, notificationProvider.returnDisableBackgroundServiceNotification(false)
        )

        serviceScope.launch {
            locationServiceControlEventsFlow.flow.collect {
                when (it) {
                    LocationServiceControlEvents.RegisterCallbacks -> locationServiceUseCases.registerCallbacks()
                    LocationServiceControlEvents.RemoveCallbacks -> locationServiceUseCases.removeCallbacks()
                    LocationServiceControlEvents.RegisterKickVoteCallback -> locationServiceUseCases.registerCallbackOnKickVote()
                    LocationServiceControlEvents.RegisterSquadInviteCallback -> locationServiceUseCases.registerCallbackOnSquadInvite()
                    LocationServiceControlEvents.RemoveKickVoteCallback -> locationServiceUseCases.removeCallbackOnKickVote()
                    LocationServiceControlEvents.RemoveSquadInviteCallback -> locationServiceUseCases.removeCallbackOnSquadInvite()
                    LocationServiceControlEvents.RegisterNotifications -> locationServiceUseCases.registerNotifications()
                    LocationServiceControlEvents.RemoveNotifications -> locationServiceUseCases.removeNotifications()
                }
            }
        }

        serviceScope.launch {
            locationStateFlow.collectLatest {
                if (it) startListeningUserLocation()
                else fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            }
        }

        serviceScope.launch {
            locationServiceUseCases.registerCallbacks()
        }

        serviceScope.launch {
            locationServiceUseCases.changeUserOnlineStatus(true)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_STICKY_COMPATIBILITY
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        serviceScope.launch {
            locationServiceUseCases.changeUserOnlineStatus(false)
        }
        notificationProvider.cancelDisableBackgroundServiceNotification()
        isServiceStarted = false
        lastKnownLocation = null
        networkManager.unregister()

        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        Log.d("tag", "ugasennnn servis")
        serviceJob.cancel()
        locationServiceUseCases.removeCallbacks()

        stopForeground(1)
        super.onDestroy()
    }


    private fun startListeningUserLocation() {
        val locationRequest =
            LocationRequest.create().setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
                .setWaitForAccurateLocation(true).setInterval(MapConstants.PREFERRED_INTERVAL)
                .setSmallestDisplacement(MAP_PRECISION_METERS)

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )
    }

    companion object {
        var isServiceStarted = false
        var lastKnownLocation: GeoPoint? = null
    }
}