package mosis.streetsandtotems.feature_totems.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import mosis.streetsandtotems.core.TotemItemsConstants
import mosis.streetsandtotems.core.presentation.components.CustomLazyColumnItem
import mosis.streetsandtotems.feature_map.domain.model.TotemData
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun TotemListItem(totem: TotemData, onButtonClick: () -> Unit) {
    CustomLazyColumnItem(onButtonClick = onButtonClick) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(MaterialTheme.sizes.totems_item_username_weight)
        ) {
            Text(
                text = TotemItemsConstants.PLACED_BY,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(text = totem.placed_by.toString(), style = MaterialTheme.typography.labelMedium)
        }
    }
}