package mosis.streetsandtotems.core.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import mosis.streetsandtotems.NavGraphs
import mosis.streetsandtotems.destinations.ProfileScreenDestination
import mosis.streetsandtotems.destinations.SignInScreenDestination
import mosis.streetsandtotems.destinations.SignUpScreenDestination
import mosis.streetsandtotems.feature_auth.presentation.profile.ProfileViewModel
import mosis.streetsandtotems.feature_auth.presentation.signin.SignInViewModel
import mosis.streetsandtotems.feature_auth.presentation.signup.SignupViewModel


@Composable
fun AppNavigation() {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background))
    {
        DestinationsNavHost(navGraph = NavGraphs.root, dependenciesContainerBuilder = {
            dependency(SignInScreenDestination) { hiltViewModel<SignInViewModel>() }
            dependency(SignUpScreenDestination) { hiltViewModel<SignupViewModel>()}
            dependency(ProfileScreenDestination) { hiltViewModel<ProfileViewModel>()}
        })
    }
}
