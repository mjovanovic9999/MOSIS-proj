package mosis.streetsandtotems.core.presentation.navigation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.ramcosta.composedestinations.spec.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import mosis.streetsandtotems.NavGraphs
import mosis.streetsandtotems.core.data.data_source.AuthProvider
import mosis.streetsandtotems.core.domain.use_case.PreferenceUseCases
import mosis.streetsandtotems.destinations.MainScreenDestination
import mosis.streetsandtotems.feature_auth.domain.use_case.AuthUseCases
import mosis.streetsandtotems.services.LocationService
import javax.inject.Inject

@HiltViewModel
class AppNavigationViewModel @Inject constructor(
    private val authUseCases: AuthUseCases, private val preferenceUseCases: PreferenceUseCases
) : ViewModel() {
    suspend fun getStartDestination(
        splashScreenCondition: MutableState<Boolean>, startRoute: MutableState<Route?>
    ) {
        if (authUseCases.isUserAuthenticated()) {
            if (LocationService.isServiceStarted || (!authUseCases.isUserAlreadyLoggedIn() && (preferenceUseCases.getAuthProvider() != AuthProvider.EmailAndPassword || authUseCases.isUserEmailVerified()))) startRoute.value =
                MainScreenDestination
        }
        if (startRoute.value == null) startRoute.value = NavGraphs.root.startRoute
        splashScreenCondition.value = false
    }
}