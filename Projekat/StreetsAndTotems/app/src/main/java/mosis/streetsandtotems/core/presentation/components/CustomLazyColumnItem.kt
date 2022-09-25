package mosis.streetsandtotems.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomLazyColumnItem(
    onButtonClick: () -> Unit,
    buttonIcon: ImageVector = Icons.Filled.MoreVert,
    content: @Composable RowScope.() -> Unit,
) {
    Box(modifier = Modifier.padding(bottom = MaterialTheme.sizes.lazy_column_spacing)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(MaterialTheme.sizes.lazy_column_width)
                .background(
                    MaterialTheme.colorScheme.secondaryContainer,
                    RoundedCornerShape(MaterialTheme.sizes.default_shape_corner)
                ), verticalAlignment = Alignment.CenterVertically
        ) {
            content()

            CustomIconButton(
                clickHandler = onButtonClick,
                icon = buttonIcon,
                buttonModifier = Modifier.weight(MaterialTheme.sizes.lazy_column_button_weight),
                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onSecondaryContainer)
            )
        }
    }
}