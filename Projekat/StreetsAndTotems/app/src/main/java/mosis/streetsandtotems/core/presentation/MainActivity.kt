package mosis.streetsandtotems.core.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.ramcosta.composedestinations.spec.Route
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import mosis.streetsandtotems.core.domain.model.SnackbarSettings
import mosis.streetsandtotems.core.presentation.components.CustomLoader
import mosis.streetsandtotems.core.presentation.components.CustomSnackbar
import mosis.streetsandtotems.core.presentation.navigation.AppNavigation
import mosis.streetsandtotems.core.presentation.navigation.AppNavigationViewModel
import mosis.streetsandtotems.di.util.StateFlowWrapper
import mosis.streetsandtotems.feature_map.presentation.components.CustomRequestNetwork
import mosis.streetsandtotems.feature_map.presentation.components.CustomRequestPermission
import mosis.streetsandtotems.services.NetworkManager
import mosis.streetsandtotems.ui.theme.AppTheme
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity() : ComponentActivity() {

    private val arePermissionsGranted = mutableStateOf(false)

    @Inject
    lateinit var networkManager: NetworkManager

    @Inject
    lateinit var snackbarFlow: StateFlowWrapper<SnackbarSettings?>

    @Inject
    lateinit var showLoaderFlow: StateFlowWrapper<Boolean>

    private val appNavigationViewModel: AppNavigationViewModel by viewModels()

    private val splashScreenCondition = mutableStateOf(true)

    private val startRoute: MutableState<Route?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            appNavigationViewModel.getStartDestination(splashScreenCondition, startRoute)
        }

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                splashScreenCondition.value
            }
        }
        setContent {
            AppTheme {
                if (!arePermissionsGranted.value) {
                    CustomRequestPermission(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                        ), arePermissionsGranted
                    )
                }
                CustomRequestNetwork(NetworkManager.isNetworkConnectivityValid)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    AppNavigation(startRoute.value)
                    CustomSnackbar(snackbarSettingsFlow = snackbarFlow.flow)
                    CustomLoader(showLoaderFlow = showLoaderFlow.flow)
                }
            }
        }
    }
}






