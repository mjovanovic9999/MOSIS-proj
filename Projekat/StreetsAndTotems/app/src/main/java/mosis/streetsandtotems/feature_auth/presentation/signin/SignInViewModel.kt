package mosis.streetsandtotems.feature_auth.presentation.signin

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsc.form_builder.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import mosis.streetsandtotems.core.*
import mosis.streetsandtotems.core.domain.model.SnackbarSettings
import mosis.streetsandtotems.core.presentation.components.CustomTextFieldType
import mosis.streetsandtotems.core.presentation.components.form.formfields.TextFormField
import mosis.streetsandtotems.core.presentation.states.FormState
import mosis.streetsandtotems.feature_auth.domain.use_case.AuthUseCases
import mosis.streetsandtotems.feature_auth.presentation.util.SignInFields
import mosis.streetsandtotems.feature_auth.presentation.util.SignInFieldsEmpty
import mosis.streetsandtotems.feature_auth.presentation.util.handleSignInWithEmailAndPassword
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val snackbarFlow: MutableStateFlow<SnackbarSettings?>,
    private val showLoaderFlow: MutableStateFlow<Boolean>,
) : ViewModel() {
    private val _signInScreenEvents = MutableStateFlow<SignInScreenEvents?>(value = null)

    private val _signInState = mutableStateOf(
        SignInState(
            formState = FormState(
                fields = listOf(
                    TextFormField(
                        initial = "",
                        name = FormFieldNamesConstants.EMAIL,
                        validators = listOf(
                            Validators.Email(MessageConstants.INVALID_EMAIL),
                            Validators.Required(MessageConstants.EMAIL_REQUIRED)
                        ),
                        label = FormFieldConstants.EMAIL,
                        placeholder = FormFieldConstants.EMAIL,
                        textFieldType = CustomTextFieldType.Outlined,
                        singleLine = true,
                        clearable = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Email
                        ),
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
                        keyboardActions = KeyboardActions(onDone = { signInWithEmailAndPasswordHandler() }),

                        )
                ),
                SignInFields::class,
                SignInFieldsEmpty()
            ),
            _signInScreenEvents
        )
    )
    val signInState: State<SignInState> = _signInState

    fun onEvent(event: SignInViewModelEvents) {
        when (event) {
            SignInViewModelEvents.SignInWithEmailAndPassword -> signInWithEmailAndPasswordHandler()
        }
    }


    private fun signInWithEmailAndPasswordHandler() {
        val data = signInState.value.formState.getDataWithValidation()
        if (data != null) {
            viewModelScope.launch {
                handleSignInWithEmailAndPassword(
                    email = data.email, password = data.password,
                    onSuccess = {
                        viewModelScope.launch {
                            _signInScreenEvents.emit(
                                SignInScreenEvents.SignInSuccessful
                            )
                        }
                    },
                    authUseCases = authUseCases,
                    snackbarFlow = snackbarFlow,
                    showLoaderFlow = showLoaderFlow
                )
            }
        }
    }
}

