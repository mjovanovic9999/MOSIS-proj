package mosis.streetsandtotems.core.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import mosis.streetsandtotems.NavGraphs
import mosis.streetsandtotems.destinations.SignInScreenDestination
import mosis.streetsandtotems.feature_auth.presentation.signin.SignInViewModel


@Composable
fun AppNavigation() {
    Box(modifier = Modifier.fillMaxSize())
    {
        DestinationsNavHost(navGraph = NavGraphs.root, dependenciesContainerBuilder = {
            dependency(SignInScreenDestination) { hiltViewModel<SignInViewModel>() }
        })
    }
}
