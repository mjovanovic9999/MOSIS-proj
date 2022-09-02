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
import com.google.firebase.firestore.GeoPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import mosis.streetsandtotems.core.MapConstants.MAP_PRECISION_METERS
import mosis.streetsandtotems.core.MapConstants.MAXIMUM_IGNORE_ACCURACY_METERS
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
import mosis.streetsandtotems.feature_map.domain.model.*
import mosis.streetsandtotems.feature_map.domain.repository.MapServiceRepository
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

    @Inject
    lateinit var mapServiceRepository: MapServiceRepository

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    override fun onCreate() {
        Log.d("tag", "brmm brmm")
        isServiceStarted = true
        super.onCreate()
        startForeground(
            1,
            notificationProvider.returnDisableBackgroundServiceNotification(false)
        )

        initUserPinsFlow()
        initResourcesFlow()
        initTotemsFlow()

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
        mapServiceRepository.removeAllCallbacks()
        super.onDestroy()

    }

    private fun initUserPinsFlow() {
        mapServiceRepository.registerCallbacksOnUserInGameDataUpdate(
            userAddedCallback = {
                emitPinActionWithServiceScope(it, playersPinFlow, PinActionType.Added)
            },
            userModifiedCallback = {
                emitPinActionWithServiceScope(it, playersPinFlow, PinActionType.Modified)

            },
            userRemovedCallback = {
                emitPinActionWithServiceScope(it, playersPinFlow, PinActionType.Removed)
            }
        )
    }

    private fun initResourcesFlow() {
        mapServiceRepository.registerCallbackOnResourcesUpdate(
            resourceAddedCallback = {
                emitPinActionWithServiceScope(
                    it,
                    resourcesPinsFlow,
                    PinActionType.Added
                )
            },
            resourceModifiedCallback = {
                emitPinActionWithServiceScope(
                    it,
                    resourcesPinsFlow,
                    PinActionType.Modified
                )
            },
            resourceRemovedCallback = {
                emitPinActionWithServiceScope(
                    it,
                    resourcesPinsFlow,
                    PinActionType.Removed
                )
            }
        )
    }

    private fun initTotemsFlow() {
        mapServiceRepository.registerCallbackOnTotemsUpdate(
            totemAddedCallback = {
                emitPinActionWithServiceScope(
                    it,
                    totemsPinsFlow,
                    PinActionType.Added
                )
            },
            totemModifiedCallback = {
                emitPinActionWithServiceScope(
                    it,
                    totemsPinsFlow,
                    PinActionType.Modified
                )
            },
            totemRemovedCallback = {
                emitPinActionWithServiceScope(
                    it,
                    totemsPinsFlow,
                    PinActionType.Removed
                )
            }
        )
    }

    private suspend fun <T> emitPinAction(
        pinData: T?,
        pinFlow: MutableSharedFlow<PinAction<T>?>,
        actionType: PinActionType
    ) {
        pinData?.let {
            pinFlow.emit(PinAction(pinData, actionType))
        }
    }

    private fun <T> emitPinActionWithServiceScope(
        pinData: T?,
        pinFlow: MutableSharedFlow<PinAction<T>?>,
        actionType: PinActionType
    ) {
        serviceScope.launch {
            emitPinAction(pinData = pinData, pinFlow = pinFlow, actionType = actionType)
        }
    }

    private fun startListeningUserLocation() {

        val locationRequest = LocationRequest.create()
            .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            .setWaitForAccurateLocation(true)
            .setInterval(4000)
            .setSmallestDisplacement(MAP_PRECISION_METERS)


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
                    result.lastLocation?.let {
                        if (it.accuracy <= MAXIMUM_IGNORE_ACCURACY_METERS) {
                            locationFlow.emit(it)
                            mapServiceRepository.updateMyLocation(
                                GeoPoint(
                                    it.latitude,
                                    it.longitude
                                )
                            )
                        }
                    }
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
        val locationFlow: MutableStateFlow<Location?> = MutableStateFlow(value = null)
        val playersPinFlow: MutableSharedFlow<PinAction<UserInGameData>?> =
            MutableSharedFlow()
        val resourcesPinsFlow: MutableSharedFlow<PinAction<ResourceData>?> =
            MutableSharedFlow()
        val totemsPinsFlow: MutableSharedFlow<PinAction<TotemData>?> =
            MutableSharedFlow()
        val customPinsFlow: MutableStateFlow<PinAction<CustomPinData>?> =//neje uvrzano
            MutableStateFlow(value = null)
        val homePinsFlow: MutableStateFlow<PinAction<HomeData>?> =//neje uvrzano
            MutableStateFlow(value = null)
        var isServiceStarted = false
        var isLocationEnabled = mutableStateOf(value = true)
    }
}