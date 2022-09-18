package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import mosis.streetsandtotems.core.presentation.components.*
import mosis.streetsandtotems.core.presentation.utils.drawVerticalScrollbar
import mosis.streetsandtotems.feature_map.domain.model.InventoryData
import mosis.streetsandtotems.feature_map.domain.model.ResourceType
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData
import mosis.streetsandtotems.feature_map.presentation.components.CustomUserInventory
import mosis.streetsandtotems.feature_map.presentation.util.convertResourceTypeToIconType
import mosis.streetsandtotems.feature_map.presentation.util.removeLeadingZerosIfAny
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomHomeDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit = {},
    inventoryData: InventoryData?,
    userInventoryData: UserInventoryData?,//korisnicki
    updateUserInventoryData: (newUserInventoryData: UserInventoryData) -> Unit,
    updateHomeItems: (inventoryData: InventoryData) -> Unit,
) {
    CustomDialog(
        isOpen = isOpen,
        modifier = Modifier
            .fillMaxWidth(MaterialTheme.sizes.drop_item_dialog_width_extended)
            .padding(top = 15.dp, bottom = 15.dp),
        onDismissRequest = onDismissRequest,

        title = {
            Column {
                CustomAreaHelper(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(60.dp)
                        .padding(bottom = 10.dp),
                    cell00 = {
                        Text(
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                            text = PinConstants.HOME,
                            textAlign = TextAlign.Center,
                        )
                    },
                )
                CustomUserInventory(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(130.dp),
                    woodCount = userInventoryData?.inventory?.wood ?: 0,
                    brickCount = userInventoryData?.inventory?.brick ?: 0,
                    emeraldCount = userInventoryData?.inventory?.emerald ?: 0,
                    stoneCount = userInventoryData?.inventory?.stone ?: 0,
                    emptySpaceCount = userInventoryData?.empty_spaces ?: 0,
                    title = BACKPACK_INVENTORY,
                    totemsCount = userInventoryData?.inventory?.totem ?: 0
                )
            }
        },
        text = {
            if (inventoryData != null)
                LazyColumn(Modifier.drawVerticalScrollbar(rememberScrollState())) {
                    item {
                        ItemHome(
                            itemsLeft = inventoryData.wood ?: 0,
                            emptySpaces = userInventoryData?.empty_spaces ?: 0,
                            resourceType = ResourceType.Wood,
                            onMoveItem = { newEmptySpacesCount: Int, newResourceItemsCount: Int, amountLeft: Int ->
                                updateHomeItems(inventoryData.copy(wood = amountLeft))
                                updateUserInventoryData(
                                    UserInventoryData(
                                        empty_spaces = newEmptySpacesCount,
                                        inventory =
                                        userInventoryData?.inventory?.copy(
                                            wood = newResourceItemsCount
                                                    + (userInventoryData.inventory.wood ?: 0)
                                        )
                                    )
                                )
                            },
                            onDismissRequest = onDismissRequest,
                            backpackItemCount = (userInventoryData?.inventory?.wood ?: 0),
                        )
                    }
                    item {
                        ItemHome(
                            itemsLeft = inventoryData.stone ?: 0,
                            emptySpaces = userInventoryData?.empty_spaces ?: 0,
                            resourceType = ResourceType.Stone,
                            onMoveItem = { newEmptySpacesCount: Int, newResourceItemsCount: Int, amountLeft: Int ->
                                updateHomeItems(inventoryData.copy(stone = amountLeft))
                                updateUserInventoryData(
                                    UserInventoryData(
                                        empty_spaces = newEmptySpacesCount,
                                        inventory =
                                        userInventoryData?.inventory?.copy(
                                            stone = newResourceItemsCount
                                                    + (userInventoryData.inventory.stone ?: 0)
                                        )
                                    )
                                )
                            },
                            onDismissRequest = onDismissRequest,
                            backpackItemCount = (userInventoryData?.inventory?.stone ?: 0),

                            )
                    }
                    item {
                        ItemHome(
                            itemsLeft = inventoryData.brick ?: 0,
                            emptySpaces = userInventoryData?.empty_spaces ?: 0,
                            resourceType = ResourceType.Brick,
                            onMoveItem = { newEmptySpacesCount: Int, newResourceItemsCount: Int, amountLeft: Int ->
                                updateHomeItems(inventoryData.copy(brick = amountLeft))
                                updateUserInventoryData(
                                    UserInventoryData(
                                        empty_spaces = newEmptySpacesCount,
                                        inventory =
                                        userInventoryData?.inventory?.copy(
                                            brick = newResourceItemsCount
                                                    + (userInventoryData.inventory.brick ?: 0)
                                        )
                                    )
                                )
                            },
                            onDismissRequest = onDismissRequest,
                            backpackItemCount = (userInventoryData?.inventory?.brick ?: 0),
                        )
                    }
                    item {
                        ItemHome(
                            itemsLeft = inventoryData.emerald ?: 0,
                            emptySpaces = userInventoryData?.empty_spaces ?: 0,
                            resourceType = ResourceType.Emerald,
                            onMoveItem = { newEmptySpacesCount: Int, newResourceItemsCount: Int, amountLeft: Int ->
                                updateHomeItems(inventoryData.copy(emerald = amountLeft))
                                updateUserInventoryData(
                                    UserInventoryData(
                                        empty_spaces = newEmptySpacesCount,
                                        inventory =
                                        userInventoryData?.inventory?.copy(
                                            emerald = newResourceItemsCount
                                                    + (userInventoryData.inventory.emerald ?: 0)
                                        )
                                    )
                                )
                            },
                            onDismissRequest = onDismissRequest,
                            backpackItemCount = (userInventoryData?.inventory?.emerald ?: 0),
                        )
                    }
                    item {
                        ItemHome(
                            itemsLeft = inventoryData.totem ?: 0,
                            emptySpaces = userInventoryData?.empty_spaces ?: 0,
                            onMoveItem = { newEmptySpacesCount: Int, newResourceItemsCount: Int, amountLeft: Int ->
                                updateHomeItems(inventoryData.copy(totem = amountLeft))
                                updateUserInventoryData(
                                    UserInventoryData(
                                        empty_spaces = newEmptySpacesCount,
                                        inventory =
                                        userInventoryData?.inventory?.copy(
                                            totem = newResourceItemsCount
                                                    + (userInventoryData.inventory.totem ?: 0)
                                        )
                                    )
                                )
                            },
                            onDismissRequest = onDismissRequest,
                            backpackItemCount = (userInventoryData?.inventory?.emerald ?: 0),
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
private fun ItemHome(
    itemsLeft: Int?,
    emptySpaces: Int?,
    resourceType: ResourceType? = null,
    onMoveItem: (newEmptySpacesCount: Int, newResourceItemsCount: Int, amountLeft: Int) -> Unit,
    onDismissRequest: () -> Unit,
    backpackItemCount: Int,
) {
    val takeAmount = mutableStateOf(FormFieldConstants.DEFAULT_AMOUNT)

    CustomAreaHelper(
        modifier = Modifier
            .fillMaxWidth()
            .size(200.dp)
            .padding(5.dp),
        shouldAlignBottom = true,
        topPadding = false,
        title = {
            CustomDialogTitle(
                isTotem = resourceType == null,
                resourceType = convertResourceTypeToIconType(resourceType),
                countMessage = itemsLeft.toString() + TitleConstants.ITEMS_LEFT,
                needTotemAdditionalText = false,
            )
        },
        cell10 = {
            Row {
                CustomButton(
                    buttonModifier = Modifier.padding(5.dp),
                    clickHandler = {
                        if (emptySpaces != null && itemsLeft != null) {
                            onMoveItem(
                                emptySpaces - takeAmount.value.toInt(),
                                takeAmount.value.toInt(),
                                itemsLeft - takeAmount.value.toInt()
                            )
                        }
                        onDismissRequest()
                    },
                    text = ButtonConstants.TAKE,
                    contentPadding = ButtonDefaults.ContentPadding,
                    buttonType = CustomButtonType.Outlined,
                    textStyle = MaterialTheme.typography.titleMedium,
                    enabled = takeAmount.value != ""
                            && takeAmount.value.toInt() > 0
                            && emptySpaces != null && emptySpaces > 0
                            && itemsLeft != null
                            && itemsLeft >= takeAmount.value.toInt()

                )
                CustomButton(
                    buttonModifier = Modifier.padding(5.dp),
                    clickHandler = {
                        if (emptySpaces != null && itemsLeft != null) {
                            onMoveItem(
                                emptySpaces + takeAmount.value.toInt(),
                                takeAmount.value.toInt() * (-1),
                                itemsLeft + takeAmount.value.toInt()
                            )
                        }
                        onDismissRequest()
                    },
                    text = ButtonConstants.PUT,
                    contentPadding = ButtonDefaults.ContentPadding,
                    buttonType = CustomButtonType.Outlined,
                    textStyle = MaterialTheme.typography.titleMedium,
                    enabled = takeAmount.value != ""
                            && takeAmount.value.toInt() > 0
                            && backpackItemCount >= takeAmount.value.toInt()
                )
            }
        },
        cell00 = {
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
                        if (
                            (itemsLeft != null
                                    && itemsLeft >= it.toInt()
                                    && (takeAmount.value == ""
                                    || (emptySpaces != null
                                    && emptySpaces - it.toInt() >= 0
                                    && it.toInt() > 0
                                    )
                                    ))
                            || backpackItemCount >= it.toInt()
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