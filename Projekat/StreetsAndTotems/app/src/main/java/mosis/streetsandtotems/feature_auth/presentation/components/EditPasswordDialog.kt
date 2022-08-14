package mosis.streetsandtotems.feature_auth.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.form.Form
import mosis.streetsandtotems.core.presentation.states.FormState
import mosis.streetsandtotems.feature_auth.domain.model.EditPasswordFields


@Composable
fun EditPasswordDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    editPasswordFormState: FormState<EditPasswordFields>
) {
    lateinit var dialogFocusManager: FocusManager

    if (isOpen)
        AlertDialog(
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) { dialogFocusManager.clearFocus() },
            onDismissRequest = onDismissRequest,
            title = {
                Text(
                    text = TitleConstants.EDIT_PASSWORD,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            text = {
                dialogFocusManager = LocalFocusManager.current
                Form(formState = editPasswordFormState)
            },
            confirmButton = {
                CustomButton(
                    clickHandler = { /*TODO*/ },
                    text = ButtonConstants.SAVE,
                    buttonType = CustomButtonType.Text,
                    enabled = editPasswordFormState.isFormFilled.value,
                    textStyle = MaterialTheme.typography.titleMedium,
                    contentPadding = ButtonDefaults.ContentPadding
                )
            },
            dismissButton = {
                CustomButton(
                    clickHandler = onDismissRequest,
                    text = ButtonConstants.CANCEL,
                    buttonType = CustomButtonType.Text,
                    textStyle = MaterialTheme.typography.titleMedium,
                    contentPadding = ButtonDefaults.ContentPadding
                )
            }
        )
}