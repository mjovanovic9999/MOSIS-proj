package mosis.streetsandtotems.feature_backpack.domain.repository

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.feature_map.domain.model.ResourceType

interface BackpackRepository {

    suspend fun dropResource(l: GeoPoint, itemCount: Int, type: ResourceType)

    suspend fun placeTotem(l: GeoPoint)

}