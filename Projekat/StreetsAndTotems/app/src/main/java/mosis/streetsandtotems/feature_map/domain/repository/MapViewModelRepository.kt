package mosis.streetsandtotems.feature_map.domain.repository

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.feature_map.domain.model.InventoryData
import mosis.streetsandtotems.feature_map.domain.model.MarketItem
import mosis.streetsandtotems.feature_map.domain.model.ResourceType
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData

interface MapViewModelRepository {
    suspend fun addCustomPin(l: GeoPoint, visible_to: String, placed_by: String, text: String)

    suspend fun updateCustomPin(
        id: String,
        visible_to: String?,
        placed_by: String?,
        text: String?,
    )

    suspend fun deleteCustomPin(id: String)

    suspend fun addHome(myId: String, l: GeoPoint)

    suspend fun deleteHome(myId: String)

    suspend fun updateResource(resourceId: String, newCount: Int)

    suspend fun updateUserInventory(myId: String, newUserInventoryData: UserInventoryData)

    suspend fun updateMarket(newMarket: Map<String, MarketItem>)

    suspend fun updateHomeInventory(homeId: String, newInventoryData: InventoryData)

//    suspend fun registerCallbackOnUserInventoryUpdate(callback: (UserInventoryData) -> Unit)
}