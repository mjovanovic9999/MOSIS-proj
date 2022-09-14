package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.core.TitleConstants.BACKPACK_EMPTY_SPACES_LEFT
import mosis.streetsandtotems.core.TitleConstants.ITEMS_LEFT
import mosis.streetsandtotems.core.presentation.components.*
import mosis.streetsandtotems.feature_map.domain.model.InventoryData
import mosis.streetsandtotems.feature_map.domain.model.ResourceType
import mosis.streetsandtotems.feature_map.presentation.util.convertResourceTypeToIconType
import mosis.streetsandtotems.feature_map.presentation.util.getCountResourceTypeFromInventory
import mosis.streetsandtotems.feature_map.presentation.util.removeLeadingZerosIfAny
import mosis.streetsandtotems.feature_map.presentation.util.updateOneInventoryData
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomResourceDialog(
    isOpen: Boolean,
    resourceType: ResourceType?,
    onDismissRequest: () -> Unit,
    onTake: (InventoryData, Int, Int) -> Unit,
    emptySpaces: Int?,
    oldInventoryData: InventoryData,
    itemsLeft: Int?,
) {

    val takeAmount = mutableStateOf(FormFieldConstants.DEFAULT_AMOUNT)

    val currentResourceCount = getCountResourceTypeFromInventory(
        oldInventoryData,
        resourceType
    )

    CustomDialog(
        isOpen = isOpen,
        modifier = Modifier
            .fillMaxWidth(MaterialTheme.sizes.drop_item_dialog_width),
        onDismissRequest = onDismissRequest,
        title = {
            CustomDialogTitle(
                isTotem = false,
                resourceType = convertResourceTypeToIconType(resourceType),
                countMessage = itemsLeft.toString() + ITEMS_LEFT,
                backpackSpaceMessage = emptySpaces.toString() + BACKPACK_EMPTY_SPACES_LEFT,
                needTotemAdditionalText = false,
            )
        },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                CustomButton(
                    clickHandler = {
                        if (resourceType != null && emptySpaces != null && currentResourceCount != null && itemsLeft != null) {
                            onTake(
                                updateOneInventoryData(
                                    oldInventoryData,
                                    newCount = (currentResourceCount + takeAmount.value.toInt()),
                                    updateType = resourceType
                                ),
                                emptySpaces - takeAmount.value.toInt(),
                                itemsLeft - takeAmount.value.toInt(),
                            )
                            onDismissRequest()
                        }
                    },
                    text = ButtonConstants.TAKE,
                    buttonType = CustomButtonType.Outlined,
                    textStyle = MaterialTheme.typography.titleMedium,
                    enabled = takeAmount.value != ""
                            && takeAmount.value.toInt() > 0
                            && emptySpaces != null && emptySpaces > 0
                            && itemsLeft != null
                            && itemsLeft >= takeAmount.value.toInt()
                )
                CustomTextField(
                    modifier = Modifier
                        .height(MaterialTheme.sizes.drop_item_dialog_amount_text_field_height)
                        .padding(start = MaterialTheme.sizes.drop_item_dialog_spacer),
                    value = takeAmount.value,
                    onValueChange = {
                        if (it != "") {
                            if (itemsLeft != null
                                && itemsLeft >= it.toInt()
                                && (takeAmount.value == ""
                                        || (emptySpaces != null
                                        && emptySpaces - it.toInt() >= 0
                                        && it.toInt() > 0
                                        ))
                            )
                                takeAmount.value = removeLeadingZerosIfAny(it)

                        } else takeAmount.value = it
                    },
                    placeholder = FormFieldConstants.AMOUNT,
                    label = FormFieldConstants.AMOUNT,
                    textFieldType = CustomTextFieldType.Outlined,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    singleLine = true,
                )
            }
        },
        buttonType = CustomButtonType.Outlined,
        confirmButtonMatchParentWidth = true,
        confirmButtonVisible = false,
        dismissButtonVisible = false,
        clickable = true,
    )
}

