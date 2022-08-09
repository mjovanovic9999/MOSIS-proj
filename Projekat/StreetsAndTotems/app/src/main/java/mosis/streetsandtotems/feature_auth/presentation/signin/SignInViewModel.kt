package mosis.streetsandtotems.feature_auth.presentation.signin

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.*
import androidx.lifecycle.ViewModel
import com.dsc.form_builder.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.core.FormFieldLenghtConstants
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.core.VisualTransformationConstants
import mosis.streetsandtotems.core.presentation.components.CustomTextFieldType
import mosis.streetsandtotems.core.presentation.components.form.formfields.TextFormField
import mosis.streetsandtotems.core.presentation.states.FormState
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    val formState = FormState(
        fields = listOf(
            TextFormField(
                initial = "",
                name = "username",
                validators = listOf(Validators.Required(MessageConstants.USERNAME_REQUIRED)),
                label = FormFieldConstants.USERNAME,
                placeholder = FormFieldConstants.USERNAME,
                textFieldType = CustomTextFieldType.Outlined,
                singleLine = true,
                clearable = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            ),
            TextFormField(
                initial = "",
                name = "password",
                validators = listOf(
                    Validators.Min(
                        FormFieldLenghtConstants.PASSWORD, MessageConstants.PASSWORD_LENGTH
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
                keyboardActions = KeyboardActions(onDone = { submit() })
            )
        ),
        Credentials::class
    )

    fun submit() {
        formState.getDataWithValidation()
    }

}


data class Credentials(
    val username: String,
    val password: String
)