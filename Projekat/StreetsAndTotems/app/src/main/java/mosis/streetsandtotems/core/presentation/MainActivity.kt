package mosis.streetsandtotems.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import mosis.streetsandtotems.core.presentation.navigation.AppNavigation
import mosis.streetsandtotems.ui.theme.AppTheme


@AndroidEntryPoint
class MainActivity() : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {}
        setContent {
            AppTheme {
                AppNavigation()
            }
        }

    }
}