package mosis.streetsandtotems.feature_backpack.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import mosis.streetsandtotems.ui.theme.sizes


@Composable
fun ItemRow(
    firstItemImageVector: ImageVector,
    secondItemImageVector: ImageVector,
    onFirstItemClick: () -> Unit,
    onSecondItemClick: () -> Unit,
    firstItemContentDescription: String? = null,
    secondItemContentDescription: String? = null,
    firstItemBadgeText: String = "",
    secondItemBadgeText: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        FABWithBadge(
            fabImageVector = firstItemImageVector,
            onFabClick = onFirstItemClick,
            modifier = Modifier.weight(MaterialTheme.sizes.backpack_item_fab_weight, false),
            contentDescription = firstItemContentDescription,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            iconModifier = Modifier.fillMaxSize(MaterialTheme.sizes.backpack_item_icon_size),
            badgeText = firstItemBadgeText
        )
        Spacer(modifier = Modifier.weight(MaterialTheme.sizes.backpack_spacer_weight))
        FABWithBadge(
            fabImageVector = secondItemImageVector,
            onFabClick = onSecondItemClick,
            modifier = Modifier.weight(MaterialTheme.sizes.backpack_item_fab_weight, false),
            contentDescription = secondItemContentDescription,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            iconModifier = Modifier.fillMaxSize(MaterialTheme.sizes.backpack_item_icon_size),
            badgeText = secondItemBadgeText
        )
    }
}
