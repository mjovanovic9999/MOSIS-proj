package mosis.streetsandtotems.feature_main.presentation

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.net.Uri
import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.MapConstants
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.core.domain.model.PrivacySettings
import mosis.streetsandtotems.core.domain.model.SnackbarSettings
import mosis.streetsandtotems.core.domain.model.UserSettings
import mosis.streetsandtotems.core.domain.use_case.PreferenceUseCases
import mosis.streetsandtotems.core.domain.util.LocationBroadcastReceiver
import mosis.streetsandtotems.core.domain.util.handleResponse
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
import mosis.streetsandtotems.feature_auth.domain.use_case.AuthUseCases
import mosis.streetsandtotems.feature_main.domain.use_case.MainUseCases
import mosis.streetsandtotems.services.LocationService
import mosis.streetsandtotems.services.LocationServiceControlEvents
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val locationBroadcastReceiver: LocationBroadcastReceiver,
    private val notificationProvider: NotificationProvider,
    private val authUseCases: AuthUseCases,
    private val showLoaderFlow: MutableStateFlow<Boolean>,
    private val snackbarSettingsFlow: MutableStateFlow<SnackbarSettings?>,
    private val application: Application,
    private val locationServiceControlEventsFlow: MutableSharedFlow<LocationServiceControlEvents>,
    private val locationStateMutableSharedFlow: MutableSharedFlow<Boolean>,
    private val preferenceUseCases: PreferenceUseCases,
    private val mainUseCases: MainUseCases,
) : ViewModel() {
    private val _mainScreenEventFlow = MutableSharedFlow<MainScreenEvents>()
    private val _mainScreenState =
        mutableStateOf(
            MainScreenState(
                mainScreenEventFlow = _mainScreenEventFlow,
                locationEnabled = true,
                userSettings = UserSettings(
                    runInBackground = false,
                    showNotifications = false,
                    callPrivacyLevel = PrivacySettings.NoOne,
                    smsPrivacyLevel = PrivacySettings.NoOne,
                ),
                drawerState = DrawerState(DrawerValue.Closed),
                username = "",
                firstName = "",
                lastName = "",
                imageUri = Uri.EMPTY
            )
        )
    val mainScreenState: State<MainScreenState> = _mainScreenState

    init {
        viewModelScope.launch {
            preferenceUseCases.getUserSettingsFlow().collect {
                _mainScreenState.value = _mainScreenState.value.copy(userSettings = it)
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
            MainScreenViewModelEvents.SignOut -> onSignOutHandler()
            MainScreenViewModelEvents.OnPause -> onPauseHandler()
            MainScreenViewModelEvents.OnResume -> onResumeHandler()
            is MainScreenViewModelEvents.ChangeCallPrivacyLevel -> onChangeCallPrivacyLevelHandler(
                event.privacyLevel
            )
            is MainScreenViewModelEvents.ChangeSmsPrivacyLevel -> onChangeSmsPrivacyLevelHandler(
                event.privacyLevel
            )
            MainScreenViewModelEvents.ToggleRunInBackground -> onToggleRunInBackground()
            MainScreenViewModelEvents.ToggleNotifications -> onToggleNotifications()
            MainScreenViewModelEvents.LeaveSquad -> TODO()
        }
    }

    private fun onChangeCallPrivacyLevelHandler(privacyLevel: PrivacySettings) {
        viewModelScope.launch {
            preferenceUseCases.updateUserSettings(
                _mainScreenState.value.userSettings.copy(
                    callPrivacyLevel = privacyLevel
                )
            )
        }
    }

    private fun onChangeSmsPrivacyLevelHandler(privacyLevel: PrivacySettings) {
        viewModelScope.launch {
            preferenceUseCases.updateUserSettings(
                _mainScreenState.value.userSettings.copy(
                    smsPrivacyLevel = privacyLevel
                )
            )
        }
    }

    private fun onToggleRunInBackground() {
        viewModelScope.launch {
            preferenceUseCases.updateUserSettings(
                _mainScreenState.value.userSettings.copy(
                    runInBackground = !_mainScreenState.value.userSettings.runInBackground
                )
            )
        }
    }

    private fun onToggleNotifications() {
        viewModelScope.launch {
            preferenceUseCases.updateUserSettings(
                _mainScreenState.value.userSettings.copy(
                    showNotifications = !_mainScreenState.value.userSettings.showNotifications
                )
            )
        }
    }

    private fun onPauseHandler() {
        viewModelScope.launch {
            if (preferenceUseCases.getUserSettings().runInBackground && LocationService.isServiceStarted) {
                notificationProvider.notifyDisable(true)
                viewModelScope.launch {
                    locationServiceControlEventsFlow.emit(LocationServiceControlEvents.RemoveCallbacks)
                }
            } else {
                application.stopService(Intent(application, LocationService::class.java))
            }
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
            viewModelScope.launch {
                if (preferenceUseCases.getUserSettings().runInBackground) {
                    notificationProvider.notifyDisable(false)
                }

                locationServiceControlEventsFlow.emit(LocationServiceControlEvents.RegisterCallbacks)
            }
        }
    }

    private fun onSignOutHandler() {
        viewModelScope.launch {
            handleResponse(
                responseFlow = authUseCases.signOut(),
                showLoaderFlow = showLoaderFlow,
                snackbarFlow = snackbarSettingsFlow,
                successMessage = MessageConstants.SIGNED_OUT,
                defaultErrorMessage = MessageConstants.SIGN_OUT_ERROR,
                onSuccess = {
                    viewModelScope.launch {
                        _mainScreenEventFlow.emit(MainScreenEvents.SignOutSuccessful)
                        application.stopService(Intent(application, LocationService::class.java))
                    }
                }
            )
        }
    }
}