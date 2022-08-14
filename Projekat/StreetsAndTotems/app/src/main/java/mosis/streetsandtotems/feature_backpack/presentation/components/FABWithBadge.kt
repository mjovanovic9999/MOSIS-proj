package mosis.streetsandtotems.feature_backpack.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import mosis.streetsandtotems.core.presentation.components.CustomFAB
import mosis.streetsandtotems.ui.theme.sizes


@Composable
fun FABWithBadge(
    fabImageVector: ImageVector,
    onFabClick: () -> Unit,
    fabShowAsImage: Boolean = false,
    modifier: Modifier = Modifier,
    badgeText: String = "",
    contentDescription: String? = null,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    iconModifier: Modifier = Modifier,
    maxBadgeWidth: Float = MaterialTheme.sizes.backpack_item_badge_size,
) {
    Box(
        modifier = modifier
            .aspectRatio(MaterialTheme.sizes.default_aspect_ratio)
    ) {
        CustomFAB(
            imageVector = fabImageVector,
            onClick = onFabClick,
            modifier = Modifier.matchParentSize(),
            showAsImage = fabShowAsImage,
            contentDescription = contentDescription,
            containerColor = containerColor,
            iconModifier = iconModifier
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth(maxBadgeWidth)
                .aspectRatio(MaterialTheme.sizes.default_aspect_ratio)
                .padding(MaterialTheme.sizes.backpack_badge_padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .align(Alignment.Center)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = badgeText,
                    style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
                )
            }
        }
    }
}