package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomAreaHelper(
    modifier: Modifier = Modifier,
    isTransparent: Boolean = false,
    title: (@Composable() () -> Unit)? = null,
    row00: (@Composable() () -> Unit)? = null,//ako treba samo jedno polje koristiti ovo
    row01: (@Composable() () -> Unit)? = null,
    row10: (@Composable() () -> Unit)? = null,
    row11: (@Composable() () -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(MaterialTheme.sizes.default_shape_corner))
            .background(
                if (isTransparent)
                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.9f)
                else
                    MaterialTheme.colorScheme.secondaryContainer//tako li boja??????
            )
    ) {
        Column(
//            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)) {
                if (title != null) {
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
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (row00 != null) {
                        row00()
                    }
                    if (row01 != null) {
                        row01()
                    }
                }
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (row10 != null) {
                        row10()
                    }
                    if (row11 != null) {
                        row11()
                    }
                }
            }
        }
    }
}