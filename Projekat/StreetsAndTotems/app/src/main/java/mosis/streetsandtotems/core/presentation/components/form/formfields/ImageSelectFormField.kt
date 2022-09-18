package mosis.streetsandtotems.core.presentation.components.form.formfields

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.core.presentation.components.CustomImageSelectorAndCropper

class ImageSelectFormField(
    initial: String,
    name: String,
    validators: List<Validators> = emptyList()
) : FormField() {
    override val fieldState: TextFieldState =
        TextFieldState(name = name, initial = initial, validators = validators)

    @Composable
    override fun Content() {
        CustomImageSelectorAndCropper(
            focusRequester = focusRequester,
            onImageSelected = {
                fieldState.change(it)
                onValueChanged.invoke(
                    it
                )
            })
        Text(
            text = if (fieldState.hasError) fieldState.errorMessage else if (fieldState.value != "") FormFieldConstants.CHANGE_PHOTO else "",
            style = MaterialTheme.typography.labelSmall.plus(
                TextStyle(if (fieldState.hasError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary)
            )
        )
    }
}