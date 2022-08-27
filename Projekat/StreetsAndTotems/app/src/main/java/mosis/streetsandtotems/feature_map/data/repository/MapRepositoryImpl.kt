package mosis.streetsandtotems.feature_map.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.feature_map.data.data_source.FirebaseMapDataSource
import mosis.streetsandtotems.feature_map.domain.repository.MapRepository

class MapRepositoryImpl(
    private val firebaseMapDataSource: FirebaseMapDataSource,
    private val auth: FirebaseAuth
) : MapRepository {
    override suspend fun updateMyLocation(newLocation: GeoPoint) {
        auth.currentUser?.let {
            firebaseMapDataSource.updateUserLocation(it, newLocation)
        }
    }


}