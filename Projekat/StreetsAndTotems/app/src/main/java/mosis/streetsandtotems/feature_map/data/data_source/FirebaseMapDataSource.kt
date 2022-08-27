package mosis.streetsandtotems.feature_map.data.data_source

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.FirestoreConstants
import mosis.streetsandtotems.feature_map.domain.model.Resource
import mosis.streetsandtotems.feature_map.domain.model.UserInGameData
import org.imperiumlabs.geofirestore.GeoFirestore
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

class FirebaseMapDataSource(private val db: FirebaseFirestore) {
    private val userGeoFirestore =
        GeoFirestore(db.collection(FirestoreConstants.USER_IN_GAME_DATA_COLLECTION))

    private val userGeoQuery =
        GeoFirestore(db.collection(FirestoreConstants.USER_IN_GAME_DATA_COLLECTION))

    fun updateUserLocation(user: FirebaseUser, newLocation: GeoPoint) {
        userGeoFirestore.setLocation(user.uid, newLocation)
    }

    suspend fun getUserInGameData(currentUser: FirebaseUser): Flow<UserInGameData?> {
        return db.collection(FirestoreConstants.USER_IN_GAME_DATA_COLLECTION)
            .whereNotEqualTo(FirestoreConstants.ID_FIELD, currentUser.uid).get()
            .await().documents.map { it.toObject<UserInGameData>()?.copy(id = it.id) }
            .asFlow()
    }

    fun regsterCallbacksOnUserInGameDataUpdate(
        currentUser: FirebaseUser,
        userAddedCallback: (user: UserInGameData?) -> Unit,
        userModifiedCallback: (user: UserInGameData?) -> Unit,
        userRemovedCallback: (user: UserInGameData?) -> Unit
    ): ListenerRegistration {
        return db.collection(FirestoreConstants.USER_IN_GAME_DATA_COLLECTION)
            .whereNotEqualTo(FirestoreConstants.ID_FIELD, currentUser.uid)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(
                    e,
                    snapshots,
                    userAddedCallback,
                    userModifiedCallback,
                    userRemovedCallback
                )
            }
    }

    suspend fun getResources(): Flow<Resource?> {
        return db.collection(FirestoreConstants.RESOURCES_COLLECTION).get()
            .await().documents.map { it.toObject<Resource>() }.asFlow()
    }

    fun registerCallbacksOnResourcesUpdate(
        resourceAddedCallback: (resource: Resource?) -> Unit,
        resourceModifiedCallback: (resource: Resource?) -> Unit,
        resourceRemovedCallback: (resource: Resource?) -> Unit
    ): ListenerRegistration {
        return db.collection(FirestoreConstants.RESOURCES_COLLECTION)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(
                    e,
                    snapshots,
                    resourceAddedCallback,
                    resourceModifiedCallback,
                    resourceRemovedCallback
                )
            }
    }

    private inline fun <reified T> collectionSnapshotListenerCallback(
        e: FirebaseFirestoreException?,
        snapshots: QuerySnapshot?,
        userAddedCallback: (user: T?) -> Unit,
        userModifiedCallback: (user: T?) -> Unit,
        userRemovedCallback: (user: T?) -> Unit
    ) {
        if (e != null) {
            Log.w("tag", "listen:error", e)
            return
        }

        for (dc in snapshots!!.documentChanges) {
            when (dc.type) {
                DocumentChange.Type.ADDED -> userAddedCallback(dc.document.toObject<T>())
                DocumentChange.Type.MODIFIED -> userModifiedCallback(dc.document.toObject<T>())
                DocumentChange.Type.REMOVED -> userRemovedCallback(dc.document.toObject<T>())
            }
        }
    }
}