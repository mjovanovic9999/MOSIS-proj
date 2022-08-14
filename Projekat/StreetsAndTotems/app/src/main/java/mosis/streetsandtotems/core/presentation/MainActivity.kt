package mosis.streetsandtotems.core.presentation

import android.Manifest
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import mosis.streetsandtotems.core.presentation.navigation.AppNavigation
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
import mosis.streetsandtotems.feature_map.presentation.components.CustomRequestLocation
import mosis.streetsandtotems.feature_map.presentation.components.CustomRequestPermission
import mosis.streetsandtotems.services.LocationService
import mosis.streetsandtotems.ui.theme.AppTheme
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity() : ComponentActivity() {


    @Inject
    lateinit var notificationProvider: NotificationProvider

    private var foregroundComponentName: ComponentName? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        installSplashScreen().apply {}
        setContent {
            AppTheme {
                CustomRequestPermission(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    ),
                )
                val locationEnabledState =
                    remember { LocationService.isLocationEnabled }

                if (!locationEnabledState.value)
                    CustomRequestLocation()

                AppNavigation()
            }
        }

    }

    override fun onResume() {
        super.onResume()

        if (!LocationService.isServiceStarted)//treb si ostane ugasen
        {
            notificationProvider.notifyDisable(false)

            foregroundComponentName =
                this.startForegroundService(Intent(this, LocationService::class.java))
            Log.d("tag", "created")


        } else {
            notificationProvider.notifyDisable(false)
        }

    }

    override fun onPause() {
        notificationProvider.notifyDisable(true)

        super.onPause()
    }
}