package mosis.streetsandtotems.feature_auth.presentation.signin

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.*
import androidx.lifecycle.ViewModel
import com.dsc.form_builder.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
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
                label = "Username",
                placeholder = "Username",
                textFieldType = CustomTextFieldType.Outlined,
                singleLine = true,
                clearable = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            ),
            TextFormField(
                initial = "",
                name = "password",
                validators = listOf(Validators.Required(MessageConstants.PASSWORD_REQUIRED)),
                label = "Password",
                placeholder = "Password",
                textFieldType = CustomTextFieldType.Outlined,
                singleLine = true,
                clearable = true,
                visualTransformation = VisualTransformation { text -> TransformedText(
                    AnnotatedString(VisualTransformationConstants.PASSWORD.repeat(text.length)),
                    OffsetMapping.Identity
                )},
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(onDone = {submit()})
            )
        ),
        Credentials::class
    )

    fun submit() {
        if (formState.validate()) {
            val data = formState.getData()
        }
    }

}


data class Credentials(
    val username: String,
    val password: String
)