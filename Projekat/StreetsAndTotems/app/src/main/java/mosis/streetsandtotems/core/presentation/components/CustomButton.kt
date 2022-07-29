package mosis.streetsandtotems.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

enum class CustomButtonType {
    Filled,
    Outlined,
    Text,
}

enum class IconPosition {
    Start,
    End
}

@Composable
fun CustomButton(
    clickHandler: () -> Unit,
    text: String = "",
    buttonType: CustomButtonType = CustomButtonType.Filled,
    buttonModifier: Modifier = Modifier,
    colors: ButtonColors = if (buttonType == CustomButtonType.Filled) ButtonDefaults.buttonColors() else if (buttonType == CustomButtonType.Text) ButtonDefaults.textButtonColors() else ButtonDefaults.outlinedButtonColors(),
    border: BorderStroke = if (buttonType != CustomButtonType.Outlined) BorderStroke(
        0.dp,
        Color.Transparent
    ) else ButtonDefaults.outlinedButtonBorder,
    icon: ImageVector? = null,
    iconPosition: IconPosition = IconPosition.Start
) {
    when (buttonType) {
        CustomButtonType.Filled -> Button(
            onClick = clickHandler,
            modifier = buttonModifier,
            colors = colors,
            border = border
        ) {
            ButtonContent(text = text, icon = icon, iconPosition = iconPosition)
        }
        CustomButtonType.Outlined -> OutlinedButton(
            onClick = clickHandler,
            modifier = buttonModifier,
            colors = colors,
            border = border
        ) {
            ButtonContent(text = text, icon = icon, iconPosition = iconPosition)
        }
        CustomButtonType.Text -> TextButton(
            onClick = clickHandler,
            modifier = buttonModifier,
            colors = colors,
            border = border
        ) {
            ButtonContent(text = text, icon = icon, iconPosition = iconPosition)
        }
    }
}

@Composable
private fun ButtonContent(text: String, icon: ImageVector?, iconPosition: IconPosition) {
    if (icon != null && iconPosition == IconPosition.Start) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
    }
    Text(text = text)
    if (icon != null && iconPosition == IconPosition.End) {
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
    }
}
