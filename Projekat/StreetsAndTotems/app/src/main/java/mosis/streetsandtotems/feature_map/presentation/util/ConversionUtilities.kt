package mosis.streetsandtotems.feature_map.presentation.util

import mosis.streetsandtotems.core.presentation.components.IconType
import mosis.streetsandtotems.feature_map.domain.model.InventoryData
import mosis.streetsandtotems.feature_map.domain.model.ResourceType
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData

fun convertResourceTypeToIconType(resourceType: ResourceType?): IconType =
    when (resourceType) {
        ResourceType.Brick -> IconType.ResourceType.Brick
        ResourceType.Wood -> IconType.ResourceType.Wood
        ResourceType.Stone -> IconType.ResourceType.Stone
        ResourceType.Emerald -> IconType.ResourceType.Emerald
        null -> IconType.OtherType.Home
    }


fun getCountResourceTypeFromInventory(
    inventoryData: InventoryData,
    resourceType: ResourceType?
): Int? =
    when (resourceType) {
        ResourceType.Wood -> inventoryData.wood
        ResourceType.Brick -> inventoryData.brick
        ResourceType.Stone -> inventoryData.stone
        ResourceType.Emerald -> inventoryData.emerald
        null -> 0
    }

fun updateOneInventoryData(
    oldInventoryData: InventoryData, newCount: Int, updateType: ResourceType
): InventoryData =
    when (updateType) {
        ResourceType.Wood -> oldInventoryData.copy(wood = newCount)
        ResourceType.Brick -> oldInventoryData.copy(brick = newCount)
        ResourceType.Stone -> oldInventoryData.copy(stone = newCount)
        ResourceType.Emerald -> oldInventoryData.copy(emerald = newCount)
    }


fun removeLeadingZerosIfAny(value:String):String{
    return if (value.length == 1)
        value
    else if (value.first() == '0') {
        value.toInt().toString()
    }
    else ""
}