package mosis.streetsandtotems.core.presentation.navigation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun DrawerIconText(
    text: String,
    icon: ImageVector,
    contentDescriptions: String,
    iconModifier: Modifier,
    tint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescriptions,
            modifier = iconModifier,
            tint = tint
        )
        Spacer(modifier = Modifier.width(MaterialTheme.sizes.drawer_spacer))
        Text(text = text, style = textStyle, color = tint)
    }
}