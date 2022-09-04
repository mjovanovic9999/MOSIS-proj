package mosis.streetsandtotems.feature_map.data.data_source

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.getField
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.FirestoreConstants
import mosis.streetsandtotems.feature_map.domain.model.ResourceData
import mosis.streetsandtotems.feature_map.domain.model.ResourceType
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData

class FirebaseMapDataSource(private val db: FirebaseFirestore) {
    fun addCustomPin(
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
        )
    }

    fun updateCustomPin(
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
                .update(data)
    }

    fun deleteCustomPin(id: String) {
        db.collection(FirestoreConstants.CUSTOM_PINS_COLLECTION).document(id).delete()
    }

    fun addHome(myId: String, l: GeoPoint) {
        db.collection(FirestoreConstants.HOMES_COLLECTION).document(myId).set(
            mapOf(
                "l" to l,
                "inventory" to mapOf(
                    "wood" to 0,
                    "brick" to 0,
                    "stone" to 0,
                    "emerald" to 0,
                    "totem" to 1,
                ),
            )
        )
    }

    fun updateHome() {}

    fun deleteHome(myId: String) {//nestovano se drugacije brise?????????
        db.collection(FirestoreConstants.HOMES_COLLECTION).document(myId).delete()

    }


    fun updateResource(resourceId: String, remaining: Int) {
        db.collection(FirestoreConstants.RESOURCES_COLLECTION).document(resourceId)
            .update(
                mapOf("remaining" to remaining)
            )
    }

    fun deleteResource(resourceId: String) {
        db.collection(FirestoreConstants.RESOURCES_COLLECTION).document(resourceId).delete()
    }

    suspend fun getPlayerInventory(userId: String): UserInventoryData? {
        return db.collection(FirestoreConstants.USER_INVENTORY).document(userId).get().await()
            .toObject(UserInventoryData::class.java)


    }

    fun updateUserInventory(
        myId: String,
        newUserInventoryData: UserInventoryData
    ) {
        db.collection(FirestoreConstants.USER_INVENTORY).document(myId).set(newUserInventoryData)
    }
}