package mosis.streetsandtotems.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import mosis.streetsandtotems.core.presentation.navigation.AppNavigation
import mosis.streetsandtotems.feature_map.presentation.map.MapScreen
import mosis.streetsandtotems.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    AppNavigation()
                }
            }
        }
    }
}
