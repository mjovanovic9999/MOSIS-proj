package mosis.streetsandtotems.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import mosis.streetsandtotems.NavGraphs
import mosis.streetsandtotems.destinations.*
import mosis.streetsandtotems.feature_auth.presentation.profile.ProfileViewModel
import mosis.streetsandtotems.feature_auth.presentation.signin.SignInViewModel
import mosis.streetsandtotems.feature_auth.presentation.signup.SignupViewModel
import mosis.streetsandtotems.feature_leaderboards.presentation.LeaderboardsViewModel


@Composable
fun AppNavigation(isUserAuthenticated: State<Boolean>) {
    DestinationsNavHost(
        navGraph = NavGraphs.root,
        startRoute = if (isUserAuthenticated.value) MainScreenDestination else NavGraphs.root.startRoute,
        dependenciesContainerBuilder = {
            dependency(SignInScreenDestination) { hiltViewModel<SignInViewModel>() }
            dependency(SignUpScreenDestination) { hiltViewModel<SignupViewModel>() }
            dependency(ProfileScreenDestination) { hiltViewModel<ProfileViewModel>() }
            dependency(LeaderboardsScreenDestination) { hiltViewModel<LeaderboardsViewModel>() }
            dependency(MainScreenDestination) { hiltViewModel<MainScreenViewModel>() }
        })
}
