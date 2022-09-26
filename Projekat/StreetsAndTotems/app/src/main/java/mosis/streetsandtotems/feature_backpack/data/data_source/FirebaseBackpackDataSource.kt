package mosis.streetsandtotems.feature_backpack.data.data_source

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.FireStoreConstants
import mosis.streetsandtotems.core.presentation.components.IconType
import mosis.streetsandtotems.feature_map.domain.model.*
import mosis.streetsandtotems.feature_map.presentation.util.convertIconResourceTypeToResourceType
import mosis.streetsandtotems.feature_map.presentation.util.updateOneInventoryData
import org.imperiumlabs.geofirestore.GeoFirestore

class FirebaseBackpackDataSource(private val db: FirebaseFirestore) {

    suspend fun dropItem(
        l: GeoPoint,
        itemCount: Int,
        type: IconType.ResourceType?,
        oldInventoryData: UserInventoryData,
        myId: String,
        mySquadId: String,
    ) {
        try {
            if (type == null) {//totem
                val totemReference = db.collection(FireStoreConstants.TOTEMS_COLLECTION).document()

                val totemGeoFirestore =
                    GeoFirestore(db.collection(FireStoreConstants.TOTEMS_COLLECTION))

                db.runBatch {
                    val time = Timestamp.now()

                    it.set(
                        totemReference, TotemData(
                            l = l,
                            placed_by = myId,
                            placing_time = time,
                            last_visited = time,
                            bonus_points = 0,
                            protection_points = 0,
                            visible_to = mySquadId,
                        )
                    )
                    it.set(
                        db.collection(FireStoreConstants.USER_INVENTORY_COLLECTION).document(myId),
                        oldInventoryData.copy(
                            empty_spaces = (oldInventoryData.empty_spaces ?: 0) + itemCount,
                            inventory = oldInventoryData.inventory?.copy(
                                totem = (oldInventoryData.inventory.totem ?: 0) - itemCount,
                            )
                        )
                    )
                }.await()

                totemGeoFirestore.setLocation(totemReference.id, l)
            } else {
                val resourceReference =
                    db.collection(FireStoreConstants.RESOURCES_COLLECTION).document()

                val resourceGeoFirestore =
                    GeoFirestore(db.collection(FireStoreConstants.RESOURCES_COLLECTION))

                val resourceType = convertIconResourceTypeToResourceType(type)
                db.runBatch {
                    it.set(
                        resourceReference, ResourceData(
                            l = l,
                            remaining = itemCount,
                            type = resourceType,
                        )
                    )
                    val newInventoryCount = when (resourceType) {
                        ResourceType.Wood -> oldInventoryData.inventory?.wood ?: 0
                        ResourceType.Brick -> oldInventoryData.inventory?.brick ?: 0
                        ResourceType.Stone -> oldInventoryData.inventory?.stone ?: 0
                        ResourceType.Emerald -> oldInventoryData.inventory?.emerald ?: 0
                    } - itemCount
                    it.set(
                        db.collection(FireStoreConstants.USER_INVENTORY_COLLECTION).document(myId),
                        oldInventoryData.copy(
                            empty_spaces = (oldInventoryData.empty_spaces ?: 0) + itemCount,
                            inventory = updateOneInventoryData(
                                oldInventoryData.inventory ?: InventoryData(0, 0, 0, 0, 0),
                                newInventoryCount,
                                resourceType
                            )
                        )
                    )
                }.await()

                resourceGeoFirestore.setLocation(resourceReference.id, l)
            }
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }
}