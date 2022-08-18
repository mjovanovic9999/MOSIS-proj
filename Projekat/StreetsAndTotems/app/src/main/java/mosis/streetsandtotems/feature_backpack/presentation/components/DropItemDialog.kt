package mosis.streetsandtotems.feature_backpack.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.core.ItemsConstants
import mosis.streetsandtotems.core.presentation.components.*
import mosis.streetsandtotems.feature_backpack.presentation.DropItemDialogState
import mosis.streetsandtotems.ui.theme.sizes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropItemDialog(state: DropItemDialogState, onDismissRequest: () -> Unit) {
    val dropAmount = remember { mutableStateOf(FormFieldConstants.DEFAULT_AMOUNT) }

    CustomDialog(
        isOpen = state.open,
        modifier = Modifier
            .fillMaxWidth(MaterialTheme.sizes.drop_item_dialog_width),
        onDismissRequest = onDismissRequest,
        title = {
            CustomDialogTitle(
                isTotem = state.dropTotem,
                resourceType = state.itemType,
                countMessage = state.itemCount?.toString() + ItemsConstants.ITEMS_LEFT
            )
        },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                CustomButton(
                    clickHandler = { /*TODO*/ },
                    text = ButtonConstants.DROP,
                    buttonType = CustomButtonType.Outlined,
                    textStyle = MaterialTheme.typography.titleMedium,
                    enabled = dropAmount.value != ""
                )
                CustomTextField(
                    modifier = Modifier
                        .height(MaterialTheme.sizes.drop_item_dialog_amount_text_field_height)
                        .padding(start = MaterialTheme.sizes.drop_item_dialog_spacer),
                    value = dropAmount.value,
                    onValueChange = {
                        if (state.itemCount != null && it != "") {
                            if (state.itemCount >= it.toInt()) dropAmount.value = it
                        } else dropAmount.value = it
                    },
                    placeholder = FormFieldConstants.AMOUNT,
                    label = FormFieldConstants.AMOUNT,
                    textFieldType = CustomTextFieldType.Outlined,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                )
            }
        },
        buttonType = CustomButtonType.Outlined,
        confirmButtonMatchParentWidth = true,
        confirmButtonText = ButtonConstants.PLACE,
        confirmButtonVisible = state.dropTotem,
        dismissButtonVisible = false,
        clickable = true
    )
}


