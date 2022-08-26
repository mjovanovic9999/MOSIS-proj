package mosis.streetsandtotems.feature_map.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.feature_map.data.data_source.FirebaseMapDataSource
import mosis.streetsandtotems.feature_map.domain.model.Location
import mosis.streetsandtotems.feature_map.domain.repository.MapRepository

class MapRepositoryImpl(
    private val firebaseMapDataSource: FirebaseMapDataSource,
    private val auth: FirebaseAuth
) : MapRepository {
    override suspend fun updateMyLocation(newLocation: Location) {
        auth.currentUser?.let {
            try {
                firebaseMapDataSource.updateUserLocation(it, newLocation).await()
            } catch (e: Exception) {
                Log.d("firestoreError", e.message.toString())
            }
        }
    }


}