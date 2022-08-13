package mosis.streetsandtotems.core.presentation

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import mosis.streetsandtotems.core.presentation.navigation.AppNavigation
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
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
                AppNavigation()
            }
        }

    }

    override fun onResume() {
        super.onResume()
//        Toast.makeText(this, "onresume", Toast.LENGTH_LONG).show()
        //this.stopService(Intent(this, LocationService::class.java))

        if (foregroundComponentName == null)
            foregroundComponentName =
                this.startForegroundService(Intent(this, LocationService::class.java))
        else {
            notificationProvider.notifyDisable(false)
        }

    }

    override fun onPause() {
//        Toast.makeText(this, "onpause", Toast.LENGTH_LONG).show()
        notificationProvider.notifyDisable(true)

        super.onPause()
    }

}