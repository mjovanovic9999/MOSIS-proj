package mosis.streetsandtotems.feature_map.data.data_source

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.FireStoreConstants
import mosis.streetsandtotems.core.FireStoreConstants.EASY_RIDDLES_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.HARD_RIDDLES_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.ITEM_COUNT
import mosis.streetsandtotems.core.FireStoreConstants.LEADERBOARD_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.MARKET_DOCUMENT_ID
import mosis.streetsandtotems.core.FireStoreConstants.MEDIUM_RIDDLES_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.ORDER_NUMBER
import mosis.streetsandtotems.core.FireStoreConstants.RIDDLE_COUNT_VALUE
import mosis.streetsandtotems.feature_map.domain.model.*
import kotlin.random.Random

class FirebaseMapDataSource(private val db: FirebaseFirestore) {
    suspend fun addCustomPin(
        l: GeoPoint,
        visible_to: String,
        placed_by: String,
        text: String,
    ) {
        db.collection(FireStoreConstants.CUSTOM_PINS_COLLECTION).add(
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

        if (data.isNotEmpty()) db.collection(FireStoreConstants.CUSTOM_PINS_COLLECTION).document(id)
            .update(data).await()
    }

    suspend fun deleteCustomPin(id: String) {
        db.collection(FireStoreConstants.CUSTOM_PINS_COLLECTION).document(id).delete().await()
    }

    suspend fun addHome(myId: String, l: GeoPoint) {
        db.collection(FireStoreConstants.HOMES_COLLECTION).document(myId).set(
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
        homeId: String, newInventoryData: InventoryData? = null, l: GeoPoint? = null
    ) {
        val data: MutableMap<String, Any> = mutableMapOf()
        if (newInventoryData != null) data["inventory"] = newInventoryData
        if (l != null) data["l"] = l

        if (data.isNotEmpty()) db.collection(FireStoreConstants.HOMES_COLLECTION).document(homeId)
            .update(data).await()
    }

    suspend fun deleteHome(myId: String) {
        db.collection(FireStoreConstants.HOMES_COLLECTION).document(myId).delete().await()

    }

    suspend fun updateResource(resourceId: String, remaining: Int) {
        db.collection(FireStoreConstants.RESOURCES_COLLECTION).document(resourceId).update(
            mapOf("remaining" to remaining)
        ).await()
    }

    suspend fun deleteResource(resourceId: String) {
        db.collection(FireStoreConstants.RESOURCES_COLLECTION).document(resourceId).delete().await()
    }

    suspend fun getUserInventory(userId: String): UserInventoryData? {
        return db.collection(FireStoreConstants.USER_INVENTORY_COLLECTION).document(userId).get()
            .await().toObject(UserInventoryData::class.java)
    }

    suspend fun updateUserInventory(
        myId: String, newUserInventoryData: UserInventoryData
    ) {
        db.collection(FireStoreConstants.USER_INVENTORY_COLLECTION).document(myId)
            .set(newUserInventoryData).await()
    }

    fun updateUserOnlineStatus(isOnline: Boolean, userId: String): Task<Void> {
        return db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION).document(userId)
            .update(FireStoreConstants.IS_ONLINE_FIELD, isOnline)
    }

    suspend fun getUserData(userId: String): UserData? {
        return db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION).document(userId).get()
            .await().toObject(UserData::class.java)
    }

    suspend fun updateMarket(newMarket: Map<String, MarketItem>) {
        db.collection(FireStoreConstants.MARKET_COLLECTION).document(MARKET_DOCUMENT_ID)
            .update(mapOf("items" to newMarket)).await()
    }

    suspend fun updateTotem(totemId: String, newTotem: TotemData) {
        db.collection(FireStoreConstants.TOTEMS_COLLECTION).document(totemId).set(newTotem).await()
    }

    suspend fun deleteTotem(totemId: String) {
        db.collection(FireStoreConstants.TOTEMS_COLLECTION).document(totemId).delete().await()
    }

    suspend fun getRiddle(protectionLevel: ProtectionLevel.RiddleProtectionLevel): RiddleData? {
        val collection = when (protectionLevel) {
            ProtectionLevel.RiddleProtectionLevel.Low -> EASY_RIDDLES_COLLECTION
            ProtectionLevel.RiddleProtectionLevel.Medium -> MEDIUM_RIDDLES_COLLECTION
            ProtectionLevel.RiddleProtectionLevel.High -> HARD_RIDDLES_COLLECTION
        }

        val count = db.collection(collection).document(ITEM_COUNT).get().await()
            .getLong(RIDDLE_COUNT_VALUE)
        val riddles =
            db.collection(collection)
                .whereEqualTo(
                    ORDER_NUMBER,
                    Random.nextInt(0, count?.toInt() ?: 10)
                )
                .get()
                .await().toObjects(RiddleData::class.java)

        return riddles[0]
    }

    suspend fun updateLeaderboard(userId: String, addLeaderboardPoints: Int) {
        db.runTransaction {
            val docRef = db.collection(LEADERBOARD_COLLECTION).document(userId)
            val points = it.get(docRef).getLong("points")
            it.update(docRef, "points", (points ?: 0) + addLeaderboardPoints)
        }.await()

    }

    suspend fun onCorrectAnswerHandler() {}//vrv ne treba

    suspend fun onIncorrectAnswerHandler() {}
}

