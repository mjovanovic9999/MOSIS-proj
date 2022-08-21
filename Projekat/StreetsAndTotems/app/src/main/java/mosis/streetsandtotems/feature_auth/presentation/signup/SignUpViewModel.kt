package mosis.streetsandtotems.feature_auth.presentation.signup

import android.util.Log
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
import mosis.streetsandtotems.core.domain.util.handleResponse
import mosis.streetsandtotems.core.domain.validators.validatePhoneNumber
import mosis.streetsandtotems.core.presentation.components.CustomTextFieldType
import mosis.streetsandtotems.core.presentation.components.form.formfields.TextFormField
import mosis.streetsandtotems.core.presentation.states.FormState
import mosis.streetsandtotems.feature_auth.domain.use_case.AuthUseCases
import mosis.streetsandtotems.feature_auth.presentation.util.SignUpFields
import mosis.streetsandtotems.feature_auth.presentation.util.SignUpFieldsEmpty
import mosis.streetsandtotems.feature_auth.presentation.util.handleSignInWithEmailAndPassword
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val snackbarFlow: MutableStateFlow<SnackbarSettings?>,
    private val showLoaderFlow: MutableStateFlow<Boolean>
) : ViewModel() {
    private val _signUpScreenEventFlow = MutableStateFlow<SignUpScreenEvents?>(value = null)
    private val _signUpScreenState =
        mutableStateOf(
            SignUpState(
                signUpScreenEventFlow = _signUpScreenEventFlow, formState = FormState(
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
                            name = FormFieldNamesConstants.EMAIL,
                            validators = listOf(
                                Validators.Email(MessageConstants.EMAIL_REQUIRED),
                                Validators.Required(MessageConstants.EMAIL_REQUIRED)
                            ),
                            label = FormFieldConstants.EMAIL,
                            placeholder = FormFieldConstants.EMAIL,
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
                                    AnnotatedString(
                                        VisualTransformationConstants.PASSWORD.repeat(
                                            text.length
                                        )
                                    ),
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
                                    AnnotatedString(
                                        VisualTransformationConstants.PASSWORD.repeat(
                                            text.length
                                        )
                                    ),
                                    OffsetMapping.Identity
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Password
                            ),
                            keyboardActions = KeyboardActions(onDone = { signUpWithEmailAndPasswordHandler() }),
                        )
                    ),
                    SignUpFields::class,
                    SignUpFieldsEmpty()
                )
            )
        )
    val signUpScreenState: State<SignUpState> = _signUpScreenState

    private fun validateRepeatedPassword(repeatedPassword: Any): Boolean {
        return _signUpScreenState.value.formState.getData().password == repeatedPassword.toString()
    }

    fun onEvent(event: SignUpViewModelEvents) {
        when (event) {
            SignUpViewModelEvents.SignUpWithEmailAndPassword -> signUpWithEmailAndPasswordHandler()
        }
    }

    private fun signUpWithEmailAndPasswordHandler() {
        val signUpFields = _signUpScreenState.value.formState.getDataWithValidation()
        if (signUpFields != null) {
            viewModelScope.launch {
                handleResponse(
                    authUseCases.emailAndPasswordSignUp(
                        signUpFields.email,
                        signUpFields.password
                    ),
                    snackbarFlow = snackbarFlow,
                    showLoaderFlow = showLoaderFlow,
                    errorMessage = MessageConstants.SIGN_UP_ERROR,
                    successMessage = MessageConstants.SIGNED_UP,
                    onSuccess = {
                        viewModelScope.launch {
                            Log.d("tag", authUseCases.isUserAuthenticated().toString())

                        //                            handleSignInWithEmailAndPassword(
//                                email = signUpFields.email,
//                                password = signUpFields.password,
//                                onSuccess = {
//                                    viewModelScope.launch {
//                                        _signUpScreenEventFlow.emit(
//                                            SignUpScreenEvents.SignUpSuccessful
//                                        )
//                                    }
//                                },
//                                authUseCases = authUseCases,
//                                snackbarFlow = snackbarFlow,
//                                showLoaderFlow = showLoaderFlow
//                            )
                        }
                    }
                )
            }
        }
    }
}


