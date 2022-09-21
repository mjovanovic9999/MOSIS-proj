package mosis.streetsandtotems.feature_map.domain.repository

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.feature_map.domain.model.*

interface MapViewModelRepository {
    suspend fun addCustomPin(l: GeoPoint, visible_to: String, placed_by: String, text: String)

    suspend fun updateCustomPin(
        id: String,
        visible_to: String?,
        placed_by: String?,
        text: String?,
    )

    suspend fun deleteCustomPin(id: String)

    suspend fun addHome(l: GeoPoint)

    suspend fun deleteHome()

    suspend fun updateResource(resourceId: String, newCount: Int)

    suspend fun updateUserInventory(newUserInventoryData: UserInventoryData)

    suspend fun updateMarket(newMarket: Map<String, MarketItem>)

    suspend fun updateHomeInventory(homeId: String, newInventoryData: InventoryData)

    suspend fun updateHomeLocation(homeId: String, l: GeoPoint)

    suspend fun updateTotem(totemId: String, newTotem: TotemData)

    suspend fun deleteTotem(totemId: String)

    suspend fun getRiddle(protectionLevel: ProtectionLevel.RiddleProtectionLevel): RiddleData?

    suspend fun updateLeaderboard(userId: String, addLeaderboardPoints: Int)

    suspend fun updateSquadLeaderboard(squadId: String, addSquadLeaderboardPoints: Int)

//    suspend fun registerCallbackOnUserInventoryUpdate(callback: (UserInventoryData) -> Unit)
}