package mosis.streetsandtotems.feature_map.data.data_source

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.FirestoreConstants
import mosis.streetsandtotems.core.FirestoreConstants.MARKET_DOCUMENT_ID
import mosis.streetsandtotems.core.domain.model.UserData
import mosis.streetsandtotems.feature_map.domain.model.InventoryData
import mosis.streetsandtotems.feature_map.domain.model.MarketItem
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData

class FirebaseMapDataSource(private val db: FirebaseFirestore) {
    suspend fun addCustomPin(
        l: GeoPoint,
        visible_to: String,
        placed_by: String,
        text: String,
    ) {
        db.collection(FirestoreConstants.CUSTOM_PINS_COLLECTION).add(
            mapOf(
                "l" to l,
                "text" to text,
                "placed_by" to placed_by,
                "visible_to" to visible_to,
            )
        ).await()
    }

    suspend fun updateCustomPin(
        id: String,
        visible_to: String?,
        placed_by: String?,
        text: String?,
    ) {
        val data: MutableMap<String, Any> = mutableMapOf()
        if (visible_to != null) data["placed_by"] = placed_by as Any
        if (placed_by != null) data["placed_by"] = placed_by as Any
        if (text != null) data["text"] = text as Any

        if (data.isNotEmpty())
            db.collection(FirestoreConstants.CUSTOM_PINS_COLLECTION).document(id)
                .update(data).await()
    }

    suspend fun deleteCustomPin(id: String) {
        db.collection(FirestoreConstants.CUSTOM_PINS_COLLECTION).document(id).delete().await()
    }

    suspend fun addHome(myId: String, l: GeoPoint) {
        db.collection(FirestoreConstants.HOMES_COLLECTION).document(myId).set(
            mapOf(
                "l" to l,
                "inventory" to mapOf(
                    "wood" to 0,
                    "brick" to 0,
                    "stone" to 0,
                    "emerald" to 0,
                    "totem" to 0,
                ),
            )
        ).await()
    }

    suspend fun updateHome(
        homeId: String,
        newInventoryData: InventoryData? = null,
        l: GeoPoint? = null
    ) {
        val data: MutableMap<String, Any> = mutableMapOf()
        if (newInventoryData != null) data["inventory"] = newInventoryData
        if (l != null) data["l"] = l

        if (data.isNotEmpty())
            db.collection(FirestoreConstants.HOMES_COLLECTION).document(homeId)
                .update(data).await()
    }

    suspend fun deleteHome(myId: String) {
        db.collection(FirestoreConstants.HOMES_COLLECTION).document(myId).delete().await()

    }

    suspend fun updateResource(resourceId: String, remaining: Int) {
        db.collection(FirestoreConstants.RESOURCES_COLLECTION).document(resourceId)
            .update(
                mapOf("remaining" to remaining)
            ).await()
    }

    suspend fun deleteResource(resourceId: String) {
        db.collection(FirestoreConstants.RESOURCES_COLLECTION).document(resourceId).delete().await()
    }

    suspend fun getUserInventory(userId: String): UserInventoryData? {
        return db.collection(FirestoreConstants.USER_INVENTORY_COLLECTION).document(userId).get()
            .await()
            .toObject(UserInventoryData::class.java)
    }

    suspend fun updateUserInventory(
        myId: String,
        newUserInventoryData: UserInventoryData
    ) {
        db.collection(FirestoreConstants.USER_INVENTORY_COLLECTION).document(myId)
            .set(newUserInventoryData).await()
    }

    fun updateUserOnlineStatus(isOnline: Boolean, userId: String): Task<Void> {
        return db.collection(FirestoreConstants.PROFILE_DATA_COLLECTION).document(userId)
            .update(FirestoreConstants.IS_ONLINE_FIELD, isOnline)
    }

    suspend fun getUserData(userId: String): UserData? {
        return db.collection(FirestoreConstants.PROFILE_DATA_COLLECTION).document(userId).get()
            .await().toObject(UserData::class.java)
    }

    suspend fun updateMarket(newMarket: Map<String, MarketItem>) {
        db.collection(FirestoreConstants.MARKET_COLLECTION).document(MARKET_DOCUMENT_ID)
            .update(mapOf("items" to newMarket))
    }

}