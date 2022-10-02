package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

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
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.MessageConstants.GET_CLOSER
import mosis.streetsandtotems.core.presentation.components.CustomDialog
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomFarItemDialog(
    farItemDialogOpen: Boolean,
    onDismissRequest: () -> Unit,
    itemName: String = "",
    farResourceIconId: Int? = null,//ako je null to je totem
) {
    CustomDialog(
        isOpen = farItemDialogOpen,
        modifier = Modifier.height(115.dp),
        title = {
            CustomFarItemDialogTitle(
                resourceId = farResourceIconId,
                name = itemName,
            )
        },
        confirmButtonEnabled = false,
        dismissButtonEnabled = false,
        onDismissRequest = onDismissRequest,
    )
}


@Composable
private fun CustomFarItemDialogTitle(
    resourceId: Int? = null,
    name: String = "",
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
            if (resourceId == null) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.tiki),
                    contentDescription = ImageContentDescriptionConstants.TOTEM,
                    modifier = Modifier
                        .size(MaterialTheme.sizes.drop_item_dialog_icon_size)
                )
            } else {
                Icon(
                    imageVector = ImageVector.vectorResource(id = resourceId),
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
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = GET_CLOSER,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.End,
            )
        }
    }
}