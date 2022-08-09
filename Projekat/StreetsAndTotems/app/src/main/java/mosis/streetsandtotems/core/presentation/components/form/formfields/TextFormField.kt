package mosis.streetsandtotems.core.presentation.components.form.formfields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Transform
import com.dsc.form_builder.Validators
import mosis.streetsandtotems.core.presentation.components.CustomTextField
import mosis.streetsandtotems.core.presentation.components.CustomTextFieldType

class TextFormField(
    initial: String,
    name: String,
    validators: List<Validators> = emptyList(),
    transform: Transform<String>? = null,
    private val textFieldType: CustomTextFieldType = CustomTextFieldType.Basic,
    private val modifier: Modifier = Modifier.fillMaxWidth(),
    private val enabled: Boolean = true,
    private val readOnly: Boolean = false,
    private val label: String = "",
    private val placeholder: String = "",
    private val clearable: Boolean = true,
    private val leadingIcon: ImageVector? = null,
    private val trailingIcon: ImageVector? = if (clearable) {
        if (textFieldType == CustomTextFieldType.Basic) Icons.Filled.Clear else Icons.Outlined.Cancel
    } else null,
    private val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    private val keyboardActions: KeyboardActions = KeyboardActions(),
    private val singleLine: Boolean = false,
    private val maxLines: Int = Int.MAX_VALUE,
    private val visualTransformation: VisualTransformation = VisualTransformation.None
) : FormField {
    override val fieldState: TextFieldState = TextFieldState(
        name = name,
        initial = initial,
        validators = validators,
        transform = transform
    )

    @Composable
    override fun Content() {
        Column() {
            CustomTextField(
                value = fieldState.value,
                onValueChange = { fieldState.change(it) },
                isError = fieldState.hasError,
                textFieldType = textFieldType,
                modifier = modifier,
                enabled = enabled,
                readOnly = readOnly,
                label = label,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = if(clearable && fieldState.value != "") trailingIcon else null,
                onTrailingIconClicked = { fieldState.change("") },
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                visualTransformation = visualTransformation
            )
            Text(
                text = if(fieldState.hasError) fieldState.errorMessage else "",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Left
            )
        }
    }
}