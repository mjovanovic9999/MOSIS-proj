package mosis.streetsandtotems.core.presentation.screens.tiki

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsc.form_builder.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.core.FormFieldNamesConstants
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.core.domain.model.SnackbarSettings
import mosis.streetsandtotems.core.domain.util.PasswordResetFields
import mosis.streetsandtotems.core.domain.util.PasswordResetFieldsEmpty
import mosis.streetsandtotems.core.domain.util.handleResponse
import mosis.streetsandtotems.core.presentation.components.CustomTextFieldType
import mosis.streetsandtotems.core.presentation.components.form.formfields.TextFormField
import mosis.streetsandtotems.core.presentation.states.FormState
import mosis.streetsandtotems.feature_auth.domain.use_case.AuthUseCases
import javax.inject.Inject


@HiltViewModel
class TikiScreenViewModel @Inject constructor(
    private val showLoader: MutableStateFlow<Boolean>,
    private val snackbarSettingsFlow: MutableStateFlow<SnackbarSettings?>,
    private val authUseCases: AuthUseCases
) : ViewModel() {
    private val _tikiScreenEventFlow = MutableSharedFlow<TikiScreenEvents>()
    private val _formState = FormState(
        listOf(
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
                    imeAction = ImeAction.Done, keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(onDone = { handlePasswordResetSubmit() })
            )
        ), PasswordResetFields::class, PasswordResetFieldsEmpty(true)
    )
    private val _tikiScreenState = mutableStateOf(TikiScreenState(_tikiScreenEventFlow, _formState))
    val tikiScreenState: State<TikiScreenState> = _tikiScreenState

    fun onEvent(event: TikiScreenViewModelEvents) {
        when (event) {
            TikiScreenViewModelEvents.SendVerificationEmail -> {
                viewModelScope.launch {
                    handleResponse(
                        responseFlow = authUseCases.sendVerificationEmail(),
                        showLoaderFlow = showLoader,
                        snackbarFlow = snackbarSettingsFlow,
                        successMessage = MessageConstants.EMAIL_SENT,
                        defaultErrorMessage = MessageConstants.EMAIL_TIMEOUT
                    )
                }
            }
            TikiScreenViewModelEvents.GoToSignInScreen -> {
                viewModelScope.launch {
                    authUseCases.signOut()
                    _tikiScreenEventFlow.emit(TikiScreenEvents.GoToSignInScreen)
                }
            }
            TikiScreenViewModelEvents.SendRecoveryEmail -> handlePasswordResetSubmit()
        }
    }


    private fun handlePasswordResetSubmit() {

        val passwordResetFields = _formState.getDataWithValidation()
        if (passwordResetFields != null) {
            viewModelScope.launch {
                _tikiScreenEventFlow.emit(TikiScreenEvents.ClearFocus)
            }
            viewModelScope.launch {
                handleResponse(
                    responseFlow = authUseCases.sendRecoveryEmail(passwordResetFields.email),
                    showLoaderFlow = showLoader,
                    snackbarFlow = snackbarSettingsFlow,
                    successMessage = MessageConstants.RECOVERY_EMAIL_SENT
                )
            }
        }
    }
}