package mosis.streetsandtotems.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import mosis.streetsandtotems.feature_auth.presentation.components.AuthViewModel
import mosis.streetsandtotems.feature_auth.presentation.components.NavGraphs
import mosis.streetsandtotems.feature_auth.presentation.components.destinations.AuthScreenDestination


@Composable
fun AppNavigation(){
    DestinationsNavHost(navGraph = NavGraphs.root, dependenciesContainerBuilder = {
        dependency(AuthScreenDestination) { hiltViewModel<AuthViewModel>()}
    })
}