package mosis.streetsandtotems.feature_auth.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.CustomDialog
import mosis.streetsandtotems.core.presentation.components.form.Form
import mosis.streetsandtotems.core.presentation.states.FormState
import mosis.streetsandtotems.feature_auth.domain.model.EditPasswordFields


@Composable
fun EditPasswordDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    editPasswordFormState: FormState<EditPasswordFields>
) {
    CustomDialog(
        isOpen = isOpen,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = TitleConstants.EDIT_PASSWORD,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        text = {
            Form(formState = editPasswordFormState)
        },
        clickable = true,
        confirmButtonText = ButtonConstants.SAVE,
        confirmButtonEnabled = editPasswordFormState.isFormFilled.value,
        dismissButtonText = ButtonConstants.CANCEL,
        onDismissButtonClick = onDismissRequest
    )
}