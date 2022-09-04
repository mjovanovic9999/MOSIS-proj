//package mosis.streetsandtotems.core.presentation.components
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.LocalTextStyle
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextAlign
//import mosis.streetsandtotems.core.ButtonConstants
//import mosis.streetsandtotems.core.FormFieldConstants
//import mosis.streetsandtotems.core.ItemsConstants
//import mosis.streetsandtotems.feature_map.domain.model.MarketItem
//import mosis.streetsandtotems.ui.theme.sizes
//
//@Composable
//fun CustomItemDialog(
//    isOpen: Boolean,
//    resourceType: IconType.ResourceType,
//    itemCount: Int,
//    dropTotem: Boolean = false,
//    onDismissRequest: () -> Unit,
//    onConfirmButtonClick: () -> Unit,
//    onDismissButtonClick: (() -> Unit)? = null,
//    isMarket: Boolean,
//    marketExchangeItem: MarketItem? = null,
//    isTotem: Boolean,
//    isHome: Boolean,
//    backpackItemCount: Int? = null
//) {
//
//    val amount =
//        remember { mutableStateOf(if (isMarket && backpackItemCount != null) backpackItemCount.toString() else FormFieldConstants.DEFAULT_AMOUNT) }
//
//    CustomDialog(
//        isOpen = isOpen,
//        modifier = Modifier
//            .fillMaxWidth(MaterialTheme.sizes.drop_item_dialog_width),
//        onDismissRequest = onDismissRequest,
//        title = {
//            CustomDialogTitle(
//                isTotem = dropTotem,
//                resourceType = resourceType,
//                countMessage = if (isTotem) itemCount.toString() + ItemsConstants.ITEMS_LEFT
//                else if (isMarket) "For $itemCount ${marketExchangeItem?.currency_type}s"
//                else if (isHome) "Backpack: $backpackItemCount"
//                else "",
//                needTotemAdditionalText = isTotem,
//            )
//        },
//        text = {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.Bottom
//            ) {
//                if (!isHome)
//                    CustomButton(
//                        clickHandler = onConfirmButtonClick,
//                        text = if (isTotem) ButtonConstants.INVEST else ButtonConstants.BUY,
//                        buttonType = CustomButtonType.Outlined,
//                        textStyle = MaterialTheme.typography.titleMedium,
//                        enabled = amount.value != "" && (isMarket && marketExchangeItem?.price != null && itemCount > marketExchangeItem.price),
//                    )
//                CustomTextField(
//                    modifier = Modifier
//                        .height(MaterialTheme.sizes.drop_item_dialog_amount_text_field_height)
//                        .padding(start = MaterialTheme.sizes.drop_item_dialog_spacer),
//                    value = amount.value,
//                    onValueChange = {
//                        if (it != "") {
//                            if (itemCount >= it.toInt()) amount.value = it
//                        } else amount.value = it
//                    },
//                    placeholder = if (!isMarket) FormFieldConstants.AMOUNT
//                    else (marketExchangeItem?.currency_type ?: "").toString() + "s left",
//                    label = if (!isMarket) FormFieldConstants.AMOUNT
//                    else (marketExchangeItem?.currency_type ?: "").toString() + "s left",
//                    textFieldType = CustomTextFieldType.Outlined,
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
//                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
//                )
//            }
//        },
//        buttonType = CustomButtonType.Outlined,
////        confirmButtonMatchParentWidth = true,// evo nzm
//        onConfirmButtonClick = onConfirmButtonClick,
//        onDismissButtonClick = onDismissButtonClick ?: {},
//        confirmButtonText = ButtonConstants.PLACE,
//        confirmButtonVisible = isHome,
//        dismissButtonVisible = isHome,
//        dismissButtonText = ButtonConstants.PUT,
//        clickable = true,
//    )
//}
//
//
//@Composable
//fun CustomItemDialogHome(
//    isOpen: Boolean,
//    resourceType: IconType.ResourceType,
//    isItemTotem: Boolean,
//    itemCount: Int,
//    onDismissRequest: () -> Unit,
//    onConfirmButtonClick: () -> Unit,
//    onDismissButtonClick: () -> Unit,
//    backpackItemCount: Int
//) {
//
//    val amount =
//        remember { mutableStateOf(FormFieldConstants.DEFAULT_AMOUNT) }
//
//    CustomDialog(
//        isOpen = isOpen,
//        modifier = Modifier
//            .fillMaxWidth(MaterialTheme.sizes.drop_item_dialog_width),
//        onDismissRequest = onDismissRequest,
//        title = {
//            CustomDialogTitle(
//                isTotem = isItemTotem,
//                resourceType = resourceType,
//                countMessage = "Backpack: $backpackItemCount",
//                needTotemAdditionalText = false,
//            )
//        },
//        text = {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.Bottom,
//            ) {
//                CustomTextField(
//                    modifier = Modifier
//                        .height(MaterialTheme.sizes.drop_item_dialog_amount_text_field_height)
//                        .padding(start = MaterialTheme.sizes.drop_item_dialog_spacer)
//                        .fillMaxWidth(0.5f),
//                    value = amount.value,
//                    onValueChange = {
//                        if (it != "") {
//                            if (itemCount >= it.toInt()) amount.value = it
//                        } else amount.value = it
//                    },
//                    placeholder = (marketExchangeItem?.currency_type ?: "").toString() + "s left",
//                    label = (marketExchangeItem?.currency_type ?: "").toString() + "s left",
//                    textFieldType = CustomTextFieldType.Outlined,
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
//                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
//                )
//            }
//        },
//        buttonType = CustomButtonType.Outlined,
////        confirmButtonMatchParentWidth = true,// evo nzm
//        onConfirmButtonClick = onConfirmButtonClick,
//        onDismissButtonClick = onDismissButtonClick ?: {},
//        confirmButtonText = ButtonConstants.TAKE,
//        confirmButtonVisible = true,//neki count
//        dismissButtonVisible = true,//isto
//        dismissButtonText = ButtonConstants.PUT,
//        clickable = true,
//    )
//}