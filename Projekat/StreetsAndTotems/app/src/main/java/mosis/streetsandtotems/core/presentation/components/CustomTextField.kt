package mosis.streetsandtotems.core.presentation.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation

enum class CustomTextFieldType {
    Basic,
    Outlined
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    textFieldType: CustomTextFieldType = CustomTextFieldType.Basic,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: String = "",
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClicked: (() -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    colors: TextFieldColors = if(textFieldType == CustomTextFieldType.Basic) TextFieldDefaults.textFieldColors() else TextFieldDefaults.outlinedTextFieldColors()
) {
    when (textFieldType) {
        CustomTextFieldType.Basic -> {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                enabled = enabled,
                readOnly = readOnly,
                label = { Text(label) },
                placeholder = { Text(placeholder) },
                leadingIcon = textFieldIcon(leadingIcon),
                trailingIcon = textFieldIcon(trailingIcon, onTrailingIconClicked),
                isError = isError,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                visualTransformation = visualTransformation,
                colors = colors
            )
        }
        CustomTextFieldType.Outlined -> {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                enabled = enabled,
                readOnly = readOnly,
                label = { Text(label) },
                placeholder = { Text(placeholder) },
                leadingIcon = textFieldIcon(leadingIcon),
                trailingIcon = textFieldIcon(trailingIcon, onTrailingIconClicked),
                isError = isError,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                visualTransformation = visualTransformation,
                colors = colors
            )
        }
    }
}

fun textFieldIcon(
    icon: ImageVector? = null,
    onIconClicked: (() -> Unit)? = null
): @Composable (() -> Unit)? {
    if (icon != null) {
        if (onIconClicked != null) {
            return { CustomIconButton(clickHandler = onIconClicked, icon = icon) }
        } else return { Icon(imageVector = icon, contentDescription = "") }
    } else return null
}