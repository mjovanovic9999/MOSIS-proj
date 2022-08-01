package mosis.streetsandtotems.feature_map.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import mosis.streetsandtotems.R
import mosis.streetsandtotems.feature_map.presentation.map.components.FAB
import mosis.streetsandtotems.feature_map.presentation.map.components.MapContainer
import mosis.streetsandtotems.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Box {
                    MapContainer(
                        Modifier
                            .height(400.dp)
                            .width(400.dp)
                    )
                    FAB(R.drawable.locate_me, {}, DpOffset(300.dp, 350.dp))

                }

            }
        }
    }
}




