package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import mosis.streetsandtotems.core.ButtonConstants.SUBMIT_ANSWER
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.core.presentation.components.*
import mosis.streetsandtotems.feature_map.domain.model.RiddleData
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomRiddleDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onCorrectAnswerClick: () -> Unit,
    riddleData: RiddleData,
) {
    val takeAmount = mutableStateOf("")

    CustomDialog(
        isOpen = isOpen,
        modifier = Modifier
            .fillMaxWidth(.95f),
        onDismissRequest = {onDismissRequest()},
        title = {
            Text(
                text = riddleData.question ?: "",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                CustomTextField(
                    modifier = Modifier
                        .height(MaterialTheme.sizes.drop_item_dialog_amount_text_field_height)
                        .padding(start = MaterialTheme.sizes.drop_item_dialog_spacer),
                    value = takeAmount.value,
                    onValueChange = { takeAmount.value = it },
                    placeholder = FormFieldConstants.ANSWER,
                    label = FormFieldConstants.ANSWER,
                    textFieldType = CustomTextFieldType.Outlined,
                    singleLine = true,
                )
            }
        },
        buttonType = CustomButtonType.Outlined,
        dismissButtonVisible = false,
        confirmButtonMatchParentWidth = true,
        confirmButtonVisible = true,
        confirmButtonText = SUBMIT_ANSWER,
        confirmButtonEnabled = takeAmount.value != "",
        onConfirmButtonClick = {
            if (takeAmount.value == riddleData.answer)
                onCorrectAnswerClick()
            else Log.d("tag", "nemas si be pojmna zagonetkeeeeee")
        },
        clickable = true,
    )
}