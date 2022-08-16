package mosis.streetsandtotems.feature_auth.presentation.profile

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.lifecycle.ViewModel
import com.dsc.form_builder.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mosis.streetsandtotems.core.*
import mosis.streetsandtotems.core.domain.validators.validatePhoneNumber
import mosis.streetsandtotems.core.presentation.components.CustomTextFieldType
import mosis.streetsandtotems.core.presentation.components.form.formfields.TextFormField
import mosis.streetsandtotems.core.presentation.states.FormState
import mosis.streetsandtotems.feature_auth.domain.model.EditPasswordFields
import mosis.streetsandtotems.feature_auth.domain.model.EditPasswordFieldsEmpty
import mosis.streetsandtotems.feature_auth.domain.model.ProfileFields
import mosis.streetsandtotems.feature_auth.domain.model.ProfileFieldsEmpty
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    private val _editMode = mutableStateOf(false)
    val editMode: State<Boolean> = _editMode

    private val _formState = MutableStateFlow(createFormState())
    val formState: StateFlow<FormState<ProfileFields>> = _formState

    private val _editPasswordDialogOpen = mutableStateOf(false)
    val editPasswordDialogOpen: State<Boolean> = _editPasswordDialogOpen

    val editPasswordFormState =
        FormState(
            listOf(
                TextFormField(
                    initial = "",
                    name = FormFieldNamesConstants.CURRENT_PASSWORD,
                    validators = listOf(
                        Validators.Min(
                            FormFieldLengthConstants.PASSWORD, MessageConstants.PASSWORD_LENGTH
                        ),
                        Validators.Required(MessageConstants.CURRENT_PASSWORD_REQUIRED),
                    ),
                    label = FormFieldConstants.CURRENT_PASSWORD,
                    placeholder = FormFieldConstants.CURRENT_PASSWORD,
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
                    name = FormFieldNamesConstants.NEW_PASSWORD,
                    validators = listOf(
                        Validators.Required(MessageConstants.NEW_PASSWORD_REQUIRED),
                        Validators.Min(8, MessageConstants.PASSWORD_LENGTH)
                    ),
                    label = FormFieldConstants.NEW_PASSWORD,
                    placeholder = FormFieldConstants.NEW_PASSWORD,
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
                    keyboardActions = KeyboardActions(onDone = { }),
                )
            ), EditPasswordFields::class, EditPasswordFieldsEmpty()
        )

    fun changeMode() {
        _editMode.value = !editMode.value
        _formState.value = createFormState()
    }

    fun showEditPasswordDialog() {
        _editPasswordDialogOpen.value = true
    }

    fun hideEditPasswordDialog() {
        _editPasswordDialogOpen.value = false
    }

    private fun validateRepeatedPassword(repeatedPassword: Any): Boolean {
        return editPasswordFormState.getData().newPassword == repeatedPassword.toString()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun createFormState(): FormState<ProfileFields> {
        return FormState(
            buildList {
                add(
                    TextFormField(
                        initial = "John",
                        name = FormFieldNamesConstants.FIRST_NAME,
                        textFieldType = CustomTextFieldType.Outlined,
                        validators = if (editMode.value) listOf(Validators.Required(MessageConstants.FIRST_NAME_REQUIRED)) else emptyList(),
                        label = FormFieldConstants.FIRST_NAME,
                        placeholder = FormFieldConstants.FIRST_NAME,
                        clearable = editMode.value,
                        readOnly = !editMode.value,
                        enabled = editMode.value,
                        keyboardOptions = if (editMode.value) KeyboardOptions(imeAction = ImeAction.Next) else KeyboardOptions(),
                        colors = {
                            if (editMode.value) TextFieldDefaults.outlinedTextFieldColors() else TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = MaterialTheme.colorScheme.background,
                                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        showErrorMessage = editMode.value,
                    )
                )
                add(
                    TextFormField(
                        initial = "Smith",
                        name = FormFieldNamesConstants.LAST_NAME,
                        textFieldType = CustomTextFieldType.Outlined,
                        validators = if (editMode.value) listOf(Validators.Required(MessageConstants.LAST_NAME_REQUIRED)) else emptyList(),
                        label = FormFieldConstants.LAST_NAME,
                        placeholder = FormFieldConstants.LAST_NAME,
                        singleLine = true,
                        clearable = editMode.value,
                        readOnly = !editMode.value,
                        enabled = editMode.value,
                        keyboardOptions = if (editMode.value) KeyboardOptions(imeAction = ImeAction.Next) else KeyboardOptions(),
                        colors = {
                            if (editMode.value) TextFieldDefaults.outlinedTextFieldColors() else TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = MaterialTheme.colorScheme.background,
                                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        showErrorMessage = editMode.value,
                    )
                )
                add(
                    TextFormField(
                        initial = "+381667778900",
                        name = FormFieldNamesConstants.PHONE_NUMBER,
                        textFieldType = CustomTextFieldType.Outlined,
                        validators = if (editMode.value) listOf(
                            Validators.Custom(
                                MessageConstants.INVALID_PHONE_NUMBER,
                                ::validatePhoneNumber
                            ), Validators.Required(MessageConstants.PHONE_NUMBER_REQUIRED)
                        ) else emptyList(),
                        label = FormFieldConstants.PHONE_NUMBER,
                        placeholder = FormFieldConstants.PHONE_NUMBER,
                        singleLine = true,
                        clearable = editMode.value,
                        readOnly = !editMode.value,
                        enabled = editMode.value,
                        keyboardOptions = if (editMode.value) KeyboardOptions(imeAction = ImeAction.Done) else KeyboardOptions(),
                        colors = {
                            if (editMode.value) TextFieldDefaults.outlinedTextFieldColors() else TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = MaterialTheme.colorScheme.background,
                                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        showErrorMessage = editMode.value,
                    )
                )
                if (!editMode.value) add(
                    TextFormField(
                        initial = "KorisnickoIme1",
                        name = FormFieldNamesConstants.USERNAME,
                        textFieldType = CustomTextFieldType.Outlined,
                        label = FormFieldConstants.USERNAME,
                        placeholder = FormFieldConstants.USERNAME,
                        singleLine = true,
                        clearable = false,
                        readOnly = true,
                        enabled = false,
                        colors = {
                            if (editMode.value) TextFieldDefaults.outlinedTextFieldColors() else TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = MaterialTheme.colorScheme.background,
                                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        showErrorMessage = false
                    )
                )
            },
            ProfileFields::class,
            ProfileFieldsEmpty()
        )
    }
}


