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
import mosis.streetsandtotems.ui.theme.sizes

@MainNavGraph
@Destination
@Composable
fun BackpackScreen(viewModel: BackpackViewModel) {
    CustomPage(
        titleText = TitleConstants.BACKPACK,
        content = {
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
                        viewModel.openDropItemDialog(
                            5,
                            IconType.ResourceType.Emerald
                        )
                    },
                    onSecondItemClick = {
                        viewModel.openDropItemDialog(
                            5,
                            IconType.ResourceType.Wood
                        )
                    },
                    firstItemBadgeText = "5",
                    secondItemBadgeText = "10",
                    firstItemContentDescription = ImageContentDescriptionConstants.EMERALD,
                    secondItemContentDescription = ImageContentDescriptionConstants.WOOD
                )
                ItemRow(
                    firstItemImageVector = ImageVector.vectorResource(id = R.drawable.stone),
                    secondItemImageVector = ImageVector.vectorResource(id = R.drawable.brick),
                    onFirstItemClick = {
                        viewModel.openDropItemDialog(
                            5,
                            IconType.ResourceType.Stone
                        )
                    },
                    onSecondItemClick = {
                        viewModel.openDropItemDialog(
                            5,
                            IconType.ResourceType.Brick
                        )
                    },
                    firstItemBadgeText = "5",
                    secondItemBadgeText = "10",
                    firstItemContentDescription = ImageContentDescriptionConstants.STONE,
                    secondItemContentDescription = ImageContentDescriptionConstants.BRICKS
                )
            }
            FABWithBadge(
                modifier = Modifier.fillMaxWidth(MaterialTheme.sizes.backpack_totem_fab_weight),
                fabImageVector = ImageVector.vectorResource(id = R.drawable.tiki),
                onFabClick = { viewModel.openDropItemDialog(5, dropTotem = true) },
                fabShowAsImage = true,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                maxBadgeWidth = MaterialTheme.sizes.backpack_tiki_badge_size,
                badgeText = "3",
                iconModifier = Modifier.fillMaxSize(),
                contentDescription = ImageContentDescriptionConstants.TOTEM
            )
        })

    DropItemDialog(
        state = viewModel.dropItemDialogOpen.value,
        onDismissRequest = { viewModel.closeDropItemDialog() },
        onDrop = {},
    )

}
