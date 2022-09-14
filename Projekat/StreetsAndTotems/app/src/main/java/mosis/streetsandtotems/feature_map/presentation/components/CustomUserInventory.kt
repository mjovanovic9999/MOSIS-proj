package mosis.streetsandtotems.feature_map.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.core.ItemsConstants
import mosis.streetsandtotems.core.TitleConstants.BACKPACK_EMPTY_SPACE_AREA
import mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs.CustomAreaHelper

@Composable
fun CustomUserInventory(
    modifier: Modifier = Modifier,
    stoneCount: Int = 0,
    woodCount: Int = 0,
    brickCount: Int = 0,
    emeraldCount: Int = 0,
    emptySpaceCount: Int = 0,
    totemsCount: Int? = null,
    title: String,
) {
    CustomAreaHelper(
        modifier = modifier,
        title = { Text(text = title, style = MaterialTheme.typography.titleLarge) },
        cell00 = {
            Column {
                Text(
                    text = "${ItemsConstants.STONE}: $stoneCount",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${ItemsConstants.EMERALD}: $emeraldCount ",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (totemsCount != null)
                    Text(
                        text = "${ItemsConstants.TOTEM}: $totemsCount",
                        style = MaterialTheme.typography.bodyMedium
                    )
            }
        },
        cell01 = {
            Column(
                horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "${ItemsConstants.WOOD}: $woodCount",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${ItemsConstants.STONE}: $brickCount",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (totemsCount != null)
                    Text(
                        text = "",
                        style = MaterialTheme.typography.bodyMedium
                    )
            }
        },
        cell11 = {
            Text(
                text = "$BACKPACK_EMPTY_SPACE_AREA: $emptySpaceCount",
                style = MaterialTheme.typography.bodyMedium
            )
        },
    )
}