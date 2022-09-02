package mosis.streetsandtotems.feature_map.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.ButtonConstants.EDIT
import mosis.streetsandtotems.core.ButtonConstants.PLACE
import mosis.streetsandtotems.core.ButtonConstants.REMOVE
import mosis.streetsandtotems.core.CustomPinDialogConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.TitleConstants.CUSTOM_NEW_PIN_DIALOG_SOLO
import mosis.streetsandtotems.core.TitleConstants.CUSTOM_NEW_PIN_DIALOG_SQUAD
import mosis.streetsandtotems.core.TitleConstants.CUSTOM_PIN_DIALOG_SOLO_PLACED_BY
import mosis.streetsandtotems.core.TitleConstants.CUSTOM_PIN_DIALOG_SQUAD_PLACED_BY
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.CustomTextField
import mosis.streetsandtotems.core.presentation.components.CustomTextFieldType
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomPinDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    dialogText: MutableState<String>,
    isNewPin: Boolean,//promena na dugmici
    isSquadMember: Boolean,
    placedBy: String?,
) {
    val initText = remember { mutableStateOf(dialogText.value) }
    if (isOpen)
        AlertDialog(
            onDismissRequest = {
                onDismissRequest()
            },
            title = {
                Text(
                    text =
                    if (isNewPin)
                        if (isSquadMember) CUSTOM_NEW_PIN_DIALOG_SQUAD else CUSTOM_NEW_PIN_DIALOG_SOLO
                    else
                        if (isSquadMember)
                            CUSTOM_PIN_DIALOG_SQUAD_PLACED_BY + (placedBy ?: "")
                        else CUSTOM_PIN_DIALOG_SOLO_PLACED_BY,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            text = {
                CustomTextField(
                    value = dialogText.value,
                    onValueChange = {
                        if (it.length <= CustomPinDialogConstants.PIN_TEXT_LENGTH)
                            dialogText.value = it
                    },
                    textFieldType = CustomTextFieldType.Outlined,
                    label = CustomPinDialogConstants.PIN_TEXT,
                    placeholder = CustomPinDialogConstants.PIN_TEXT,
                    modifier = Modifier.height(MaterialTheme.sizes.custom_pin_dialog_text_field_height),

                    )
            },
            dismissButton = {
                CustomButton(
                    clickHandler = {
                        if (!isNewPin) {

                        }//na server delete
                        onDismissRequest()
                    },
                    buttonType = CustomButtonType.Outlined,
                    text = if (isNewPin) ButtonConstants.CANCEL else REMOVE,
                    textStyle = MaterialTheme.typography.titleMedium,
                )
            },
            confirmButton = {
                CustomButton(
                    clickHandler = {
                        if (isNewPin) {

                        }//na server add
                        else {
                        }//na server edit
                        onDismissRequest()
                    },
                    buttonType = CustomButtonType.Outlined,
                    text = if (isNewPin) PLACE else EDIT,
                    enabled = if (isNewPin) dialogText.value != "" else dialogText.value != initText.value,
                    textStyle = MaterialTheme.typography.titleMedium,
                )
            })
}