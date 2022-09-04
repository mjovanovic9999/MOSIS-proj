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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.ContextCompat.startActivity
import com.skydoves.landscapist.glide.GlideImage
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun PlayerDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    isSquadMember: Boolean = false,
    tradeEnabled: Boolean = false,
    callsAllowed: Boolean? = false,
    messagingAllowed: Boolean? = false,
    phoneNumber: String? = null,
    firstName: String? = null,
    lastName: String? = null,
    userName: String? = null,
    image: Uri? = null,
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
                            text = (firstName ?: " ").first() + "" + (lastName ?: " ").first(),
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
                        text = userName ?: "",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = (firstName ?: " ") + (lastName ?: " "),
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
                        enabled = callsAllowed == true,
                        clickHandler = { callNumber(context, phoneNumber) },
                        icon = Icons.Outlined.Call,
                        buttonModifier = Modifier
                            .size(MaterialTheme.sizes.profile_dialog_row_height),
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                        iconModifier = Modifier.size(MaterialTheme.sizes.profile_dialog_icon_size)
                    )
                    CustomIconButton(
                        enabled = messagingAllowed == true,
                        clickHandler = { sendSms(context, phoneNumber) },
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
            ) {
                GlideImage(
                    imageModel = image,
                    modifier = Modifier.clip(RoundedCornerShape(MaterialTheme.sizes.default_shape_corner))
                )
            }
        },
        confirmButtonText = if (isSquadMember) ButtonConstants.KICK else ButtonConstants.INVITE_TO_SQUAD,
        confirmButtonMatchParentWidth = true,
        dismissButtonText = ButtonConstants.TRADE,
        dismissButtonVisible = tradeEnabled,
        dismissButtonMatchParentWidth = true,
        buttonType = CustomButtonType.Outlined,
    )
}

fun callNumber(context: Context, number: String?) {
    if (number != null) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        startActivity(context, intent, null)
    }
}

fun sendSms(context: Context, number: String?) {
    if (number != null) {
        val uri = Uri.parse("smsto:$number")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        startActivity(context, intent, null)
    }
}