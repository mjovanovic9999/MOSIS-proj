package mosis.streetsandtotems.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.StateFlow
import mosis.streetsandtotems.ui.theme.overlay
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomLoader(showLoaderFlow: StateFlow<Boolean>) {
    if (showLoaderFlow.collectAsState().value)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(overlay)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { }
        ) {
            CircularProgressIndicator(
                strokeWidth = MaterialTheme.sizes.loader_stroke_width,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(MaterialTheme.sizes.loader_width)
                    .aspectRatio(MaterialTheme.sizes.default_aspect_ratio)
            )
        }
}
