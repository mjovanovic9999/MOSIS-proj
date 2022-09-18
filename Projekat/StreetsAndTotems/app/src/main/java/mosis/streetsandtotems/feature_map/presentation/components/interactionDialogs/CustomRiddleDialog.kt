package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.core.ButtonConstants.SUBMIT_ANSWER
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.core.TitleConstants.SOLVE_ME
import mosis.streetsandtotems.core.presentation.components.*
import mosis.streetsandtotems.feature_map.domain.model.RiddleData
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomRiddleDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onCorrectAnswerClick: () -> Unit,
    onIncorrectAnswerClick: () -> Unit,
    riddleData: RiddleData,
) {
    val takeAmount = remember { mutableStateOf("") }

    CustomDialog(
        isOpen = isOpen,
        modifier = Modifier
            .fillMaxWidth(MaterialTheme.sizes.drop_item_dialog_width),
        onDismissRequest = {
            onDismissRequest()
            takeAmount.value = ""
        },
        title = {
            Text(SOLVE_ME, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        },
        text = {
            Column() {
                Text(
                    text = riddleData.question ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Justify,
                )
                Box(modifier = Modifier.height(85.dp)) {
                    CustomTextField(
                        modifier = Modifier
                            .height(MaterialTheme.sizes.drop_item_dialog_amount_text_field_height)
                            .align(
                                Alignment.BottomCenter
                            ),
                        value = takeAmount.value,
                        onValueChange = {
                            takeAmount.value = it
                        },
                        placeholder = FormFieldConstants.ANSWER,
                        label = FormFieldConstants.ANSWER,
                        textFieldType = CustomTextFieldType.Outlined,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        enabled = true,
                    )
                }
            }
        },
        buttonType = CustomButtonType.Outlined,
        dismissButtonVisible = false,
        confirmButtonMatchParentWidth = true,
        confirmButtonVisible = true,
        confirmButtonText = SUBMIT_ANSWER,
        confirmButtonEnabled = takeAmount.value != "",
        onConfirmButtonClick = {
            if (takeAmount.value.uppercase() == riddleData.answer?.uppercase())
                onCorrectAnswerClick()
            else
                onIncorrectAnswerClick()
            onDismissRequest()
            takeAmount.value = ""
        },
        clickable = true,
    )
}