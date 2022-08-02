package mosis.streetsandtotems.feature_map.presentation.map.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    viewModel.state.onTap { x, y ->
        viewModel.state.addMarker(
            (x + y).toString(),
            x,
            y,
            c = { Pin(R.drawable.pin_emerald) },
            clipShape = null
        )
    }
}