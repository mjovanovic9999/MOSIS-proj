package mosis.streetsandtotems.feature_map.presentation.map.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import mosis.streetsandtotems.R
import mosis.streetsandtotems.feature_map.presentation.map.MapViewModel
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.onTap
import ovh.plrapps.mapcompose.ui.MapUI
import ovh.plrapps.mapcompose.ui.state.markers.model.RenderingStrategy

@Composable
fun MapComponent(
    modifier: Modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
    viewModel: MapViewModel = hiltViewModel()
) {
    MapUI(
        modifier, state = viewModel.state
    )
/*    val cntx=LocalContext.current*/
    viewModel.state.onTap { x, y ->
/*        Toast.makeText(cntx, x.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(cntx, y.toString(), Toast.LENGTH_SHORT).show()*/

        viewModel.state.addMarker(
/*
            (x + y).toString(),
*/
            "prvi",
            x,
            y,
            c = { Pin(R.drawable.pin_emerald) },
            clipShape = null

        )

    }
}