package mosis.streetsandtotems.feature_map.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.core.ItemsConstants
import mosis.streetsandtotems.core.TitleConstants.BACKPACK_EMPTY_SPACE_AREA
import mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs.CustomAreaHelper
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomBackpackInventory(
    modifier: Modifier = Modifier,
    stoneCount: Int = 0,
    woodCount: Int = 0,
    brickCount: Int = 0,
    emeraldCount: Int = 0,
    backpackEmptySpaceCount: Int = 0,
) {
    CustomAreaHelper(
        modifier = modifier,
        title = { Text(text = "Backpack inventory", style = MaterialTheme.typography.titleLarge) },
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
            }
        },
        cell01 = {
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${ItemsConstants.WOOD}: $woodCount",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${ItemsConstants.STONE}: $brickCount",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        cell11 = {
            Text(
                text = "$BACKPACK_EMPTY_SPACE_AREA: $backpackEmptySpaceCount",
                style = MaterialTheme.typography.bodyMedium
            )
        },
//        backgroundColor = MaterialTheme.colorScheme.secondary,
//        backgroundColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.4f),

    )
}