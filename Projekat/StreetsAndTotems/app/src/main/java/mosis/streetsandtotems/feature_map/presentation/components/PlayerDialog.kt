package mosis.streetsandtotems.feature_map.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.CustomDialog
import mosis.streetsandtotems.core.presentation.components.CustomIconButton
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun PlayerDialog(isOpen: Boolean, onDismissRequest: () -> Unit) {
    CustomDialog(
        isOpen = isOpen,
        onDismissRequest = onDismissRequest,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(MaterialTheme.sizes.profile_dialog_initials_weight)) {
                    Box(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                CircleShape
                            )
                            .size(MaterialTheme.sizes.profile_dialog_row_height)
                            .align(Alignment.CenterStart),
                    ) {
                        Text(
                            text = "JS",
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(MaterialTheme.sizes.profile_dialog_username_and_name_weight)
                        .height(MaterialTheme.sizes.profile_dialog_row_height),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "AAAAAAAAAA",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Aleksandar Sokolovic",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Row(
                    modifier = Modifier.weight(MaterialTheme.sizes.profile_dialog_icon_buttons_weight),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    CustomIconButton(
                        clickHandler = { /*TODO*/ },
                        icon = Icons.Outlined.Call,
                        buttonModifier = Modifier
                            .size(MaterialTheme.sizes.profile_dialog_row_height),
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                        iconModifier = Modifier.size(MaterialTheme.sizes.profile_dialog_icon_size)
                    )
                    CustomIconButton(
                        clickHandler = { /*TODO*/ },
                        icon = Icons.Outlined.Message,
                        buttonModifier = Modifier
                            .size(MaterialTheme.sizes.profile_dialog_row_height),
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                        iconModifier = Modifier.size(MaterialTheme.sizes.profile_dialog_icon_size)
                    )
                }
            }
        },
        text = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(MaterialTheme.sizes.default_aspect_ratio)
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer,
                        RoundedCornerShape(MaterialTheme.sizes.default_shape_corner)
                    )
            )
        },
        confirmButtonText = ButtonConstants.KICK,
        confirmButtonMatchParentWidth = true,
        dismissButtonText = ButtonConstants.TRADE,
        dismissButtomMatchParentWidth = true,
        buttonType = CustomButtonType.Outlined
    )
}