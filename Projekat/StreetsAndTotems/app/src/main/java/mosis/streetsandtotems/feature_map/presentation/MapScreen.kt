package mosis.streetsandtotems.feature_map.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.presentation.components.CustomFAB
import mosis.streetsandtotems.feature_map.presentation.components.MapComponent


@Composable
fun MapScreen(/*mapViewModel: MapViewModel,*/ modifier: Modifier = Modifier) {
    Box(Modifier) {
        MapComponent(
            Modifier
                .fillMaxSize()
        )
        Box(modifier = Modifier.matchParentSize()) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {

                CustomFAB(R.drawable.menu, {})
                CustomFAB(R.drawable.layers, {})
            }
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                val context = LocalContext.current

                CustomFAB(R.drawable.locate_me, {
                    /*    mapViewModel.viewModelScope.launch {
                            mapViewModel.state.centerOnMarker(
                                "prvi",
                                0.4f
                            )
                        }*/
                })
                CustomFAB(R.drawable.add_pin, {
                    Toast.makeText(context, "Ako ima vreme za cutom pin", Toast.LENGTH_LONG).show()
                })
            }
        }

    }
}

