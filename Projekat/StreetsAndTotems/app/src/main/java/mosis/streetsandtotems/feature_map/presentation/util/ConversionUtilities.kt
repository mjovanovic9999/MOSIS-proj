package mosis.streetsandtotems.feature_map.presentation.util

import android.util.Log
import com.google.firebase.Timestamp
import mosis.streetsandtotems.core.PointsConversion
import mosis.streetsandtotems.core.PointsConversion.HOURS_TO_POINTS_CONVERSION
import mosis.streetsandtotems.core.ProtectionLevelConstants
import mosis.streetsandtotems.core.domain.model.PrivacySettings
import mosis.streetsandtotems.core.presentation.components.IconType
import mosis.streetsandtotems.feature_map.domain.model.InventoryData
import mosis.streetsandtotems.feature_map.domain.model.ProtectionLevel
import mosis.streetsandtotems.feature_map.domain.model.ResourceType
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun convertResourceTypeToIconType(resourceType: ResourceType?): IconType = when (resourceType) {
    ResourceType.Brick -> IconType.ResourceType.Brick
    ResourceType.Wood -> IconType.ResourceType.Wood
    ResourceType.Stone -> IconType.ResourceType.Stone
    ResourceType.Emerald -> IconType.ResourceType.Emerald
    null -> IconType.OtherType.Home
}


fun getCountResourceTypeFromInventory(
    inventoryData: InventoryData, resourceType: ResourceType?
): Int? = when (resourceType) {
    ResourceType.Wood -> inventoryData.wood
    ResourceType.Brick -> inventoryData.brick
    ResourceType.Stone -> inventoryData.stone
    ResourceType.Emerald -> inventoryData.emerald
    null -> 0
}

fun updateOneInventoryData(
    oldInventoryData: InventoryData, newCount: Int, updateType: ResourceType
): InventoryData = when (updateType) {
    ResourceType.Wood -> oldInventoryData.copy(wood = newCount)
    ResourceType.Brick -> oldInventoryData.copy(brick = newCount)
    ResourceType.Stone -> oldInventoryData.copy(stone = newCount)
    ResourceType.Emerald -> oldInventoryData.copy(emerald = newCount)
}


fun removeLeadingZerosIfAny(value: String): String {
    return if (value.length == 1) value
    else if (value.first() == '0') {
        value.toInt().toString()
    } else value
}

fun getNextLevelPoints(currentPoints: Int?): Int = if (currentPoints == null) PointsConversion.LOW
else if (currentPoints < PointsConversion.LOW) PointsConversion.LOW - currentPoints
else if (currentPoints < PointsConversion.MEDIUM) PointsConversion.MEDIUM - currentPoints
else if (currentPoints < PointsConversion.HIGH) PointsConversion.HIGH - currentPoints
else 0


fun getProtectionLevelFromPoints(points: Int?): String =
    if (points == null || points < PointsConversion.LOW) ProtectionLevelConstants.UNPROTECTED
    else if (points < PointsConversion.MEDIUM) ProtectionLevelConstants.LOW
    else if (points < PointsConversion.HIGH) ProtectionLevelConstants.MEDIUM
    else ProtectionLevelConstants.HIGH

fun getProtectionLevelFromPointsNoUnprotected(points: Int): ProtectionLevel.RiddleProtectionLevel =
    if (points < PointsConversion.MEDIUM) ProtectionLevel.RiddleProtectionLevel.Low
    else if (points < PointsConversion.HIGH) ProtectionLevel.RiddleProtectionLevel.Medium
    else ProtectionLevel.RiddleProtectionLevel.High

fun calculateTotemTimePoints(lastVisited: Timestamp?): Int =
    if (lastVisited != null)
        ((Timestamp.now().seconds - lastVisited.seconds) / 3600 * HOURS_TO_POINTS_CONVERSION).toInt()
    else 0

fun shouldEnableNumber(
    settings: PrivacySettings?,
    mySquadId: String,
    selectedPlayerSquadId: String?,
    phone_number: String?,
): Boolean =
    if (phone_number == null)
        false
    else
        when (settings) {
            PrivacySettings.NoOne -> false
            PrivacySettings.OnlySquadMembers -> isSquadMember(mySquadId, selectedPlayerSquadId)
            PrivacySettings.Everyone -> true
            null -> false
        }

fun isSquadMember(mySquadId: String, selectedPlayerSquadId: String?): Boolean =
    mySquadId != "" && selectedPlayerSquadId == mySquadId