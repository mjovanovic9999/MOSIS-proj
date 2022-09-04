package mosis.streetsandtotems.feature_map.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.feature_map.data.data_source.FirebaseMapDataSource
import mosis.streetsandtotems.feature_map.data.data_source.FirebaseServiceDataSource
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

}