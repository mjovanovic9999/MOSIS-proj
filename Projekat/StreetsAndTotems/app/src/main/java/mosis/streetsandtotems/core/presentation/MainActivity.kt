package mosis.streetsandtotems.core.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import mosis.streetsandtotems.core.domain.model.SnackbarSettings
import mosis.streetsandtotems.core.presentation.components.CustomLoader
import mosis.streetsandtotems.core.presentation.components.CustomSnackbar
import mosis.streetsandtotems.core.presentation.navigation.AppNavigation
import mosis.streetsandtotems.feature_map.presentation.components.CustomRequestNetwork
import mosis.streetsandtotems.feature_map.presentation.components.CustomRequestPermission
import mosis.streetsandtotems.feature_settings_persistence.PreferencesDataStore
import mosis.streetsandtotems.services.NetworkManager
import mosis.streetsandtotems.ui.theme.AppTheme
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity() : ComponentActivity() {

    private val arePermissionsGranted = mutableStateOf(false)

    @Inject
    lateinit var networkManager: NetworkManager

    @Inject
    lateinit var snackbarFlow: MutableStateFlow<SnackbarSettings?>

    @Inject
    lateinit var showLoaderFlow: MutableStateFlow<Boolean>

    @Inject
    lateinit var isUserAuthenticated: State<Boolean>

    @Inject
    lateinit var store: PreferencesDataStore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {}
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
                    AppNavigation(isUserAuthenticated)
                    CustomSnackbar(snackbarSettingsFlow = snackbarFlow)
                    CustomLoader(showLoaderFlow = showLoaderFlow)
                }
            }
        }
    }
}






