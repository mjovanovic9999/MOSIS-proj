package mosis.streetsandtotems.feature_auth.presentation.profile

import android.util.Log
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
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
import mosis.streetsandtotems.core.presentation.components.form.formfields.ImageSelectFormField
import mosis.streetsandtotems.core.presentation.components.form.formfields.TextFormField
import mosis.streetsandtotems.core.presentation.states.FormState
import mosis.streetsandtotems.feature_auth.presentation.util.EditPasswordFields
import mosis.streetsandtotems.feature_auth.presentation.util.EditPasswordFieldsEmpty
import mosis.streetsandtotems.feature_auth.presentation.util.ProfileFields
import mosis.streetsandtotems.feature_auth.presentation.util.ProfileFieldsEmpty
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    private val _profileScreenState = mutableStateOf(
        ProfileScreenState(
            editMode = false, formState = createFormState(
                ProfileFields(
                    firstName = "", lastName = "", phoneNumber = "", email = "", imagePath = ""
                ), editMode = false
            ), editPasswordDialogOpen = true, passwordDialogFormState = FormState(
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
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Password
                        ),
                    ), TextFormField(
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
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Password
                        ),
                    ), TextFormField(
                        initial = "",
                        name = FormFieldNamesConstants.REPEAT_PASSWORD,
                        validators = listOf(
                            Validators.Custom(
                                MessageConstants.PASSWORDS_DO_NOT_MATCH, ::validateRepeatedPassword
                            ),
                            Validators.Min(
                                FormFieldLengthConstants.PASSWORD, MessageConstants.PASSWORD_LENGTH
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
                            imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
                        ),
                        keyboardActions = KeyboardActions(onDone = { }),
                    )
                ), EditPasswordFields::class, EditPasswordFieldsEmpty()
            )
        )
    )
    val profileScreenState: State<ProfileScreenState> = _profileScreenState

    private var lastFieldValues = ProfileFields(
        firstName = "", lastName = "", phoneNumber = "", email = "", imagePath = ""
    )

    private fun validateRepeatedPassword(repeatedPassword: Any): Boolean {
        return _profileScreenState.value.passwordDialogFormState.getData().newPassword == repeatedPassword.toString()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun createFormState(
        initialFormFields: ProfileFields, editMode: Boolean
    ): FormState<ProfileFields> {
        return FormState(
            buildList {
                add(
                    ImageSelectFormField(
                        initial = initialFormFields.imagePath,
                        name = FormFieldNamesConstants.IMAGE_PATH,
                        validators = listOf(Validators.Required(MessageConstants.IMAGE_REQUIRED)),
                        editable = editMode,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )
                )
                add(
                    TextFormField(
                        initial = initialFormFields.firstName,
                        name = FormFieldNamesConstants.FIRST_NAME,
                        textFieldType = CustomTextFieldType.Outlined,
                        validators = if (editMode) listOf(
                            Validators.Required(
                                MessageConstants.FIRST_NAME_REQUIRED
                            )
                        ) else emptyList(),
                        label = FormFieldConstants.FIRST_NAME,
                        placeholder = FormFieldConstants.FIRST_NAME,
                        clearable = editMode,
                        readOnly = !editMode,
                        enabled = editMode,
                        keyboardOptions = if (editMode) KeyboardOptions(
                            imeAction = ImeAction.Next
                        ) else KeyboardOptions(),
                        colors = {
                            if (editMode) TextFieldDefaults.outlinedTextFieldColors() else TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = MaterialTheme.colorScheme.background,
                                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        showErrorMessage = editMode,
                    )
                )
                add(
                    TextFormField(
                        initial = initialFormFields.lastName,
                        name = FormFieldNamesConstants.LAST_NAME,
                        textFieldType = CustomTextFieldType.Outlined,
                        validators = if (editMode) listOf(
                            Validators.Required(
                                MessageConstants.LAST_NAME_REQUIRED
                            )
                        ) else emptyList(),
                        label = FormFieldConstants.LAST_NAME,
                        placeholder = FormFieldConstants.LAST_NAME,
                        singleLine = true,
                        clearable = editMode,
                        readOnly = !editMode,
                        enabled = editMode,
                        keyboardOptions = if (editMode) KeyboardOptions(
                            imeAction = ImeAction.Next
                        ) else KeyboardOptions(),
                        colors = {
                            if (editMode) TextFieldDefaults.outlinedTextFieldColors() else TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = MaterialTheme.colorScheme.background,
                                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        showErrorMessage = editMode,
                    )
                )
                add(
                    TextFormField(
                        initial = initialFormFields.phoneNumber,
                        name = FormFieldNamesConstants.PHONE_NUMBER,
                        textFieldType = CustomTextFieldType.Outlined,
                        validators = if (editMode) listOf(
                            Validators.Custom(
                                MessageConstants.INVALID_PHONE_NUMBER, ::validatePhoneNumber
                            ), Validators.Required(MessageConstants.PHONE_NUMBER_REQUIRED)
                        ) else emptyList(),
                        label = FormFieldConstants.PHONE_NUMBER,
                        placeholder = FormFieldConstants.PHONE_NUMBER,
                        singleLine = true,
                        clearable = editMode,
                        readOnly = !editMode,
                        enabled = editMode,
                        keyboardOptions = if (editMode) KeyboardOptions(
                            imeAction = ImeAction.Next
                        ) else KeyboardOptions(),
                        colors = {
                            if (editMode) TextFieldDefaults.outlinedTextFieldColors() else TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = MaterialTheme.colorScheme.background,
                                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        showErrorMessage = editMode,
                    )
                )
                add(
                    TextFormField(
                        initial = initialFormFields.email,
                        name = FormFieldNamesConstants.EMAIL,
                        textFieldType = CustomTextFieldType.Outlined,
                        validators = if (editMode) listOf(
                            Validators.Custom(
                                MessageConstants.INVALID_EMAIL, ::validatePhoneNumber
                            ), Validators.Required(MessageConstants.EMAIL_REQUIRED)
                        ) else emptyList(),
                        label = FormFieldConstants.EMAIL,
                        placeholder = FormFieldConstants.EMAIL,
                        singleLine = true,
                        clearable = editMode,
                        readOnly = !editMode,
                        enabled = editMode,
                        keyboardOptions = if (editMode) KeyboardOptions(
                            imeAction = ImeAction.Done
                        ) else KeyboardOptions(),
                        colors = {
                            if (editMode) TextFieldDefaults.outlinedTextFieldColors() else TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = MaterialTheme.colorScheme.background,
                                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        showErrorMessage = editMode,
                    )
                )
            }, ProfileFields::class, ProfileFieldsEmpty()
        )
    }

    fun onEvent(event: ProfileViewModelEvents) {
        when (event) {
            is ProfileViewModelEvents.InitializeFormFields -> onInitializeFormFields(event.currentUserFields)
            ProfileViewModelEvents.ChangeMode -> onChangeModeHandler()
            ProfileViewModelEvents.HideEditPasswordDialog -> onShowEditPasswordDialogHandler()
            ProfileViewModelEvents.ShowEditPasswordDialog -> onHideEditPasswordDialogHandler()
        }
    }

    private fun onInitializeFormFields(currentUserFields: ProfileFields) {
        Log.d("tag", currentUserFields.imagePath)
        _profileScreenState.value = _profileScreenState.value.copy(
            formState = createFormState(
                currentUserFields, editMode = false
            )
        )
    }

    private fun onChangeModeHandler() {
        val editMode = _profileScreenState.value.editMode
        if (!editMode) lastFieldValues = _profileScreenState.value.formState.getData()
        Log.d("tag", lastFieldValues.imagePath)
        _profileScreenState.value = _profileScreenState.value.copy(
            editMode = !editMode,
            formState = createFormState(lastFieldValues, !editMode),
        )
    }

    private fun onShowEditPasswordDialogHandler() {
        _profileScreenState.value = _profileScreenState.value.copy(editPasswordDialogOpen = false)
    }

    private fun onHideEditPasswordDialogHandler() {
        _profileScreenState.value = _profileScreenState.value.copy(editPasswordDialogOpen = false)
    }

}


