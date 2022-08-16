package mosis.streetsandtotems.feature_map.presentation.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mosis.streetsandtotems.R
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.onTap
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
/*    val cntx=LocalContext.current*/
    mapState.onTap { x, y ->
/*        Toast.makeText(cntx, x.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(cntx, y.toString(), Toast.LENGTH_SHORT).show()*/

        mapState.addMarker(
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