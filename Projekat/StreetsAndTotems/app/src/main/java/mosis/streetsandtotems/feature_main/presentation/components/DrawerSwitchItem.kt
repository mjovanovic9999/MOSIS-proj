package mosis.streetsandtotems.feature_main.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun DrawerSwitchItem(
    text: String,
    switchState: Boolean,
    onCheckedChangeHandler: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DrawerIconText(
            text = text,
            icon = Icons.Filled.Circle,
            contentDescriptions = ImageContentDescriptionConstants.BULLET,
            iconModifier = Modifier.size(MaterialTheme.sizes.drawer_bullet_size)
        )
        Switch(
            checked = switchState,
            onCheckedChange = onCheckedChangeHandler,
            modifier = Modifier.scale(MaterialTheme.sizes.drawer_switch_scale_factor)
        )
    }
}
