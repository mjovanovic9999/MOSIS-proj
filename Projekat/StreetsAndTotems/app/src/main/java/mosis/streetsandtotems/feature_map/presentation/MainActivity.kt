package mosis.streetsandtotems.feature_map.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import mosis.streetsandtotems.feature_map.presentation.map.components.MapScreen
import mosis.streetsandtotems.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
               MapScreen(Modifier)

            }
        }
    }
}


