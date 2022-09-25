package mosis.streetsandtotems.feature_backpack.domain.repository

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.core.presentation.components.IconType
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData

interface BackpackRepository {

    suspend fun dropItem(
        l: GeoPoint,
        itemCount: Int,
        type: IconType.ResourceType?,
        oldInventory: UserInventoryData,
    )
}