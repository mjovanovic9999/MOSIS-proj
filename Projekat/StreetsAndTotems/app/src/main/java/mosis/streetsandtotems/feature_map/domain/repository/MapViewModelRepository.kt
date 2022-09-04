package mosis.streetsandtotems.feature_map.domain.repository

import com.google.firebase.firestore.GeoPoint

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
}