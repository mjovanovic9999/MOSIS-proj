package mosis.streetsandtotems.feature_main.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import mosis.streetsandtotems.core.domain.model.PrivacySettings
import mosis.streetsandtotems.destinations.SignInScreenDestination
import mosis.streetsandtotems.feature_main.presentation.components.DrawerContent
import mosis.streetsandtotems.feature_main.presentation.components.DrawerScreen
import mosis.streetsandtotems.feature_main.presentation.components.LifecycleCompose
import mosis.streetsandtotems.feature_map.presentation.components.CustomRequestLocationDialog
import mosis.streetsandtotems.ui.theme.sizes


@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun MainScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: MainScreenViewModel,
) {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val state = viewModel.mainScreenState.value

    LaunchedEffect(Unit) {
        state.mainScreenEventFlow.collect {
            when (it) {
                MainScreenEvents.SignOutSuccessful -> {
                    destinationsNavigator.popBackStack()
                    destinationsNavigator.navigate(SignInScreenDestination)
                }
            }
        }
    }

    LifecycleCompose(onResume = { viewModel.onEvent(MainScreenViewModelEvents.OnResume) },
        onPause = { viewModel.onEvent(MainScreenViewModelEvents.OnPause) })

    if (!state.locationEnabled) CustomRequestLocationDialog()

    ModalNavigationDrawer(drawerState = state.drawerState, drawerContent = {
        ModalDrawerSheet {
            DrawerContent(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxSize()
                    .padding(MaterialTheme.sizes.drawer_column_padding),
                destinationsNavigator,
                onSignOut = { viewModel.onEvent(MainScreenViewModelEvents.SignOut) },
                runInBackground = state.userSettings.runInBackground,
                onRunInBackgroundChange = { viewModel.onEvent(MainScreenViewModelEvents.ToggleRunInBackground) },
                showNotifications = state.userSettings.showNotifications,
                onShowNotificationsChange = { viewModel.onEvent(MainScreenViewModelEvents.ToggleNotifications) },
                callPrivacyLevel = state.userSettings.callPrivacyLevel.ordinal,
                onCallPrivacyLevelIndexChange = {
                    viewModel.onEvent(
                        MainScreenViewModelEvents.ChangeCallPrivacyLevel(
                            PrivacySettings.values()[it]
                        )
                    )
                },
                smsPrivacyLevel = state.userSettings.smsPrivacyLevel.ordinal,
                onSmsPrivacyLevelChange = {
                    viewModel.onEvent(
                        MainScreenViewModelEvents.ChangeSmsPrivacyLevel(
                            PrivacySettings.values()[it]
                        )
                    )
                },
                username = state.currentUserData.user_name ?: "",
                firstName = state.currentUserData.first_name ?: "",
                lastName = state.currentUserData.last_name ?: "",
                imagePath = state.currentUserData.image_uri ?: "",
                squadId = state.currentUserData.squad_id ?: "",
                phoneNumber = state.currentUserData.phone_number ?: "",
                email = state.currentUserData.email ?: "",
                onLeaveSquad = { viewModel.onEvent(MainScreenViewModelEvents.LeaveSquad) })
        }
    }, content = {
        DrawerScreen(navController = navController,
            openDrawer = { scope.launch { state.drawerState.open() } })
    })
}



