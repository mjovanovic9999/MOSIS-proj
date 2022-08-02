package mosis.streetsandtotems.feature_map.presentation.map.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import mosis.streetsandtotems.R

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
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

                FAB(R.drawable.menu, {})
                FAB(R.drawable.layers, {})
            }
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                FAB(R.drawable.locate_me, {})
                FAB(R.drawable.add_pin, {})
            }
        }

    }
}