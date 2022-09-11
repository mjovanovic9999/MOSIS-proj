package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomAreaHelper(
    modifier: Modifier = Modifier,
    isTransparent: Boolean = false,
    title: (@Composable() () -> Unit)? = null,
    cell00: (@Composable() () -> Unit)? = null,//ako treba samo jedno polje koristiti ovo!!!
    cell01: (@Composable() () -> Unit)? = null,
    cell10: (@Composable() () -> Unit)? = null,
    cell11: (@Composable() () -> Unit)? = null,
    shouldAlignBottom: Boolean = false,
    topPadding: Boolean = false,
    backgroundColor: Color=MaterialTheme.colorScheme.secondaryContainer,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(MaterialTheme.sizes.default_shape_corner))
            .background(
                if (isTransparent)
                    backgroundColor.copy(alpha = 0.9f)
                else
                    backgroundColor
            )
            .padding(5.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (title != null) {
                Box(if (topPadding) Modifier.padding(top = 5.dp) else Modifier) {
                    title()
                }
            }
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (cell00 != null) {
                        cell00()
                    }
                    if (cell01 != null) {
                        cell01()
                    }
                }
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = if (shouldAlignBottom) Alignment.Bottom else Alignment.CenterVertically,
                ) {
                    if (cell10 != null) {
                        cell10()
                    }
                    if (cell11 != null) {
                        cell11()
                    }
                }
            }
        }
    }
}