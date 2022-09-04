package mosis.streetsandtotems.core.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.dsc.form_builder.isNumeric
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.core.ItemsConstants
import mosis.streetsandtotems.feature_map.domain.model.MarketItem
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomItemDialog(
    isOpen: Boolean,
    resourceType: IconType.ResourceType,
    itemCount: Int,
    dropTotem: Boolean = false,
    onDismissRequest: () -> Unit,
    onButtonClick: () -> Unit,
    isMarket: Boolean,
    marketExchangeItem: MarketItem? = null,
    isTotem: Boolean,
    isHome: Boolean,
    backpackItemCount: Int? = null
) {

    val dropAmount = remember { mutableStateOf(FormFieldConstants.DEFAULT_AMOUNT) }

    CustomDialog(
        isOpen = isOpen,
        modifier = Modifier
            .fillMaxWidth(MaterialTheme.sizes.drop_item_dialog_width),
        onDismissRequest = onDismissRequest,
        title = {
            CustomDialogTitle(
                isTotem = dropTotem,
                resourceType = resourceType,
                countMessage = if (isTotem) itemCount.toString() + ItemsConstants.ITEMS_LEFT
                else if (isMarket) "For $itemCount ${marketExchangeItem?.currency_type}s"
                else if (isHome) "Backpack: $backpackItemCount"
                else "",
                needAdditionalText = isTotem
            )
        },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                CustomButton(
                    clickHandler = onButtonClick,
                    text = ButtonConstants.DROP,
                    buttonType = CustomButtonType.Outlined,
                    textStyle = MaterialTheme.typography.titleMedium,
                    enabled = dropAmount.value != "" && dropAmount.value.isNumeric()
                )
                CustomTextField(
                    modifier = Modifier
                        .height(MaterialTheme.sizes.drop_item_dialog_amount_text_field_height)
                        .padding(start = MaterialTheme.sizes.drop_item_dialog_spacer),
                    value = dropAmount.value,
                    onValueChange = {
                        if (it != "") {
                            if (itemCount >= it.toInt()) dropAmount.value = it
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
        confirmButtonVisible = dropTotem,
        dismissButtonVisible = isHome,
        dismissButtonText = "Take",
        onDismissButtonClick = {},
        clickable = true,
    )
}