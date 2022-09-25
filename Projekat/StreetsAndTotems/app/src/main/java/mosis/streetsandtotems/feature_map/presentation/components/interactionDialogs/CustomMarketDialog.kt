package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

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
import mosis.streetsandtotems.core.TitleConstants.BACKPACK_INVENTORY
import mosis.streetsandtotems.core.TitleConstants.PRICE
import mosis.streetsandtotems.core.presentation.components.*
import mosis.streetsandtotems.core.presentation.utils.drawVerticalScrollbar
import mosis.streetsandtotems.feature_map.domain.model.InventoryData
import mosis.streetsandtotems.feature_map.domain.model.MarketItem
import mosis.streetsandtotems.feature_map.domain.model.ResourceType
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData
import mosis.streetsandtotems.feature_map.presentation.components.CustomUserInventory
import mosis.streetsandtotems.feature_map.presentation.util.convertResourceTypeToIconType
import mosis.streetsandtotems.feature_map.presentation.util.removeLeadingZerosIfAny
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomMarketDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit = {},
    items: Map<String, MarketItem>?,
    userInventoryData: UserInventoryData?,
    updateUserInventoryData: (newUserInventoryData: UserInventoryData) -> Unit,
    updateMarketItems: (items: Map<String, MarketItem>) -> Unit,
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
                CustomUserInventory(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp),
                    woodCount = userInventoryData?.inventory?.wood ?: 0,
                    brickCount = userInventoryData?.inventory?.brick ?: 0,
                    emeraldCount = userInventoryData?.inventory?.emerald ?: 0,
                    stoneCount = userInventoryData?.inventory?.stone ?: 0,
                    emptySpaceCount = userInventoryData?.empty_spaces ?: 0,
                    title = BACKPACK_INVENTORY,
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
                            onBuy = { newEmptySpacesCount: Int, newResourceItemsCount: Int, resourceType: ResourceType, amountLeft: Int, exchangedResourceLeft: Int ->
                                val mutableMap = items?.toMutableMap()
                                if (mutableMap != null) {
                                    mutableMap[typeName] = MarketItem(
                                        amount_left = amountLeft,
                                        currency_type = items[typeName]?.currency_type,
                                        price = items[typeName]?.price ?: 0,
                                    )
                                    updateMarketItems(
                                        mutableMap.toMap()
                                    )
                                }
                                updateUserInventoryData(
                                    UserInventoryData(
                                        empty_spaces = newEmptySpacesCount+exchangedResourceLeft,
                                        inventory =
                                        when (resourceType) {
                                            ResourceType.Wood -> {
                                                when (items?.get(typeName)?.currency_type) {
                                                    ResourceType.Wood -> {
                                                        userInventoryData?.inventory?.copy(
                                                            wood = newResourceItemsCount
                                                                    + (userInventoryData.inventory.wood
                                                                ?: 0)
                                                        )
                                                    }
                                                    ResourceType.Brick -> {
                                                        userInventoryData?.inventory?.copy(
                                                            wood = newResourceItemsCount
                                                                    + (userInventoryData.inventory.wood
                                                                ?: 0),
                                                            brick = exchangedResourceLeft,
                                                        )
                                                    }
                                                    ResourceType.Stone -> {
                                                        userInventoryData?.inventory?.copy(
                                                            wood = newResourceItemsCount
                                                                    + (userInventoryData.inventory.wood
                                                                ?: 0),
                                                            stone = exchangedResourceLeft,
                                                        )
                                                    }
                                                    ResourceType.Emerald -> {
                                                        userInventoryData?.inventory?.copy(
                                                            wood = newResourceItemsCount
                                                                    + (userInventoryData.inventory.wood
                                                                ?: 0),
                                                            emerald = exchangedResourceLeft,
                                                        )
                                                    }
                                                    null -> InventoryData()
                                                }
                                            }
                                            ResourceType.Brick -> {
                                                when (items?.get(typeName)?.currency_type) {
                                                    ResourceType.Wood -> {
                                                        userInventoryData?.inventory?.copy(
                                                            brick = newResourceItemsCount
                                                                    + (userInventoryData.inventory.brick
                                                                ?: 0),
                                                            wood = exchangedResourceLeft,
                                                        )
                                                    }
                                                    ResourceType.Brick -> {
                                                        userInventoryData?.inventory?.copy(
                                                            brick = newResourceItemsCount
                                                                    + (userInventoryData.inventory.brick
                                                                ?: 0),
                                                        )
                                                    }
                                                    ResourceType.Stone -> {
                                                        userInventoryData?.inventory?.copy(
                                                            brick = newResourceItemsCount
                                                                    + (userInventoryData.inventory.brick
                                                                ?: 0),
                                                            stone = exchangedResourceLeft,
                                                        )
                                                    }
                                                    ResourceType.Emerald -> {
                                                        userInventoryData?.inventory?.copy(
                                                            brick = newResourceItemsCount
                                                                    + (userInventoryData.inventory.brick
                                                                ?: 0),
                                                            emerald = exchangedResourceLeft,
                                                        )
                                                    }
                                                    null -> InventoryData()
                                                }
                                            }
                                            ResourceType.Stone -> {
                                                when (items?.get(typeName)?.currency_type) {
                                                    ResourceType.Wood -> {
                                                        userInventoryData?.inventory?.copy(
                                                            stone = newResourceItemsCount
                                                                    + (userInventoryData.inventory.stone
                                                                ?: 0),
                                                            wood = exchangedResourceLeft
                                                        )
                                                    }
                                                    ResourceType.Brick -> {
                                                        userInventoryData?.inventory?.copy(
                                                            stone = newResourceItemsCount
                                                                    + (userInventoryData.inventory.stone
                                                                ?: 0),
                                                            brick = exchangedResourceLeft,
                                                        )
                                                    }
                                                    ResourceType.Stone -> {
                                                        userInventoryData?.inventory?.copy(
                                                            stone = newResourceItemsCount
                                                                    + (userInventoryData.inventory.stone
                                                                ?: 0),
                                                        )
                                                    }
                                                    ResourceType.Emerald -> {
                                                        userInventoryData?.inventory?.copy(
                                                            stone = newResourceItemsCount
                                                                    + (userInventoryData.inventory.stone
                                                                ?: 0),
                                                            emerald = exchangedResourceLeft,
                                                        )
                                                    }
                                                    null -> InventoryData()
                                                }
                                            }
                                            ResourceType.Emerald -> {
                                                when (items?.get(typeName)?.currency_type) {
                                                    ResourceType.Wood -> {
                                                        userInventoryData?.inventory?.copy(
                                                            emerald = newResourceItemsCount
                                                                    + (userInventoryData.inventory.emerald
                                                                ?: 0),
                                                            wood = exchangedResourceLeft,
                                                        )
                                                    }
                                                    ResourceType.Brick -> {
                                                        userInventoryData?.inventory?.copy(
                                                            emerald = newResourceItemsCount
                                                                    + (userInventoryData.inventory.emerald
                                                                ?: 0),
                                                            brick = exchangedResourceLeft,
                                                        )
                                                    }
                                                    ResourceType.Stone -> {
                                                        userInventoryData?.inventory?.copy(
                                                            emerald = newResourceItemsCount
                                                                    + (userInventoryData.inventory.emerald
                                                                ?: 0),
                                                            stone = exchangedResourceLeft,
                                                        )
                                                    }
                                                    ResourceType.Emerald -> {
                                                        userInventoryData?.inventory?.copy(
                                                            emerald = newResourceItemsCount
                                                                    + (userInventoryData.inventory.emerald
                                                                ?: 0),
                                                        )
                                                    }
                                                    null -> InventoryData()
                                                }
                                            }
                                        }
                                    )
                                )
                            },
                            onDismissRequest = onDismissRequest,
                            exchangeCurrency = items?.get(typeName)?.price ?: 1,
                            exchangeResourceType = items?.get(typeName)?.currency_type,
                            exchangeResourceCount = when (items?.get(typeName)?.currency_type) {
                                ResourceType.Wood -> userInventoryData?.inventory?.wood ?: 0
                                ResourceType.Brick -> userInventoryData?.inventory?.brick ?: 0
                                ResourceType.Stone -> userInventoryData?.inventory?.stone ?: 0
                                ResourceType.Emerald -> userInventoryData?.inventory?.emerald ?: 0
                                null -> 0
                            }
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
    onBuy: (newEmptySpacesCount: Int, newResourceItemsCount: Int, resourceType: ResourceType, amountLeft: Int, exchangedResourceLeft: Int) -> Unit,
    onDismissRequest: () -> Unit,
    exchangeResourceType: ResourceType?,
    exchangeCurrency: Int,
    exchangeResourceCount: Int,
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
                customMessage = "$PRICE $exchangeCurrency ${exchangeResourceType}s",
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
                            itemsLeft - takeAmount.value.toInt(),
                            exchangeResourceCount - takeAmount.value.toInt() * exchangeCurrency
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
                        && exchangeResourceCount >= takeAmount.value.toInt() * exchangeCurrency
            )
        },
        cell11 = {
            CustomTextField(
                modifier = Modifier
                    .height(MaterialTheme.sizes.drop_item_dialog_amount_text_field_height)
                    .fillMaxWidth(.9f)
                    .padding(
                        start = MaterialTheme.sizes.drop_item_dialog_spacer,
                        end = 5.dp
                    ),
                value = takeAmount.value,
                onValueChange = {
                    if (it != "") {
                        if (itemsLeft != null
                            && itemsLeft >= it.toInt()
                            && exchangeResourceCount >= it.toInt() * exchangeCurrency
                            && (takeAmount.value == ""
                                    || (emptySpaces != null
                                    && emptySpaces - it.toInt() >= 0
                                    && it.toInt() > 0
                                    ))
                        )
                            takeAmount.value = removeLeadingZerosIfAny(it)
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