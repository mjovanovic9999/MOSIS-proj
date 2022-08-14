package mosis.streetsandtotems.feature_totems.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import mosis.streetsandtotems.core.TotemItemsConstants
import mosis.streetsandtotems.core.presentation.components.CustomIconButton
import mosis.streetsandtotems.feature_totems.domain.model.Totem
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun TotemListItem(totem: Totem) {
    Row(
        modifier = Modifier
            .fillMaxWidth(MaterialTheme.sizes.lazy_column_width)
            .background(
                MaterialTheme.colorScheme.secondaryContainer,
                RoundedCornerShape(MaterialTheme.sizes.default_shape_corner)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(MaterialTheme.sizes.totems_item_totem_id_weight)
        ) {
            Text(
                text = TotemItemsConstants.TOTEM_ID,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(text = totem.id, style = MaterialTheme.typography.labelMedium)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(MaterialTheme.sizes.totems_item_username_weight)
        ) {
            Text(
                text = TotemItemsConstants.PLACED_BY,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(text = totem.placedBy, style = MaterialTheme.typography.labelMedium)
        }

        CustomIconButton(
            clickHandler = { /*TODO*/ },
            icon = Icons.Filled.MoreVert,
            buttonModifier = Modifier.weight(MaterialTheme.sizes.lazy_column_button_weight)
        )
    }
}