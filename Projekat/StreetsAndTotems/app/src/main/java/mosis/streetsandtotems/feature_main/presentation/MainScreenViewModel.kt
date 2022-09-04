package mosis.streetsandtotems.feature_main.presentation

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.HandleResponseConstants
import mosis.streetsandtotems.core.MapConstants
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.core.domain.model.SnackbarSettings
import mosis.streetsandtotems.core.domain.util.LocationBroadcastReceiver
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
import mosis.streetsandtotems.feature_auth.domain.use_case.AuthUseCases
import mosis.streetsandtotems.feature_settings_persistence.PreferencesDataStore
import mosis.streetsandtotems.services.LocationService
import mosis.streetsandtotems.services.LocationServiceControlEvents
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val locationBroadcastReceiver: LocationBroadcastReceiver,
    private val notificationProvider: NotificationProvider,
    private val authUseCases: AuthUseCases,
    private val showLoaderFlow: MutableStateFlow<Boolean>,
    private val snackbarSettingsFlow: MutableStateFlow<SnackbarSettings?>,
    private val application: Application,
    private val locationServiceControlEventsFlow: MutableSharedFlow<LocationServiceControlEvents>,
    private val locationStateMutableSharedFlow: MutableSharedFlow<Boolean>
) : ViewModel() {
    private var backgroundServiceEnabled = true
    private val _mainScreenEventFlow = MutableStateFlow<MainScreenEvents?>(null)
    private val _mainScreenState =
        mutableStateOf(
            MainScreenState(
                mainScreenEventFlow = _mainScreenEventFlow,
                locationEnabled = true
            )
        )
    val mainScreenState: State<MainScreenState> = _mainScreenState

    init {
        viewModelScope.launch {
            PreferencesDataStore(application).userSettingsFlow.map { it.runInBackground }.collect {
                backgroundServiceEnabled = it
            }
        }
        viewModelScope.launch {
            locationStateMutableSharedFlow.collectLatest {
                _mainScreenState.value = _mainScreenState.value.copy(locationEnabled = it)
            }
        }
    }

    fun onEvent(event: MainScreenViewModelEvents) {
        when (event) {
            MainScreenViewModelEvents.SignOut -> signOutHandler()
            MainScreenViewModelEvents.OnPause -> onPauseHandler()
            MainScreenViewModelEvents.OnResume -> onResumeHandler()
        }
    }

    private fun onPauseHandler() {
        if (backgroundServiceEnabled && LocationService.isServiceStarted) {
            notificationProvider.notifyDisable(true)
            viewModelScope.launch {
                locationServiceControlEventsFlow.emit(LocationServiceControlEvents.RemoveCallbacks)
            }
        } else {
            application.stopService(Intent(application, LocationService::class.java))
        }

        application.unregisterReceiver(
            locationBroadcastReceiver,
        )


    }

    private fun onResumeHandler() {
        val locationRequest = LocationRequest.create()
            .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            .setWaitForAccurateLocation(true)
            .setInterval(MapConstants.PREFERRED_INTERVAL)
            .setSmallestDisplacement(MapConstants.MAP_PRECISION_METERS)

        val locationSettingsRequest =
            LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)
                .build()

        viewModelScope.launch {
            try {
                LocationServices.getSettingsClient(application)
                    .checkLocationSettings(locationSettingsRequest).await()
                locationStateMutableSharedFlow.emit(value = true)
            } catch (e: Exception) {
                locationStateMutableSharedFlow.emit(value = false)
            }
        }

        application.registerReceiver(
            locationBroadcastReceiver,
            IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        )

        if (!LocationService.isServiceStarted) {
            Log.d("tag", "startuje servis")

            application.startForegroundService(
                Intent(
                    application,
                    LocationService::class.java
                )
            )

        } else {
            if (backgroundServiceEnabled) {
                notificationProvider.notifyDisable(false)
            }
            viewModelScope.launch {
                locationServiceControlEventsFlow.emit(LocationServiceControlEvents.RegisterCallbacks)
            }
        }
    }

    private fun signOutHandler() {
        viewModelScope.launch {
            authUseCases.signOut()
            _mainScreenEventFlow.emit(MainScreenEvents.SignOutSuccessful)
            snackbarSettingsFlow.emit(
                SnackbarSettings(
                    message = MessageConstants.SIGNED_OUT,
                    duration = SnackbarDuration.Short,
                    snackbarId = snackbarSettingsFlow.value?.snackbarId?.plus(
                        HandleResponseConstants.ID_ADDITION_FACTOR
                    )
                        ?: HandleResponseConstants.DEFAULT_ID,
                )
            )
        }
    }
}