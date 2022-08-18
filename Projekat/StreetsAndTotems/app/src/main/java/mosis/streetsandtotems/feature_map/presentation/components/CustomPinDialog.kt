package mosis.streetsandtotems.feature_map.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.CustomPinDialogConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.CustomTextField
import mosis.streetsandtotems.core.presentation.components.CustomTextFieldType
import mosis.streetsandtotems.ui.theme.sizes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPinDialog(isOpen: Boolean, onDismissRequest: () -> Unit) {
    val commentState = remember { mutableStateOf("") }
    if (isOpen)
        AlertDialog(
            onDismissRequest = {
                onDismissRequest()
                commentState.value = ""
            },
            title = {
                Text(
                    text = TitleConstants.CUSTOM_PIN_DIALOG_SQUAD,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            text = {
                CustomTextField(
                    value = commentState.value,
                    onValueChange = {
                        if (it.length <= CustomPinDialogConstants.NOTE_TEXT_LENGTH) commentState.value =
                            it
                    },
                    textFieldType = CustomTextFieldType.Outlined,
                    label = CustomPinDialogConstants.NOTE_TEXT,
                    placeholder = CustomPinDialogConstants.NOTE_TEXT,
                    modifier = Modifier.height(MaterialTheme.sizes.custom_pin_dialog_text_field_height),
                )
            },
            dismissButton = {
                CustomButton(
                    clickHandler = onDismissRequest,
                    buttonType = CustomButtonType.Outlined,
                    text = ButtonConstants.CANCEL,
                    textStyle = MaterialTheme.typography.titleMedium,
                    )
            },
            confirmButton = {
                CustomButton(
                    clickHandler = { /*TODO*/ },
                    buttonType = CustomButtonType.Outlined,
                    text = ButtonConstants.PLACE,
                    enabled = commentState.value != "",
                    textStyle = MaterialTheme.typography.titleMedium,
                    )
            })
}