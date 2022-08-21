package mosis.streetsandtotems.core.presentation.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import mosis.streetsandtotems.core.HandleResponseConstants
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.core.domain.model.SnackbarSettings
import mosis.streetsandtotems.core.domain.util.LocationBroadcastReceiver
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
import mosis.streetsandtotems.feature_auth.domain.use_case.AuthUseCases
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    val locationBroadcastReceiver: LocationBroadcastReceiver,
    val notificationProvider: NotificationProvider,
    private val authUseCases: AuthUseCases,
    private val showLoaderFlow: MutableStateFlow<Boolean>,
    private val snackbarSettingsFlow: MutableStateFlow<SnackbarSettings?>
) : ViewModel() {
    private val _mainScreenEventFlow = MutableStateFlow<MainScreenEvents?>(null)
    private val _mainScreenState =
        mutableStateOf(MainScreenState(mainScreenEventFlow = _mainScreenEventFlow))
    val mainScreenState: State<MainScreenState> = _mainScreenState

    fun onEvent(event: MainScreenViewModelEvents) {
        when (event) {
            MainScreenViewModelEvents.SignOut -> signOutHandler()
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