package mosis.streetsandtotems.feature_auth.presentation.signup

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
import mosis.streetsandtotems.core.domain.validators.validatePhoneNumber
import mosis.streetsandtotems.core.presentation.components.CustomTextFieldType
import mosis.streetsandtotems.core.presentation.components.form.formfields.TextFormField
import mosis.streetsandtotems.core.presentation.states.FormState
import mosis.streetsandtotems.feature_auth.domain.model.SignUpFields
import mosis.streetsandtotems.feature_auth.domain.model.SignUpFieldsEmpty
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor() : ViewModel() {
    val formState: FormState<SignUpFields> = FormState(
        fields = listOf(
            TextFormField(
                initial = "",
                name = FormFieldNamesConstants.FIRST_NAME,
                validators = listOf(Validators.Required(MessageConstants.FIRST_NAME_REQUIRED)),
                label = FormFieldConstants.FIRST_NAME,
                placeholder = FormFieldConstants.FIRST_NAME,
                textFieldType = CustomTextFieldType.Outlined,
                singleLine = true,
                clearable = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            ),
            TextFormField(
                initial = "",
                name = FormFieldNamesConstants.LAST_NAME,
                validators = listOf(Validators.Required(MessageConstants.LAST_NAME_REQUIRED)),
                label = FormFieldConstants.LAST_NAME,
                placeholder = FormFieldConstants.LAST_NAME,
                textFieldType = CustomTextFieldType.Outlined,
                singleLine = true,
                clearable = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            ),
            TextFormField(
                initial = "",
                name = FormFieldNamesConstants.PHONE_NUMBER,
                validators = listOf(
                    Validators.Custom(
                        MessageConstants.INVALID_PHONE_NUMBER,
                        ::validatePhoneNumber
                    ), Validators.Required(MessageConstants.PHONE_NUMBER_REQUIRED)
                ),
                label = FormFieldConstants.PHONE_NUMBER,
                placeholder = FormFieldConstants.PHONE_NUMBER,
                textFieldType = CustomTextFieldType.Outlined,
                singleLine = true,
                clearable = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            ),
            TextFormField(
                initial = "",
                name = FormFieldNamesConstants.USERNAME,
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
                name = FormFieldNamesConstants.PASSWORD,
                validators = listOf(
                    Validators.Required(MessageConstants.PASSWORD_REQUIRED),
                    Validators.Min(8, MessageConstants.PASSWORD_LENGTH)
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
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
            ),
            TextFormField(
                initial = "",
                name = FormFieldNamesConstants.REPEAT_PASSWORD,
                validators = listOf(
                    Validators.Custom(
                        MessageConstants.PASSWORDS_DO_NOT_MATCH,
                        ::validateRepeatedPassword
                    ),
                    Validators.Min(
                        FormFieldLengthConstants.PASSWORD,
                        MessageConstants.PASSWORD_LENGTH
                    ),
                    Validators.Required(MessageConstants.REPEAT_PASSWORD_REQUIRED),
                ),
                label = FormFieldConstants.REPEAT_PASSWORD,
                placeholder = FormFieldConstants.REPEAT_PASSWORD,
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
                keyboardActions = KeyboardActions(onDone = { validate() }),
            )
        ),
        SignUpFields::class,
        SignUpFieldsEmpty()
    )

    private fun validate() {
        formState.validate()
    }

    fun submit() {
        formState.getDataWithValidation()
    }

    private fun validateRepeatedPassword(repeatedPassword: Any): Boolean {
        return formState.getData().password == repeatedPassword.toString()
    }
}


