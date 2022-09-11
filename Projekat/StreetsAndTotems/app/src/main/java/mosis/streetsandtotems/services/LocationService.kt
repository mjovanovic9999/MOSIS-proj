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
import mosis.streetsandtotems.feature_map.domain.model.PinAction
import mosis.streetsandtotems.feature_map.domain.model.PinActionType
import mosis.streetsandtotems.feature_map.domain.repository.MapServiceRepository
import javax.inject.Inject


@AndroidEntryPoint
class LocationService : Service() {
    @Inject
    lateinit var locationStateFlow: MutableSharedFlow<Boolean>

    @Inject
    lateinit var locationServiceControlEventsFlow: SharedFlowWrapper<LocationServiceControlEvents>

    @Inject
    lateinit var locationServiceEventsFlow: MutableSharedFlow<LocationServiceEvents>

    @Inject
    lateinit var context: Application

    @Inject
    lateinit var notificationProvider: NotificationProvider

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {

            Toast.makeText(
                context,
                result.lastLocation?.accuracy.toString(),
                Toast.LENGTH_SHORT
            ).show()

            serviceScope.launch {
                result.lastLocation?.let {
                    if (it.accuracy <= MAXIMUM_IGNORE_ACCURACY_METERS) {
                        locationServiceEventsFlow.emit(
                            LocationServiceEvents.PlayerLocationChanged(
                                GeoPoint(it.latitude, it.longitude)
                            )
                        )
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
            }
        }
    }

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

        serviceScope.launch {
            locationServiceControlEventsFlow.flow.collectLatest {
                when (it) {
                    LocationServiceControlEvents.RegisterCallbacks -> registerCallbacks()
                    LocationServiceControlEvents.RemoveCallbacks -> mapServiceRepository.removeAllCallbacks()
                }
            }
        }

        serviceScope.launch {
            locationStateFlow.collectLatest {
                if (it) startListeningUserLocation()
                else fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            }
        }

        registerCallbacks()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
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
        stopForeground(1)
        super.onDestroy()

    }

    private fun registerCallbacks() {
        initUserPinsFlow()
        initResourcesFlow()
        initTotemsFlow()
        initCustomPinsFlow()
        initHomesFlow()
        initUserInventoryFlow()
        initMarketFlow()
    }

    private fun initUserPinsFlow() {
        mapServiceRepository.registerCallbacksOnProfileDataUpdate(
            userAddedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.ProfileDataChanged(
                        PinAction(
                            it,
                            PinActionType.Added
                        )
                    )
                )
            },
            userModifiedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.ProfileDataChanged(
                        PinAction(
                            it,
                            PinActionType.Modified
                        )
                    )
                )
            },
            userRemovedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.ProfileDataChanged(
                        PinAction(
                            it,
                            PinActionType.Added
                        )
                    )
                )
            }
        )
    }

    private fun initResourcesFlow() {
        mapServiceRepository.registerCallbackOnResourcesUpdate(
            resourceAddedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.ResourcesChanged(
                        PinAction(
                            it,
                            PinActionType.Added
                        )
                    )
                )
            },
            resourceModifiedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.ResourcesChanged(
                        PinAction(
                            it,
                            PinActionType.Modified
                        )
                    )
                )
            },
            resourceRemovedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.ResourcesChanged(
                        PinAction(
                            it,
                            PinActionType.Removed
                        )
                    )
                )
            }
        )
    }

    private fun initTotemsFlow() {
        mapServiceRepository.registerCallbackOnTotemsUpdate(
            totemAddedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.TotemChanged(
                        PinAction(
                            it,
                            PinActionType.Added
                        )
                    )
                )
            },
            totemModifiedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.TotemChanged(
                        PinAction(
                            it,
                            PinActionType.Modified
                        )
                    )
                )
            },
            totemRemovedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.TotemChanged(
                        PinAction(
                            it,
                            PinActionType.Removed
                        )
                    )
                )
            }
        )
    }

    private fun initCustomPinsFlow() {
        mapServiceRepository.registerCallbackOnCustomPinsUpdate(
            customPinAddedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.CustomPinChanged(
                        PinAction(
                            it,
                            PinActionType.Added
                        )
                    )
                )
            }, customPinModifiedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.CustomPinChanged(
                        PinAction(
                            it,
                            PinActionType.Modified
                        )
                    )
                )
            }, customPinRemovedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.CustomPinChanged(
                        PinAction(
                            it,
                            PinActionType.Removed
                        )
                    )
                )
            }
        )
    }

    private fun initHomesFlow() {
        mapServiceRepository.registerCallbackOnHomesUpdate(
            homeAddedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.HomeChanged(
                        PinAction(
                            it,
                            PinActionType.Added
                        )
                    )
                )
            },
            homeModifiedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.HomeChanged(
                        PinAction(
                            it,
                            PinActionType.Modified
                        )
                    )
                )
            },
            homeRemovedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.HomeChanged(
                        PinAction(
                            it,
                            PinActionType.Removed
                        )
                    )
                )
            }
        )
    }

    private fun initUserInventoryFlow() {
        mapServiceRepository.registerCallbackOnUserInventoryUpdate {
            Log.d("d", it.toString())
            emitLocationServiceEvent(LocationServiceEvents.UserInventoryChanged(it))
        }
    }

    private fun initMarketFlow() {
        mapServiceRepository.registerCallbackOnMarketUpdate(
            marketAddedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.MarketChanged(
                        PinAction(
                            it,
                            PinActionType.Added
                        )
                    )
                )
            },
            marketModifiedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.MarketChanged(
                        PinAction(
                            it,
                            PinActionType.Modified
                        )
                    )
                )
            },
            marketRemovedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.MarketChanged(
                        PinAction(
                            it,
                            PinActionType.Removed
                        )
                    )
                )
            }
        )
    }

    private fun emitLocationServiceEvent(event: LocationServiceEvents) {
        serviceScope.launch {
            locationServiceEventsFlow.emit(event)
        }
    }

    private fun startListeningUserLocation() {
        val locationRequest = LocationRequest.create()
            .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            .setWaitForAccurateLocation(true)
            .setInterval(MapConstants.PREFERRED_INTERVAL)
            .setSmallestDisplacement(MAP_PRECISION_METERS)

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    companion object {
        var isServiceStarted = false
    }
}