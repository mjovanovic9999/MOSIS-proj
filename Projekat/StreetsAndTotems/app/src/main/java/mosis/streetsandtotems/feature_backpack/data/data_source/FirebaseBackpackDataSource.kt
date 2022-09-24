package mosis.streetsandtotems.feature_backpack.data.data_source

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.FireStoreConstants
import mosis.streetsandtotems.feature_map.domain.model.ResourceData
import mosis.streetsandtotems.feature_map.domain.model.ResourceType
import mosis.streetsandtotems.feature_map.domain.model.TotemData

class FirebaseBackpackDataSource(private val db: FirebaseFirestore) {

    suspend fun dropResource(l: GeoPoint, itemCount: Int, type: ResourceType) {
        db.collection(FireStoreConstants.RESOURCES_COLLECTION).document().set(
            ResourceData(
                l = l,
                remaining = itemCount,
                type = type,
            )
        ).await()
    }


    suspend fun placeTotem(myId: String, l: GeoPoint, mySquadId: String) {
        val currentTime = Timestamp.now()
        db.collection(FireStoreConstants.TOTEMS_COLLECTION).document().set(
            TotemData(
                bonus_points = 0,
                l = l,
                last_visited = currentTime,
                placed_by = myId,
                placing_time = currentTime,
                protection_points = 0,
                visible_to = mySquadId,
            )
        ).await()
    }

}