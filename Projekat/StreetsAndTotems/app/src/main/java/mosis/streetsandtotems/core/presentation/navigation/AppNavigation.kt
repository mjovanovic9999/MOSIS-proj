package mosis.streetsandtotems.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.spec.Route
import mosis.streetsandtotems.NavGraphs
import mosis.streetsandtotems.core.presentation.screens.tiki.TikiScreenViewModel
import mosis.streetsandtotems.destinations.*
import mosis.streetsandtotems.feature_auth.presentation.profile.ProfileViewModel
import mosis.streetsandtotems.feature_auth.presentation.signin.SignInViewModel
import mosis.streetsandtotems.feature_auth.presentation.signup.SignupViewModel
import mosis.streetsandtotems.feature_leaderboards.presentation.LeaderboardsViewModel
import mosis.streetsandtotems.feature_main.presentation.MainScreenViewModel


@Composable
fun AppNavigation(startRoute: Route?) {
    if (startRoute != null)
        DestinationsNavHost(navGraph = NavGraphs.root,
            startRoute = startRoute,
            dependenciesContainerBuilder = {
                dependency(SignInScreenDestination) { hiltViewModel<SignInViewModel>() }
                dependency(SignUpScreenDestination) { hiltViewModel<SignupViewModel>() }
                dependency(ProfileScreenDestination) { hiltViewModel<ProfileViewModel>() }
                dependency(LeaderboardsScreenDestination) { hiltViewModel<LeaderboardsViewModel>() }
                dependency(MainScreenDestination) { hiltViewModel<MainScreenViewModel>() }
                dependency(TikiScreenDestination) { hiltViewModel<TikiScreenViewModel>() }
            })
}
