package mosis.streetsandtotems.feature_map.presentation.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ovh.plrapps.mapcompose.ui.MapUI
import ovh.plrapps.mapcompose.ui.state.MapState

@Composable
fun MapComponent(
    modifier: Modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
    mapState: MapState
) {
    MapUI(
        modifier, state = mapState
    )
}