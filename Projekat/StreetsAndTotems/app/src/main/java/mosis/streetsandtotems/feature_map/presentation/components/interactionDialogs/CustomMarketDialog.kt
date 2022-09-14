package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.core.PinConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.TitleConstants.PRICE
import mosis.streetsandtotems.core.presentation.components.*
import mosis.streetsandtotems.core.presentation.utils.drawVerticalScrollbar
import mosis.streetsandtotems.feature_map.domain.model.MarketItem
import mosis.streetsandtotems.feature_map.domain.model.ResourceType
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData
import mosis.streetsandtotems.feature_map.presentation.components.CustomBackpackInventory
import mosis.streetsandtotems.feature_map.presentation.util.convertResourceTypeToIconType
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomMarketDialog(
    isOpen: Boolean = true,
    onDismissRequest: () -> Unit = {},
    items: Map<String, MarketItem>?,
    userInventoryData: UserInventoryData?,
    updateUserInventoryData: (newUserInventoryData: UserInventoryData) -> Unit,
    updateMarketItems: (items: Map<String, MarketItem>) -> Unit,
    onBuy: (newUserInventoryData: UserInventoryData, items: Map<String, MarketItem>) -> Unit
) {
    CustomDialog(
        isOpen = isOpen,
        modifier = Modifier
            .fillMaxWidth(MaterialTheme.sizes.drop_item_dialog_width_extended)
            .padding(top = 15.dp, bottom = 15.dp),
        onDismissRequest = onDismissRequest,

        title = {//backpack empty space
            Column {
                CustomAreaHelper(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(60.dp)
                        .padding(bottom = 10.dp),
                    cell00 = {
                        Text(
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                            text = PinConstants.MARKET,
                            textAlign = TextAlign.Center,
                        )
                    },
                )
                CustomBackpackInventory(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp),
                    woodCount = userInventoryData?.inventory?.wood ?: 0,
                    brickCount = userInventoryData?.inventory?.brick ?: 0,
                    emeraldCount = userInventoryData?.inventory?.emerald ?: 0,
                    stoneCount = userInventoryData?.inventory?.stone ?: 0,
                    backpackEmptySpaceCount = userInventoryData?.empty_spaces ?: 0
                )
            }

        },
        text = {
            LazyColumn(Modifier.drawVerticalScrollbar(rememberScrollState())) {
                items(items?.keys?.toList() ?: listOf()) { typeName ->
                    ItemMarket(
                        itemsLeft = items?.get(typeName)?.amount_left ?: 0,
                        emptySpaces = userInventoryData?.empty_spaces ?: 0,
                        resourceType = enumValueOf(typeName),
                        onBuy = { newEmptySpacesCount: Int, newResourceItemsCount: Int, resourceType: ResourceType, amountLeft: Int ->
//                            val mutableMap = items?.toMutableMap()
//                            if (mutableMap != null) {
//                                mutableMap[typeName] = MarketItem(
//                                    amount_left = amountLeft,
//                                    currency_type = items[typeName]?.currency_type,
//                                    price = items[typeName]?.price ?: 0,
//                                )
//                                updateMarketItems(
//                                    mutableMap.toMap()
//                                )
//                            }
//                            updateUserInventoryData(
//                                UserInventoryData(
//                                    empty_spaces = newEmptySpacesCount,
//                                    inventory =
//                                    when (resourceType) {
//                                        ResourceType.Wood -> userInventoryData?.inventory?.copy(
//                                            wood = newResourceItemsCount
//                                                    + (userInventoryData.inventory.wood ?: 0)
//                                        )
//                                        ResourceType.Brick -> userInventoryData?.inventory?.copy(
//                                            brick = newResourceItemsCount
//                                                    + (userInventoryData.inventory.brick ?: 0)
//                                        )
//                                        ResourceType.Stone -> userInventoryData?.inventory?.copy(
//                                            stone = newResourceItemsCount
//                                                    + (userInventoryData.inventory.stone ?: 0)
//                                        )
//                                        ResourceType.Emerald -> userInventoryData?.inventory?.copy(
//                                            emerald = newResourceItemsCount
//                                                    + (userInventoryData.inventory.emerald ?: 0)
//                                        )
//                                    }
//                                )
//                            )

                            val mutableMap = items?.toMutableMap()
                            if (mutableMap != null) {
                                mutableMap[typeName] = MarketItem(
                                    amount_left = amountLeft,
                                    currency_type = items[typeName]?.currency_type,
                                    price = items[typeName]?.price ?: 0,
                                )
                                onBuy(
                                    UserInventoryData(
                                        empty_spaces = newEmptySpacesCount,
                                        inventory =
                                        when (resourceType) {
                                            ResourceType.Wood -> userInventoryData?.inventory?.copy(
                                                wood = newResourceItemsCount
                                                        + (userInventoryData.inventory.wood ?: 0)
                                            )
                                            ResourceType.Brick -> userInventoryData?.inventory?.copy(
                                                brick = newResourceItemsCount
                                                        + (userInventoryData.inventory.brick ?: 0)
                                            )
                                            ResourceType.Stone -> userInventoryData?.inventory?.copy(
                                                stone = newResourceItemsCount
                                                        + (userInventoryData.inventory.stone ?: 0)
                                            )
                                            ResourceType.Emerald -> userInventoryData?.inventory?.copy(
                                                emerald = newResourceItemsCount
                                                        + (userInventoryData.inventory.emerald ?: 0)
                                            )
                                        }
                                    ),
                                    mutableMap.toMap(),
                                )
                            }
                        },
                        onDismissRequest = onDismissRequest,
                        exchangeCurrency = items?.get(typeName)?.price ?: 0,
                        exchangeResourceType = items?.get(typeName)?.currency_type,
                    )
                }
            }
        },
        buttonType = CustomButtonType.Outlined,
        confirmButtonMatchParentWidth = true,
        confirmButtonVisible = false,
        dismissButtonVisible = true,
        dismissButtonText = ButtonConstants.DISMISS,
        onDismissButtonClick = onDismissRequest,
        clickable = true,
        dismissButtonMatchParentWidth = true,
    )
}


@Composable
private fun ItemMarket(
    itemsLeft: Int?,
    emptySpaces: Int?,
    resourceType: ResourceType,
    onBuy: (newEmptySpacesCount: Int, newResourceItemsCount: Int, resourceType: ResourceType, amountLeft: Int) -> Unit,// newEmptySpacesCount, newResourceItemsLeft
    onDismissRequest: () -> Unit,
    exchangeResourceType: ResourceType?,
    exchangeCurrency: Int,
) {
    val takeAmount = mutableStateOf(FormFieldConstants.DEFAULT_AMOUNT)

    CustomAreaHelper(
        modifier = Modifier
            .fillMaxWidth()
            .size(150.dp)
            .padding(5.dp),
        shouldAlignBottom = true,
        topPadding = false,
        title = {
            CustomDialogTitle(
                isTotem = false,
                resourceType = convertResourceTypeToIconType(resourceType),
                countMessage = itemsLeft.toString() + TitleConstants.ITEMS_LEFT,
                backpackSpaceMessage = "$PRICE $exchangeCurrency ${exchangeResourceType}s",
                needTotemAdditionalText = false,
            )
        },
        cell10 = {
            CustomButton(
                buttonModifier = Modifier.padding(5.dp),
                clickHandler = {
                    if (emptySpaces != null && itemsLeft != null) {
                        onBuy(
                            emptySpaces - takeAmount.value.toInt(),
                            takeAmount.value.toInt(),
                            resourceType,
                            itemsLeft - takeAmount.value.toInt()
                        )
                    }
                    onDismissRequest()
                },
                text = ButtonConstants.BUY,
                contentPadding = ButtonDefaults.ContentPadding,
                buttonType = CustomButtonType.Outlined,
                textStyle = MaterialTheme.typography.titleMedium,
                enabled = takeAmount.value != ""
                        && takeAmount.value.toInt() > 0
                        && emptySpaces != null && emptySpaces > 0
                        && itemsLeft != null
                        && itemsLeft >= takeAmount.value.toInt()
            )
        },
        cell11 = {
            CustomTextField(
                modifier = Modifier
                    .height(MaterialTheme.sizes.drop_item_dialog_amount_text_field_height)
                    .padding(
                        start = MaterialTheme.sizes.drop_item_dialog_spacer,
                        end = 5.dp
                    ),
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
                            if (it.length == 1)
                                takeAmount.value = it
                            else if (it.first() == '0') {
                                takeAmount.value = it.toInt().toString()
                            }
                    } else takeAmount.value = it
                },
                singleLine = true,
                textFieldType = CustomTextFieldType.Outlined,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                placeholder = FormFieldConstants.AMOUNT,
                label = FormFieldConstants.AMOUNT,

                )
        },
    )
}