package mosis.streetsandtotems.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.ui.theme.sizes

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
    matchParentWidth: Boolean = false,
    clickHandler: () -> Unit,
    text: String = "",
    buttonType: CustomButtonType = CustomButtonType.Filled,
    buttonModifier: Modifier = if (matchParentWidth) Modifier.fillMaxWidth() else Modifier,
    colors: ButtonColors = if (buttonType == CustomButtonType.Filled) ButtonDefaults.buttonColors() else if (buttonType == CustomButtonType.Text) ButtonDefaults.textButtonColors() else ButtonDefaults.outlinedButtonColors(),
    border: BorderStroke = if (buttonType != CustomButtonType.Outlined) BorderStroke(
        MaterialTheme.sizes.none,
        Color.Transparent
    ) else ButtonDefaults.outlinedButtonBorder,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    iconPosition: IconPosition = IconPosition.Start,
    iconSize: Dp = ButtonDefaults.IconSize,
    textStyle: TextStyle = TextStyle.Default,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding
) {
    when (buttonType) {
        CustomButtonType.Filled -> Button(
            onClick = clickHandler,
            modifier = buttonModifier,
            colors = colors,
            border = border,
            enabled = enabled,
            contentPadding = contentPadding
        ) {
            ButtonContent(
                text = text,
                icon = icon,
                iconPosition = iconPosition,
                iconSize,
                textStyle,
                if (matchParentWidth) Modifier.fillMaxWidth() else Modifier
            )
        }
        CustomButtonType.Outlined -> OutlinedButton(
            onClick = clickHandler,
            modifier = buttonModifier,
            colors = colors,
            border = border,
            enabled = enabled,
            contentPadding = contentPadding
        ) {
            ButtonContent(
                text = text,
                icon = icon,
                iconPosition = iconPosition,
                iconSize,
                textStyle,
                if (matchParentWidth) Modifier.fillMaxWidth() else Modifier
            )
        }
        CustomButtonType.Text -> TextButton(
            contentPadding = if (contentPadding != ButtonDefaults.ContentPadding) contentPadding else PaddingValues(
                top = MaterialTheme.sizes.none,
                bottom = MaterialTheme.sizes.none,
                start = MaterialTheme.sizes.text_button_side_padding,
                end = MaterialTheme.sizes.text_button_side_padding
            ),
            onClick = clickHandler,
            modifier = buttonModifier.defaultMinSize(
                ButtonDefaults.MinWidth,
                MaterialTheme.sizes.text_button_min_heigth
            ),
            colors = colors,
            border = border,
            enabled = enabled
        ) {
            ButtonContent(
                text = text,
                icon = icon,
                iconPosition = iconPosition,
                iconSize,
                textStyle,
                if (matchParentWidth) Modifier.fillMaxWidth() else Modifier
            )
        }
    }
}

@Composable
private fun ButtonContent(
    text: String,
    icon: ImageVector?,
    iconPosition: IconPosition,
    iconSize: Dp,
    textStyle: TextStyle,
    textModifier: Modifier,
) {
    if (icon != null && iconPosition == IconPosition.Start) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
    }
    Text(
        text = text,
        style = textStyle,
        textAlign = TextAlign.Center,
        modifier = textModifier
    )
    if (icon != null && iconPosition == IconPosition.End) {
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
fun CustomIconButton(
    clickHandler: () -> Unit,
    buttonModifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    IconButton(onClick = clickHandler, modifier = buttonModifier, enabled = enabled) {
        icon?.let { Icon(imageVector = it, contentDescription = "") }
    }
}