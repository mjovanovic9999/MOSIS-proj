package mosis.streetsandtotems.feature_map.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.presentation.components.CustomFAB
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.MainNavGraph
import mosis.streetsandtotems.feature_map.presentation.components.MapComponent
import mosis.streetsandtotems.ui.theme.sizes


@OptIn(ExperimentalMaterial3Api::class)
@MainNavGraph(start = true)
@Destination
@Composable
fun MapScreen(drawerState: DrawerState, mapViewModel: MapViewModel) {
    val scope = rememberCoroutineScope()


    Box(Modifier) {
        MapComponent(
            modifier = Modifier.fillMaxSize()
        )
        Box(modifier = Modifier.matchParentSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                CustomFAB(
                    imageVector = ImageVector.vectorResource(id = R.drawable.menu),
                    onClick = { scope.launch { drawerState.open() } },
                    modifier = Modifier.padding(MaterialTheme.sizes.fab_padding)
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {

                val context = LocalContext.current
                CustomFAB(
                    imageVector = ImageVector.vectorResource(id = R.drawable.layers),
                    onClick = {
                        Toast.makeText(context, drawerState.isOpen.toString(), Toast.LENGTH_LONG)
                            .show()
                    },
                    modifier = Modifier.padding(MaterialTheme.sizes.fab_padding)
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                val context = LocalContext.current

                CustomFAB(
                    imageVector = ImageVector.vectorResource(id = R.drawable.locate_me),
                    onClick = {
                        locateUser(context, mapViewModel)
                        /*    mapViewModel.viewModelScope.launch {
                                mapViewModel.state.centerOnMarker(
                                    "prvi",
                                    0.4f
                                )
                            }*/
                    },
                    modifier = Modifier.padding(MaterialTheme.sizes.fab_padding)
                )
                CustomFAB(
                    imageVector = ImageVector.vectorResource(id = R.drawable.add_pin),
                    onClick = {
                        Toast.makeText(context, "Ako ima vreme za cutom pin", Toast.LENGTH_LONG)
                            .show()
                    },
                    modifier = Modifier.padding(MaterialTheme.sizes.fab_padding)
                )

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

