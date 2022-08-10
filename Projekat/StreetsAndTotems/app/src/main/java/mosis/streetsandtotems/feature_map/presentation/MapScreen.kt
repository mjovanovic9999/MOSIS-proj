package mosis.streetsandtotems.feature_map.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.presentation.components.CustomFAB
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.MainNavGraph
import mosis.streetsandtotems.feature_map.presentation.components.MapComponent


@OptIn(ExperimentalMaterial3Api::class)
@MainNavGraph(start = true)
@Destination
@Composable
fun MapScreen(drawerState: DrawerState, mapViewModel: MapViewModel) {
    val scope = rememberCoroutineScope()


    Box(Modifier) {
        MapComponent(
            Modifier.fillMaxSize()
        )
        Box(modifier = Modifier.matchParentSize()) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                val context = LocalContext.current
                CustomFAB(R.drawable.menu, { scope.launch { drawerState.open() } })
            }
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {

                val context = LocalContext.current
                CustomFAB(
                    R.drawable.layers,
                    {
                        Toast.makeText(context, drawerState.isOpen.toString(), Toast.LENGTH_LONG)
                            .show()
                    })
            }
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                val context = LocalContext.current

                CustomFAB(R.drawable.locate_me, {
                    locateUser(context, mapViewModel)
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

fun locateUser(context: Context, viewModel: MapViewModel) /*:Location*/ {
    viewModel.LoadLocation {
        Toast.makeText(
            context,
            viewModel.locationState.AccuracyMeters.toString(),
            Toast.LENGTH_SHORT
        )
            .show()
    }
}

