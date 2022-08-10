package mosis.streetsandtotems.feature_map.presentation.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import mosis.streetsandtotems.R
import mosis.streetsandtotems.feature_map.presentation.MapViewModel
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.onTap
import ovh.plrapps.mapcompose.ui.MapUI

@Composable
fun MapComponent(
    modifier: Modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
    viewModel: MapViewModel = hiltViewModel()
) {
    MapUI(
        modifier, state = viewModel.mapState
    )
/*    val cntx=LocalContext.current*/
    viewModel.mapState.onTap { x, y ->
/*        Toast.makeText(cntx, x.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(cntx, y.toString(), Toast.LENGTH_SHORT).show()*/

        viewModel.mapState.addMarker(
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