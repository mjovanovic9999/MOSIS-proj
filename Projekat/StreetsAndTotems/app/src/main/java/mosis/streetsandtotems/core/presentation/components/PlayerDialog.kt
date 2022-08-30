package mosis.streetsandtotems.core.presentation.components

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.ContextCompat.startActivity
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.ui.theme.sizes


@Composable
fun PlayerDialog(
    isOpen: Boolean,//na true da se fetchuje broj username ime prezime
    onDismissRequest: () -> Unit,
    isSquadMember: Boolean = false,
    tradeEnabled: Boolean = false,
    isCallAllowed: Boolean? = false,
    isMessagingAllowed: Boolean? = false,
) {
    val context = LocalContext.current
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
                        enabled = isCallAllowed == false,
                        clickHandler = { callNumber(context, "0123456789") },//fetch number
                        icon = Icons.Outlined.Call,
                        buttonModifier = Modifier
                            .size(MaterialTheme.sizes.profile_dialog_row_height),
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                        iconModifier = Modifier.size(MaterialTheme.sizes.profile_dialog_icon_size)
                    )
                    CustomIconButton(
                        enabled = isMessagingAllowed == false,
                        clickHandler = { sendSms(context, "0123456789") },////fetch number
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
        confirmButtonText = if (isSquadMember) ButtonConstants.KICK else ButtonConstants.INVITE_TO_SQUAD,
        confirmButtonMatchParentWidth = true,
        dismissButtonText = ButtonConstants.TRADE,
        dismissButtonVisible = tradeEnabled,
        dismissButtomMatchParentWidth = true,
        buttonType = CustomButtonType.Outlined
    )
}

fun callNumber(context: Context, number: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$number")
    startActivity(context, intent, null)
}

fun sendSms(context: Context, number: String) {
    val uri = Uri.parse("smsto:$number")
    val intent = Intent(Intent.ACTION_SENDTO, uri)
    startActivity(context, intent, null)
}