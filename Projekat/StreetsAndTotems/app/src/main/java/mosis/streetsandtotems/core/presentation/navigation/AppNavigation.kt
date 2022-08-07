package mosis.streetsandtotems.core.presentation.navigation

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import mosis.streetsandtotems.NavGraphs
import mosis.streetsandtotems.destinations.AuthScreenDestination
import mosis.streetsandtotems.destinations.MainScreenDestination
import mosis.streetsandtotems.feature_auth.presentation.components.AuthViewModel


@Composable
fun AppNavigation(locationPermissionRequest: ActivityResultLauncher<Array<String>>) {
    DestinationsNavHost(navGraph = NavGraphs.root, dependenciesContainerBuilder = {
        dependency(AuthScreenDestination) { hiltViewModel<AuthViewModel>() }
        dependency(MainScreenDestination) { locationPermissionRequest }
    })
}