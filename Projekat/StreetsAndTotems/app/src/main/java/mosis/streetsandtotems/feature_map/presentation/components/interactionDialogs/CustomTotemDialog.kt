package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.*
import mosis.streetsandtotems.core.TitleConstants.BACKPACK_INVENTORY
import mosis.streetsandtotems.core.TitleConstants.VALUE_POINTS
import mosis.streetsandtotems.core.TotemDialogConstants.POINTS
import mosis.streetsandtotems.core.TotemDialogConstants.POINTS_NEEDED_FOR_NEXT_LEVEL
import mosis.streetsandtotems.core.TotemDialogConstants.TOTEM_STRENGTH
import mosis.streetsandtotems.core.presentation.components.*
import mosis.streetsandtotems.core.presentation.utils.drawVerticalScrollbar
import mosis.streetsandtotems.feature_map.domain.model.*
import mosis.streetsandtotems.feature_map.presentation.components.CustomUserInventory
import mosis.streetsandtotems.feature_map.presentation.util.convertResourceTypeToIconType
import mosis.streetsandtotems.feature_map.presentation.util.getNextLevelPoints
import mosis.streetsandtotems.feature_map.presentation.util.getProtectionLevelFromPoints
import mosis.streetsandtotems.feature_map.presentation.util.removeLeadingZerosIfAny
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomTotemDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit = {},
    userInventoryData: UserInventoryData?,//korisnicki
    updateUserInventoryData: (newUserInventoryData: UserInventoryData) -> Unit,
    updateTotem: (newTotem: TotemData) -> Unit,
    currentTotem: TotemData,
    onHarvestTotemPoints: () -> Unit,
    onPickUpTotem: () -> Unit,
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
                        .size(130.dp)
                        .padding(bottom = 10.dp),
                    title = {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.tiki),
                            contentDescription = ImageContentDescriptionConstants.TOTEM,
                            modifier = Modifier.size(MaterialTheme.sizes.drop_item_dialog_icon_size)
                        )
                    },
                    cell00 = {
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            text = TOTEM_STRENGTH + getProtectionLevelFromPoints(currentTotem.protection_points),
                            textAlign = TextAlign.Center,
                        )
                    },
                    cell11 = {
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            text = POINTS_NEEDED_FOR_NEXT_LEVEL +
                                    getNextLevelPoints(currentTotem.protection_points).toString()
                                    + POINTS,
                            textAlign = TextAlign.Center,
                        )
                    }
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
            LazyColumn(Modifier.drawVerticalScrollbar(rememberScrollState())) {
                item {
                    ItemTotem(
                        emptySpaces = userInventoryData?.empty_spaces ?: 0,
                        resourceType = ResourceType.Wood,
                        onInvestItem = { newEmptySpacesCount: Int, newResourceItemsCount: Int, addedPoints: Int ->
                            updateTotem(
                                currentTotem.copy(
                                    protection_points =
                                    addedPoints + (currentTotem.protection_points ?: 0)
                                )
                            )
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
                    ItemTotem(
                        emptySpaces = userInventoryData?.empty_spaces ?: 0,
                        resourceType = ResourceType.Brick,
                        onInvestItem = { newEmptySpacesCount: Int, newResourceItemsCount: Int, addedPoints: Int ->
                            updateTotem(
                                currentTotem.copy(
                                    protection_points =
                                    addedPoints + (currentTotem.protection_points ?: 0)
                                )
                            )
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
                    ItemTotem(
                        emptySpaces = userInventoryData?.empty_spaces ?: 0,
                        resourceType = ResourceType.Stone,
                        onInvestItem = { newEmptySpacesCount: Int, newResourceItemsCount: Int, addedPoints: Int ->
                            updateTotem(
                                currentTotem.copy(
                                    protection_points =
                                    addedPoints + (currentTotem.protection_points ?: 0)
                                )
                            )
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
                    ItemTotem(
                        emptySpaces = userInventoryData?.empty_spaces ?: 0,
                        resourceType = ResourceType.Emerald,
                        onInvestItem = { newEmptySpacesCount: Int, newResourceItemsCount: Int, addedPoints: Int ->
                            updateTotem(
                                currentTotem.copy(
                                    protection_points =
                                    addedPoints + (currentTotem.protection_points ?: 0)
                                )
                            )
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
            }
        },
        buttonType = CustomButtonType.Outlined,
        onConfirmButtonClick = {
            onHarvestTotemPoints()
            onDismissRequest()
        },
        confirmButtonText = ButtonConstants.HARVEST,
        confirmButtonVisible = true,
        confirmButtonMatchParentWidth = false,

        onDismissButtonClick = {
            onPickUpTotem()
            onDismissRequest()
        },
        dismissButtonText = ButtonConstants.PICK_UP_TOTEM,
        dismissButtonVisible = true,
        dismissButtonMatchParentWidth = false,
        dismissButtonEnabled = (userInventoryData?.empty_spaces ?: 0) > 0,

        clickable = true,
    )
}


@Composable
private fun ItemTotem(
    emptySpaces: Int?,
    resourceType: ResourceType,
    onInvestItem: (newEmptySpacesCount: Int, newResourceItemsCount: Int, addedPoints: Int) -> Unit,
    onDismissRequest: () -> Unit,
    backpackItemCount: Int,
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
                resourceType = convertResourceTypeToIconType(resourceType),
                countMessage = VALUE_POINTS + when (resourceType) {
                    ResourceType.Wood -> PointsConversion.WOOD
                    ResourceType.Brick -> PointsConversion.BRICK
                    ResourceType.Stone -> PointsConversion.STONE
                    ResourceType.Emerald -> PointsConversion.EMERALD
                },
                needTotemAdditionalText = false,
            )
        },
        cell10 = {
            CustomButton(
                buttonModifier = Modifier.padding(5.dp),
                clickHandler = {
                    if (emptySpaces != null) {
                        onInvestItem(
                            emptySpaces + takeAmount.value.toInt(),
                            takeAmount.value.toInt() * (-1),
                            takeAmount.value.toInt() * when (resourceType) {
                                ResourceType.Wood -> PointsConversion.WOOD
                                ResourceType.Brick -> PointsConversion.BRICK
                                ResourceType.Stone -> PointsConversion.STONE
                                ResourceType.Emerald -> PointsConversion.EMERALD
                            },
                        )
                    }
                    onDismissRequest()
                },
                text = ButtonConstants.INVEST,
                contentPadding = ButtonDefaults.ContentPadding,
                buttonType = CustomButtonType.Outlined,
                textStyle = MaterialTheme.typography.titleMedium,
                enabled = takeAmount.value != "" && takeAmount.value.toInt() > 0 && backpackItemCount >= takeAmount.value.toInt()
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
                        if ((takeAmount.value == "" || (emptySpaces != null && emptySpaces - it.toInt() >= 0 && it.toInt() > 0)) || backpackItemCount >= takeAmount.value.toInt()) takeAmount.value =
                            removeLeadingZerosIfAny(it)

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