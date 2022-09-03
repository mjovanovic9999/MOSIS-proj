package mosis.streetsandtotems.feature_main.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mosis.streetsandtotems.destinations.SignInScreenDestination
import mosis.streetsandtotems.feature_main.presentation.components.DrawerContent
import mosis.streetsandtotems.feature_main.presentation.components.DrawerScreen
import mosis.streetsandtotems.feature_main.presentation.components.LifecycleCompose
import mosis.streetsandtotems.feature_map.presentation.components.CustomRequestLocation
import mosis.streetsandtotems.feature_settings_persistence.PreferencesDataStore
import mosis.streetsandtotems.feature_settings_persistence.PrivacySettings
import mosis.streetsandtotems.feature_settings_persistence.UserSettings
import mosis.streetsandtotems.services.LocationService
import mosis.streetsandtotems.ui.theme.sizes


@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun MainScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: MainScreenViewModel,
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val state = viewModel.mainScreenState.value

    val context = LocalContext.current
    val preferencesDataStore = PreferencesDataStore(context) //treba se inject vrv

    val scope = rememberCoroutineScope()
    val userSettings = remember {
        mutableStateOf(
            UserSettings(
                runInBackground = true,
                showNotifications = true,
                showMyPhoneNumber = false,
                callPrivacyLevel = PrivacySettings.NO_ONE,
                smsPrivacyLevel = PrivacySettings.NO_ONE,
            )
        )
    }

    scope.launch {
        preferencesDataStore.userSettingsFlow.collect {
            userSettings.value = it
        }
    }

    LaunchedEffect(Unit) {
        state.mainScreenEventFlow.collectLatest {
            if (it != null)
                when (it) {
                    MainScreenEvents.SignOutSuccessful -> {
                        destinationsNavigator.popBackStack()
                        destinationsNavigator.navigate(SignInScreenDestination)
                    }
                }
        }
    }

    LifecycleCompose(
        viewModel.locationBroadcastReceiver,
        viewModel.notificationProvider
    )

    CustomRequestLocation(LocationService.isLocationEnabled)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxSize()
                        .padding(MaterialTheme.sizes.drawer_column_padding),
                    destinationsNavigator,
                    onSignOut = { viewModel.onEvent(MainScreenViewModelEvents.SignOut) },
                    runInBackground = userSettings.value.runInBackground,
                    onRunInBackgroundChange = {
                        viewModel.viewModelScope.launch {
                            preferencesDataStore.saveUserSettings(
                                userSettings = userSettings.value.copy(
                                    runInBackground = !userSettings.value.runInBackground
                                )
                            )
                        }
                    },
                    showNotifications = userSettings.value.showNotifications,
                    onShowNotificationsChange = {
                        viewModel.viewModelScope.launch {
                            preferencesDataStore.saveUserSettings(
                                userSettings = userSettings.value.copy(
                                    showNotifications = !userSettings.value.showNotifications
                                )
                            )
                        }
                    },
                    showMyPhoneNumber = userSettings.value.showMyPhoneNumber,
                    onShowMyPhoneNumberChange = {
                        viewModel.viewModelScope.launch {
                            preferencesDataStore.saveUserSettings(
                                userSettings = userSettings.value.copy(
                                    showMyPhoneNumber = !userSettings.value.showMyPhoneNumber
                                )
                            )
                        }
                    },
                    callPrivacyLevel = userSettings.value.callPrivacyLevel.ordinal,
                    onCallPrivacyLevelIndexChange = {
                        viewModel.viewModelScope.launch {
                            preferencesDataStore.saveUserSettings(
                                userSettings = userSettings.value.copy(
                                    callPrivacyLevel = PrivacySettings.values()[it]
                                )
                            )
                        }
                    },
                    smsPrivacyLevel = userSettings.value.smsPrivacyLevel.ordinal,
                    onSmsPrivacyLevelChange = {
                        viewModel.viewModelScope.launch {
                            preferencesDataStore.saveUserSettings(
                                userSettings = userSettings.value.copy(
                                    smsPrivacyLevel = PrivacySettings.values()[it]
                                )
                            )
                        }
                    }
                )
            }
        },
        content = { DrawerScreen(navController = navController, drawerState = drawerState) }
    )
}



