package mosis.streetsandtotems.feature_auth.presentation.signin

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.lifecycle.ViewModel
import com.dsc.form_builder.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
import mosis.streetsandtotems.core.*
import mosis.streetsandtotems.core.presentation.components.CustomTextFieldType
import mosis.streetsandtotems.core.presentation.components.form.formfields.TextFormField
import mosis.streetsandtotems.core.presentation.states.FieldsEmpty
import mosis.streetsandtotems.core.presentation.states.FormState
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    val formState: FormState<SignInFields> = FormState(
        fields = listOf(
            TextFormField(
                initial = "",
                name = FormFieldNamesConstants.USERNAME,
                validators = listOf(Validators.Required(MessageConstants.USERNAME_REQUIRED)),
                label = FormFieldConstants.USERNAME,
                placeholder = FormFieldConstants.USERNAME,
                textFieldType = CustomTextFieldType.Outlined,
                singleLine = true,
                clearable = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            ),
            TextFormField(
                initial = "",
                name = FormFieldNamesConstants.PASSWORD,
                validators = listOf(
                    Validators.Min(
                        FormFieldLengthConstants.PASSWORD, MessageConstants.PASSWORD_LENGTH
                    ),
                    Validators.Required(MessageConstants.PASSWORD_REQUIRED),
                ),
                label = FormFieldConstants.PASSWORD,
                placeholder = FormFieldConstants.PASSWORD,
                textFieldType = CustomTextFieldType.Outlined,
                singleLine = true,
                clearable = true,
                visualTransformation = { text ->
                    TransformedText(
                        AnnotatedString(VisualTransformationConstants.PASSWORD.repeat(text.length)),
                        OffsetMapping.Identity
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(onDone = { submit() }),

                )
        ),
        SignInFields::class,
        SignInFieldsEmpty()
    )

    fun submit() {
        formState.getDataWithValidation()
    }


}

 data class SignInFieldsEmpty(
    var username: Boolean = true,
    var password: Boolean = true
) : FieldsEmpty {
    override fun setFieldEmpty(name: String, empty: Boolean) {
        when (name) {
            FormFieldNamesConstants.USERNAME -> {
                username = empty
            }
            FormFieldNamesConstants.PASSWORD -> {
                password = empty
            }
        }
    }

    override fun anyFieldIsEmpty(): Boolean {
        return username || password
    }
}

data class SignInFields(
    val username: String,
    val password: String
)