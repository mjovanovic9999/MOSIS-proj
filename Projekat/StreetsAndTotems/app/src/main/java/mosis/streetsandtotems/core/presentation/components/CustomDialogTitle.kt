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
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.ItemsConstants
import mosis.streetsandtotems.ui.theme.sizes

sealed class IconType {
    sealed class ResourceType : IconType() {
        object Emerald : ResourceType()
        object Wood : ResourceType()
        object Stone : ResourceType()
        object Brick : ResourceType()
    }

    sealed class OtherType : IconType() {
        object Home : OtherType()
    }
}


@Composable
fun CustomDialogTitle(
    isTotem: Boolean = false,
    resourceType: IconType? = null,
    countMessage: String? = null
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
                    contentDescription = ImageContentDescriptionConstants.EDIT_PASSWORD,
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
                    IconType.ResourceType.Emerald -> ItemsConstants.EMERALD
                    IconType.ResourceType.Wood -> ItemsConstants.WOOD
                    IconType.ResourceType.Stone -> ItemsConstants.STONE
                    IconType.ResourceType.Brick -> ItemsConstants.BRICK
                    IconType.OtherType.Home -> TODO()
                },
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (countMessage != null)
                Text(
                    text = countMessage,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
        }
    }
}