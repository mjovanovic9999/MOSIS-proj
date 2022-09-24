package mosis.streetsandtotems.feature_backpack.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.ramcosta.composedestinations.annotation.Destination
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.CustomPage
import mosis.streetsandtotems.core.presentation.components.IconType
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.MainNavGraph
import mosis.streetsandtotems.feature_backpack.presentation.components.DropItemDialog
import mosis.streetsandtotems.feature_backpack.presentation.components.FABWithBadge
import mosis.streetsandtotems.feature_backpack.presentation.components.ItemRow
import mosis.streetsandtotems.feature_map.domain.model.ResourceType
import mosis.streetsandtotems.ui.theme.sizes

@MainNavGraph
@Destination
@Composable
fun BackpackScreen(backpackViewModel: BackpackViewModel) {
    val state = backpackViewModel.backpackScreenState.value

    CustomPage(titleText = TitleConstants.BACKPACK, content = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(MaterialTheme.sizes.default_aspect_ratio),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ItemRow(
                firstItemImageVector = ImageVector.vectorResource(id = R.drawable.emerald),
                secondItemImageVector = ImageVector.vectorResource(id = R.drawable.wood),
                onFirstItemClick = {
                    backpackViewModel.openDropItemDialog(
                        state.useInventoryData.inventory?.emerald ?: 0,
                        IconType.ResourceType.Emerald
                    )
                },
                onSecondItemClick = {
                    backpackViewModel.openDropItemDialog(
                        state.useInventoryData.inventory?.wood ?: 0, IconType.ResourceType.Wood
                    )
                },
                firstItemBadgeText = (state.useInventoryData.inventory?.emerald ?: 0).toString(),
                secondItemBadgeText = (state.useInventoryData.inventory?.wood ?: 0).toString(),
                firstItemContentDescription = ImageContentDescriptionConstants.EMERALD,
                secondItemContentDescription = ImageContentDescriptionConstants.WOOD
            )
            ItemRow(
                firstItemImageVector = ImageVector.vectorResource(id = R.drawable.stone),
                secondItemImageVector = ImageVector.vectorResource(id = R.drawable.brick),
                onFirstItemClick = {
                    backpackViewModel.openDropItemDialog(
                        state.useInventoryData.inventory?.stone ?: 0, IconType.ResourceType.Stone
                    )
                },
                onSecondItemClick = {
                    backpackViewModel.openDropItemDialog(
                        state.useInventoryData.inventory?.brick ?: 0, IconType.ResourceType.Brick
                    )
                },
                firstItemBadgeText = (state.useInventoryData.inventory?.stone ?: 0).toString(),
                secondItemBadgeText = (state.useInventoryData.inventory?.brick ?: 0).toString(),
                firstItemContentDescription = ImageContentDescriptionConstants.STONE,
                secondItemContentDescription = ImageContentDescriptionConstants.BRICKS
            )
        }
        FABWithBadge(
            modifier = Modifier.fillMaxWidth(MaterialTheme.sizes.backpack_totem_fab_weight),
            fabImageVector = ImageVector.vectorResource(id = R.drawable.tiki),
            onFabClick = {
                backpackViewModel.openDropItemDialog(
                    state.useInventoryData.inventory?.totem ?: 0, dropTotem = true
                )
            },
            fabShowAsImage = true,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            maxBadgeWidth = MaterialTheme.sizes.backpack_tiki_badge_size,
            badgeText = (state.useInventoryData.inventory?.totem ?: 0).toString(),
            iconModifier = Modifier.fillMaxSize(),
            contentDescription = ImageContentDescriptionConstants.TOTEM
        )
    })

    DropItemDialog(
        state = state.dropItemDialogState,
        onDismissRequest = { backpackViewModel.closeDropItemDialog() },
        onDrop = { dropItemCount: Int, type: IconType.ResourceType? ->
            backpackViewModel.onEvent(
                BackpackViewModelEvents.DropResource(
                    dropItemCount, type
                )
            )
        },
        isDropEnabled = true,//zasto ovo ovako???
//        takeAmount.value != ""
//                && takeAmount.value.toInt() > 0
//                && backpackItemCount >= takeAmount.value.toInt()
    )

}
