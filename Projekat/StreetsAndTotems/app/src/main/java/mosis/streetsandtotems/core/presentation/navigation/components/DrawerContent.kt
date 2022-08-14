package mosis.streetsandtotems.core.presentation.navigation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GroupRemove
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import mosis.streetsandtotems.core.DrawerConstants
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.IconPosition
import mosis.streetsandtotems.destinations.LeaderboardsScreenDestination
import mosis.streetsandtotems.destinations.ProfileScreenDestination
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun DrawerContent(modifier: Modifier, destinationsNavigator: DestinationsNavigator) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxWidth(MaterialTheme.sizes.drawer_column_width)
            .padding(top = MaterialTheme.sizes.drawer_spacing)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) { focusManager.clearFocus() },
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.sizes.drawer_spacing)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.sizes.drawer_spacer)
        ) {
            Column(
                modifier = Modifier.height(MaterialTheme.sizes.drawer_image_and_text_size),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.sizes.none)
            ) {
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.sizes.drawer_image_size)
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(MaterialTheme.sizes.default_shape_corner)
                        )
                ) {
                }
                CustomButton(
                    clickHandler = { destinationsNavigator.navigate(ProfileScreenDestination) },
                    buttonType = CustomButtonType.Text,
                    text = DrawerConstants.PROFILE
                )
            }
            Column(
                modifier = Modifier.height(MaterialTheme.sizes.drawer_image_size),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "KorisnickoIme",
                    style = MaterialTheme.typography.titleLarge.plus(TextStyle(fontWeight = FontWeight.ExtraBold)),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Petar Petrovic",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleMedium.plus(TextStyle(fontWeight = FontWeight.Bold))
                )
            }

        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        DrawerIconText(
            text = DrawerConstants.SETTINGS,
            icon = Icons.Outlined.Settings,
            contentDescriptions = ImageContentDescriptionConstants.SETTINGS,
            iconModifier = Modifier.size(MaterialTheme.sizes.drawer_icon_size),
            textStyle = MaterialTheme.typography.titleLarge.plus(
                TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        DrawerSwitchItem(
            text = DrawerConstants.NOTIFIACTIONS,
            switchState = false,
            onCheckedChangeHandler = {})
        DrawerSwitchItem(
            text = DrawerConstants.RUN_IN_BACKGROUND,
            switchState = true,
            onCheckedChangeHandler = {})
        DrawerSwitchItem(
            text = DrawerConstants.SHOW_PHONE_NUMBER,
            switchState = false,
            onCheckedChangeHandler = {})
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        DrawerIconSelection(label = DrawerConstants.CALL_SETTINGS)
        DrawerIconSelection(label = DrawerConstants.SMS_SETTINGS)
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = MaterialTheme.sizes.drawer_last_divider_top_padding)
        )
        CustomButton(
            clickHandler = { destinationsNavigator.navigate(LeaderboardsScreenDestination) },
            text = DrawerConstants.LEADERBOARD,
            buttonType = CustomButtonType.Text,
            icon = Icons.Outlined.Leaderboard,
            iconSize = MaterialTheme.sizes.drawer_icon_size,
            textStyle = MaterialTheme.typography.titleMedium.plus(
                TextStyle(
                    fontWeight = FontWeight.Bold
                )
            ),
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.tertiary),
            iconContentDescription = ImageContentDescriptionConstants.LEADERBOARD
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = MaterialTheme.sizes.drawer_leave_buttons_spacing),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.sizes.drawer_leave_buttons_spacing),
                horizontalAlignment = Alignment.End
            ) {
                CustomButton(
                    clickHandler = { /*TODO*/ },
                    text = DrawerConstants.LEAVE_SQUAD,
                    buttonType = CustomButtonType.Text,
                    icon = Icons.Outlined.GroupRemove,
                    iconPosition = IconPosition.End,
                    iconSize = MaterialTheme.sizes.icon,
                    textStyle = MaterialTheme.typography.titleMedium.plus(
                        TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ),
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                )
                CustomButton(
                    clickHandler = { /*TODO*/ },
                    text = DrawerConstants.SIGN_OUT,
                    buttonType = CustomButtonType.Text,
                    icon = Icons.Outlined.Logout,
                    iconPosition = IconPosition.End,
                    iconSize = MaterialTheme.sizes.icon,
                    textStyle = MaterialTheme.typography.titleMedium.plus(
                        TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ),
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.tertiary)
                )
            }
        }
    }
}