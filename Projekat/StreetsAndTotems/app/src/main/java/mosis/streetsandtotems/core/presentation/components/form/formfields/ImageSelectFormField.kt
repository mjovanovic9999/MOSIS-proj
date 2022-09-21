package mosis.streetsandtotems.core.presentation.components.form.formfields

import android.net.Uri
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.skydoves.landscapist.glide.GlideImage
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.core.presentation.components.CustomImageSelectorAndCropper
import mosis.streetsandtotems.ui.theme.sizes

class ImageSelectFormField(
    initial: String,
    name: String,
    validators: List<Validators> = emptyList(),
    val modifier: Modifier? = null,
    val editable: Boolean = true
) : FormField() {
    override val fieldState: TextFieldState =
        TextFieldState(name = name, initial = initial, validators = validators)

    @Composable
    override fun Content() {
        if (editable) {
            CustomImageSelectorAndCropper(initialImageUri = Uri.parse(fieldState.value),
                modifier = modifier ?: Modifier.size(MaterialTheme.sizes.image_select_size),
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
        } else GlideImage(
            imageModel = Uri.parse(fieldState.value),
            modifier?.clip(RoundedCornerShape(MaterialTheme.sizes.default_shape_corner))
                ?: Modifier
                    .size(MaterialTheme.sizes.image_select_size)
                    .clip(RoundedCornerShape(MaterialTheme.sizes.default_shape_corner))
        )
    }
}