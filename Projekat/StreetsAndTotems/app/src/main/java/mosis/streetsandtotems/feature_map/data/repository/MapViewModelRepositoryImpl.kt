package mosis.streetsandtotems.feature_map.data.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.FirestoreConstants
import mosis.streetsandtotems.core.domain.model.UserData
import mosis.streetsandtotems.feature_map.data.data_source.FirebaseMapDataSource
import mosis.streetsandtotems.feature_map.domain.model.InventoryData
import mosis.streetsandtotems.feature_map.domain.model.MarketItem
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData
import mosis.streetsandtotems.feature_map.domain.repository.MapViewModelRepository

class MapViewModelRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firebaseMapDataSource: FirebaseMapDataSource
) : MapViewModelRepository {
    override suspend fun addCustomPin(
        l: GeoPoint,
        visible_to: String,
        placed_by: String,
        text: String,
    ) {
        auth.currentUser?.let {
            firebaseMapDataSource.addCustomPin(l, visible_to, placed_by, text)
        }
    }

    override suspend fun updateCustomPin(
        id: String,
        visible_to: String?,
        placed_by: String?,
        text: String?,
    ) {
        auth.currentUser?.let {
            firebaseMapDataSource.updateCustomPin(id, visible_to, placed_by, text)
        }
    }

    override suspend fun deleteCustomPin(id: String) {
        auth.currentUser?.let {
            firebaseMapDataSource.deleteCustomPin(id)
        }
    }

    override suspend fun addHome(myId: String, l: GeoPoint) {
        auth.currentUser?.let {
            firebaseMapDataSource.addHome(myId, l)
        }
    }

    override suspend fun deleteHome(myId: String) {
        auth.currentUser?.let {
            firebaseMapDataSource.deleteHome(myId)
        }
    }

    override suspend fun updateResource(resourceId: String, newCount: Int) {
        auth.currentUser?.let {
            if (newCount <= 0)
                firebaseMapDataSource.deleteResource(resourceId)
            else
                firebaseMapDataSource.updateResource(resourceId, newCount)
        }
    }

    override suspend fun updateUserInventory(
        myId: String,
        newUserInventoryData: UserInventoryData
    ) {
        firebaseMapDataSource.updateUserInventory(myId, newUserInventoryData)
    }

    override suspend fun updateMarket(newMarket: Map<String, MarketItem>) {
        firebaseMapDataSource.updateMarket(newMarket)
    }

    override suspend fun updateHomeInventory(homeId: String, newInventoryData: InventoryData) {
        firebaseMapDataSource.updateHome(homeId, newInventoryData)
    }


//    override suspend fun registerCallbackOnUserInventoryUpdate(userId: String): UserInventoryData? {
//        return try {
//            firebaseMapDataSource.getUserInventory(userId)
//        } catch (e: Exception) {
//            Log.d("tag", e.message.toString())
//            null
//        }
//    }
}
