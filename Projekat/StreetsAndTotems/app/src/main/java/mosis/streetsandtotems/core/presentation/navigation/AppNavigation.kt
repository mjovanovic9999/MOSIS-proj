package mosis.streetsandtotems.core.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import mosis.streetsandtotems.NavGraphs
import mosis.streetsandtotems.destinations.MainScreenDestination
import mosis.streetsandtotems.destinations.SignInScreenDestination
import mosis.streetsandtotems.destinations.SignUpScreenDestination
import mosis.streetsandtotems.feature_auth.presentation.signin.SignInViewModel
import mosis.streetsandtotems.feature_auth.presentation.signup.SignupViewModel


@Composable
fun AppNavigation() {
    Box(modifier = Modifier.fillMaxSize())
    {
        DestinationsNavHost(navGraph = NavGraphs.root, dependenciesContainerBuilder = {
            dependency(SignInScreenDestination) { hiltViewModel<SignInViewModel>() }
            dependency(SignUpScreenDestination) { hiltViewModel<SignupViewModel>()}
        })
    }
}
