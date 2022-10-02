package mosis.streetsandtotems.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.ItemsConstants
import mosis.streetsandtotems.core.PinConstants.HOME
import mosis.streetsandtotems.core.PointsConversion
import mosis.streetsandtotems.ui.theme.sizes

sealed class IconType {
    sealed class ResourceType(val additionalText: String) : IconType() {
        object Emerald : ResourceType(" (${PointsConversion.EMERALD}pts)")
        object Wood : ResourceType(" (${PointsConversion.WOOD}pts)")
        object Stone : ResourceType(" (${PointsConversion.STONE}pts)")
        object Brick : ResourceType(" (${PointsConversion.BRICK}pts)")
    }

    sealed class OtherType : IconType() {
        object Home : OtherType()
    }
}


@Composable
fun CustomDialogTitle(
    isTotem: Boolean = false,
    resourceType: IconType? = null,
    countMessage: String? = null,
    customMessage: String? = null,
    needTotemAdditionalText: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.tertiaryContainer,
                    CircleShape
                )
        ) {
            if (isTotem) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.tiki),
                    contentDescription = ImageContentDescriptionConstants.TOTEM,
                    modifier = Modifier
                        .size(MaterialTheme.sizes.drop_item_dialog_icon_size)
                )
            } else if (resourceType != null) {
                Icon(
                    imageVector = when (resourceType) {
                        IconType.ResourceType.Emerald -> ImageVector.vectorResource(id = R.drawable.emerald)
                        IconType.ResourceType.Wood -> ImageVector.vectorResource(id = R.drawable.wood)
                        IconType.ResourceType.Stone -> ImageVector.vectorResource(id = R.drawable.stone)
                        IconType.ResourceType.Brick -> ImageVector.vectorResource(id = R.drawable.brick)
                        IconType.OtherType.Home -> ImageVector.vectorResource(id = R.drawable.home)
                    },
                    contentDescription = null,
                    modifier = Modifier
                        .size(MaterialTheme.sizes.drop_item_dialog_icon_size)
                        .padding(MaterialTheme.sizes.drop_item_dialog_icon_padding),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (isTotem) {
                Text(
                    text = ItemsConstants.TOTEM,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else if (resourceType != null) Text(
                text = when (resourceType) {
                    IconType.ResourceType.Emerald -> ItemsConstants.EMERALD + if (needTotemAdditionalText) IconType.ResourceType.Emerald.additionalText else ""
                    IconType.ResourceType.Wood -> ItemsConstants.WOOD + if (needTotemAdditionalText) IconType.ResourceType.Wood.additionalText else ""
                    IconType.ResourceType.Stone -> ItemsConstants.STONE + if (needTotemAdditionalText) IconType.ResourceType.Stone.additionalText else ""
                    IconType.ResourceType.Brick -> ItemsConstants.BRICK + if (needTotemAdditionalText) IconType.ResourceType.Brick.additionalText else ""
                    IconType.OtherType.Home -> HOME
                },
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (countMessage != null)
                    Text(
                        text = countMessage,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )
                if (customMessage != null)
                    Text(
                        text = customMessage,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.End,
                    )
            }
        }
    }
}